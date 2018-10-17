<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphiList" %>
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
	IErpCauhinhchiphiList obj = (IErpCauhinhchiphiList)session.getAttribute("obj"); 
		obj.setNextSplittings();  %>
<% 	ResultSet TTDTlist = (ResultSet)obj.getTTDTList(); 
	
 	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpCauhinhchiphiSvl","",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
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
//     document.ncpForm.trangthai.selectedIndex = 0;
	document.getElementById("trangthai").value = "";
	document.getElementById("diengiai").value = "";
	 document.ncpForm.action.value = "search";
   	 document.forms["ncpForm"].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ncpForm" method="post" action="../../ErpCauhinhchiphiSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
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
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền  &gt; Kế toán &gt; Nhóm tài khoản</TD> 
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
												<INPUT id="diengiai" name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>

								<TR>
									<TD width="19%" class="plainlabel">Trạng thái </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<select id= "trangthai" name="trangthai" onChange ="searchform();">
												    <%if(obj.getTrangthai().equals("1")){ %>
													<option value = "" ></option>
													<option value = "1" selected="selected" >Hoạt động</option>
													<option value = "0" >Ngưng hoạt động</option>
													<%} else if (obj.getTrangthai().equals("0")){%>
													<option value = "" ></option>
													<option value = "1"  >Hoạt động</option>
													<option value = "0" selected="selected">Ngưng hoạt động</option>
													<%} else {%>
													<option value = "" selected="selected"></option>
													<option value = "1"  >Hoạt động</option>
													<option value = "0" >Ngưng hoạt động</option>
													<%} %>
												</select>
											</TD></TR>
										</TABLE>								
								</TR>
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
					<LEGEND class="legendtitle">&nbsp;Nhóm tài khoản&nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0 && obj.getChixem().equals("0") ){ %>
						<a class="button3" href="javascript:submitform()">
				    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
				    	<%} %>                         		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="15%" >Mã</TH>
									<TH width="20%">Diễn giải</TH>
									<TH width="15%">Trạng thái</TH>
									<TH width="10%">Ngày tạo</TH>
									<TH width="10%">Người tạo</TH>
									<TH width="10%">Ngày sửa</TH>
									<TH width="10%">Người sửa</TH>
									<TH width="10%">Tác vụ</TH>
								</TR>
					<% 		if(TTDTlist != null){ 

                                String lightrow = "tblightrow";
                                String darkrow = "tbdarkrow";
								ResultSet rs;
								
								int m = 0;
								while(TTDTlist.next()){
 									String tt = TTDTlist.getString("trangthai");
 									String trangthai = "";
 									
 									if(tt.equals("1"))
 										trangthai = "Hoạt động";
 									else
 										trangthai = "Ngưng hoạt động";
									
                                		if (m % 2 != 0) {%>                     
                                    		<TR class= <%=lightrow%> >
                               			<%} else {%>
                                    		<TR class= <%= darkrow%> >
                               			<%}%>
												
												<TD align="left"><%= TTDTlist.getString("MA")%> </TD>
												
												<TD align="left"><%= TTDTlist.getString("DIENGIAI")%></TD>
												
												<TD align="center"><%= trangthai %></TD>
												
												<TD align="center"><%= TTDTlist.getString("ngaytao")%></TD>
												
												<TD align="center"><%= TTDTlist.getString("nguoitao")%></TD>

												<TD align="center"><%= TTDTlist.getString("ngaysua")%></TD>
												
												<TD align="center"><%= TTDTlist.getString("nguoisua")%></TD>

												<TD align="center">
												<% if( obj.getChixem().equals("0") ) { %>
													<A href = "../../ErpCauhinhchiphiUpdateSvl?userId=<%=userId%>&update=<%=TTDTlist.getString("PK_SEQ")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
													<A href = "../../ErpCauhinhchiphiSvl?userId=<%=userId%>&delete=<%= TTDTlist.getString("PK_SEQ") %>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm chi phí này?')) return false;"></A>&nbsp;							
												<% } %>
													<!-- QUYEN VIEW DLN -->
													<A href = "../../ErpCauhinhchiphiUpdateSvl?userId=<%=userId%>&display=<%=TTDTlist.getString("PK_SEQ")%>"><img title="Hiển thị" src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<!-- END QUYEN VIEW DLN -->
												</TD>
                                        	
                                    	</TR>
                               	<% 
                               			m++;
                            		}
                                }
											
                            %>  
						<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ncpForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('ncpForm', eval(ncpForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ncpForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('ncpForm', eval(ncpForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('ncpForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
						</tr>
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
 	if( TTDTlist != null) TTDTlist.close(); 
 	obj.DBClose();
	} %>
	