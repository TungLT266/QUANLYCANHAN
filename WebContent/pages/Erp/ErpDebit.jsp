<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.debit.IErpDebitList"%>
<%@ page import="geso.traphaco.erp.beans.debit.imp.ErpDebitList"%>
<%@ page import = "geso.traphaco.center.util.Utility" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpDebitList obj =(IErpDebitList)session.getAttribute("obj");
    ResultSet csxRs = obj.getRsDebit();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['khtt'].magiamgiahangmua.value= "";
	    document.forms['khtt'].diengiai.value= "";
	    document.forms['khtt'].trangthai.value = "";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpDebitSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán > Debit note </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
                             	<TD width="15%" class="plainlabel" >Số chứng từ </TD>
								<TD class="plainlabel">
									<input  type="text" name="sochungtu" value="<%=obj.getMa() %>" size="20" onchange=submitform();>
								</TD>
                             </TR>
                             <TR>
                             	<TD width="20%" class="plainlabel" >Diễn giải </TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDienGiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<% if(obj.getTrangThai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Đã xoá</option>
											<option value=""> </option>
										<%} else if( obj.getTrangThai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected >Đã chốt</option>
											<option value="2" >Đã xoá</option>
											<option value=""> </option>
											
										<%} else  if( obj.getTrangThai().equals("2") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1"  >Đã chốt</option>
											<option value="2"  selected>Đã xoá</option>
											<option value=""> </option>
											
										<% } else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1"  >Đã chốt</option>
											<option value="2"  >Đã xoá</option>
											<option value="" selected> </option>
										 <% }   %>
										
									 </select>
								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Debit note &nbsp;&nbsp;	
                            	
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="7%" align="center">Mã</TH>
                                    <TH width="15%" align="center">Diễn giải</TH>
                                    <TH width="15%" align="center">Đối tượng</TH>
                                    <TH width="7%" align="center">Trạng thái</TH>
                                    <TH width="10%" align="center">Ngày tạo</TH>
                                    <TH width="10%" align="center">Người tạo</TH>
                                    <TH width="10%" align="center">Ngày sửa</TH>
                                    <TH width="10%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(csxRs != null)
                                    while ( csxRs.next()){
                                       
                                    	String tt = csxRs.getString("trangthai").trim();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= csxRs.getString("pk_seq") %></TD>
                                                <TD align="left"><%= csxRs.getString("diengiai") %></TD>                                      
                                                <TD align="left"><%= csxRs.getString("doituong")%></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chưa duyệt</TD>
                                                <% } else { if(tt.equals("1")) { %> 
                                                	<TD align="center">Đã duyệt</TD>
                                                <% } else {  %>       
                                                	<TD align="center">Đã hủy</TD>
                                                <%} } %> 
                                                	
                                                <TD align="center"><%= csxRs.getString("NGAYTAO")%> </TD>
                                                <TD align="left"><%= csxRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= csxRs.getString("NGAYSUA")%> </TD>
                                                <TD align="left"><%= csxRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
							                   		<A href = "../../ErpDebitUpdateSvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>">
							                   			<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
							                   		<A href = "../../ErpDebitUpdateSvl?userId=<%=userId%>&chot=<%= csxRs.getString("pk_seq") %>">
							                   			<img src="../images/Chot.png" alt="Duyet" width="20" height="20" longdesc="Duyet" border=0 onclick="if(!confirm('Bạn Muốn Duyệt Phiếu Này?')) return false;"></A>&nbsp;
													<A href = "../../ErpDebitUpdateSvl?userId=<%=userId%>&delete=<%= csxRs.getString("pk_seq") %>">
														<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn Muốn Xoá Phiếu Này?')) return false;"></A>
												<% } else { %>
													<A href = "../../ErpDebitUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>">
														<IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
												<% }  %>
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
                                          <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
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
</html>
<% 	
	try{
		if(csxRs != null)
			csxRs.close();
		if(obj != null){
			obj.DbClose();
		}
	}catch (SQLException e) {
		e.printStackTrace();
	} %>
<%}%>