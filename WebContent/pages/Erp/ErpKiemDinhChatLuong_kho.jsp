<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluong_kho"%>
<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_kho"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.*"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpKiemdinhchatluong_kho obj =(ErpKiemdinhchatluong_kho)session.getAttribute("obj");
	ResultSet kdclRs = obj.getRsKdcl();
	
	NumberFormat formatter = new DecimalFormat("#,###,###.######"); 
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpKiemdinhchatluong_khoSvl","",userId);

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
	function newform()
	{   
		document.forms["kdcl"].action.value = "new";
	    document.forms["kdcl"].submit();
	}
	 
	function submitform()
	{
		document.kdcl.action.value= 'search';
		document.forms["kdcl"].submit();
	}
	
	function clearform()
	{ 
	   
	    document.kdcl.sanpham.value= "";
	    document.kdcl.trangthai.value = "";
	    
	    document.kdcl.tungayKD.value = "";
	    document.kdcl.denngayKD.value = "";
	    submitform();
	}

	function newform()
	{
		document.kdcl.action.value= 'new';
		document.kdcl.submit();
	}
	
	
	 function thongbao()
	 {
		 var tt = document.forms["kdcl"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["kdcl"].msg.value);
	 }
	 
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kdcl" method="post" action="../../ErpKiemdinhchatluong_khoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">Quản lý cung ứng > Quản lý mua hàng > Kiểm định chất lượng </TD>  
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
			                       
			                        <TD class="plainlabel" width="12%">Từ ngày </TD>
			                        <TD class="plainlabel" width="25%">
			                            <input type="text" class="days" 
			                                   id="tungayKD" name="tungayKD" value="<%=obj.getTungay() %>" maxlength="10" onchange="submitform()" />
			                        </TD>
			                        <TD class="plainlabel" width="12%">Đến ngày </TD>
			                        <TD class="plainlabel" >
			                            <input type="text" class="days" 
			                                   id="denngayKD" name="denngayKD" value="<%=obj.getDenngay() %>" maxlength="10" onchange="submitform()"/>
			                        </TD>
			                    </TR>
			                                                
                            
                             <TR>
                             	<TD  class="plainlabel" >Số lô </TD>
								<TD class="plainlabel">
									<input type="text" name="solo" value="<%= obj.getSolo()%>" size="20" onchange=submitform();>
								</TD>
								<TD class="plainlabel" >Sản phẩm </TD>
								<TD class="plainlabel">
									<input type="text" name=sanpham value="<%= obj.getSpTen() %>" size="20" onchange=submitform();>
								</TD>
                             </TR>
                             <TR>
                             	<TD class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel" colspan = 3>
								
										<% 
                    	String mang[] = new String[]{"0","1","2"};
                    	String mangten[] =new String[]{"Chưa chốt","Đã chốt","Đã xóa"};
                    	%> <select name="trangthai" onChange="submitform();">
										<option value=""></option>
										<%
                    	for(int j=0;j<mang.length;j++){
							 if(obj.getTrangthai().trim().equals(mang[j])){
							 %>
										<option selected="selected" value="<%=mang[j]%>">
											<%=mangten[j] %>
										</option>
										<%
							 }else{
							 %>
										<option value="<%=mang[j]%>">
											<%=mangten[j] %>
										</option>
										<% 
							 }
						 }
                    	%>
                    	</select>
								 								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="4" > 
                             		<a class="button" href="javascript:submitform()">
                                	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
                            <LEGEND class="legendtitle">&nbsp;Kiểm định chất lượng &nbsp;&nbsp;	
	
	                	   <a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                            	 
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="5%" align="center">Mã </TH>
                                    <TH width="20%" align="center">Sản phẩm</TH>

                                    
                                    <TH width="12%" align="center">Số lượng không đạt</TH>
                                    <TH width="7%" align="center">Trạng thái</TH>
                                    <TH width="5%" align="center">Ngày kiểm</TH>
                                    <TH width="8%" align="center">Người kiểm</TH>
                                    <TH width="7%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(kdclRs != null)
                                    while ( kdclRs.next()){
                                       
                                    	String tt = kdclRs.getString("TRANGTHAI").trim();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= kdclRs.getString("PK_SEQ") %> </TD>                                   
                                                <TD align="left"><%= kdclRs.getString("SPTEN")%> </TD>
                                              
                                                <TD align="center"><%= kdclRs.getString("SOLUONGKHONGDAT") %> </TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chưa chốt</TD>
                                                <% }else if( tt.equals("1") ) { %>
                                                 	<TD align="center">Đã chốt</TD>
                                                 <%}else{ %>
                                                 	<TD align="center">Đã xóa</TD>
													<%} %>
                                                <TD align="center"><%= kdclRs.getString("NGAYKD")%> </TD>
                                                <TD align="left"><%= kdclRs.getString("NGUOICHOT")%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
                                                	<%if(quyen[2]!=0){ %>
							                   		<A href = "../../ErpKiemdinhchatluongUpdate_khoSvl?userId=<%=userId%>&update=<%= kdclRs.getString("PK_SEQ") %>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A> &nbsp;
							                   		<A href = "../../ErpKiemdinhchatluong_khoSvl?userId=<%=userId%>&hoantat=<%= kdclRs.getString("PK_SEQ") %>"><IMG src="../images/Chot.png" alt="Hoàn tất" title="Hoàn tất" border=0></A>&nbsp;
							                   		<A href = "../../ErpKiemdinhchatluong_khoSvl?userId=<%=userId%>&delete=<%= kdclRs.getString("PK_SEQ") %>"><IMG src="../images/Delete20.png" alt="Xóa" title="Xóa" border=0></A>&nbsp;
							                   		<%} %>
							                   		 
												<% } else if(tt.equals("1")) { %>
													 
													<%if(quyen[2]!=0){ %>
							                   			<A href = "../../ErpKiemdinhchatluongUpdate_khoSvl?userId=<%=userId%>&display=<%= kdclRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
							                   		<%} %>
													
												 	
													
												<% }else{
													%>
													<A href = "../../ErpKiemdinhchatluongUpdate_khoSvl?userId=<%=userId%>&display=<%= kdclRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
													<%  
												}
                                                %>
												
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
                                          
                                            
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
	try
    {
		if(kdclRs != null)
			kdclRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>