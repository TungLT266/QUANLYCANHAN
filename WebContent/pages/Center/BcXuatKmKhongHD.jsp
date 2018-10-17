<%@page import="java.text.Format"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghdList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpXuatkmkhonghdList obj =(IErpXuatkmkhonghdList)session.getAttribute("obj");
	ResultSet csxRs = obj.getGiamgiaRs();
	ResultSet khRs = obj.getKhRs();  
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ErpHopdongSvl","",userId);
	
%>
<% obj.setNextSplittings(); %>
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
	
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
	
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
		document.forms['khtt'].mahoadon.value= "";
	    document.forms['khtt'].diengiai.value= "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].sohoadon.value = "";
	    document.forms['khtt'].khachhang.value = "";
	    document.forms['khtt'].tennguoitao.value ="";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}
	
	function xuatExcel() {
		document.forms['khtt'].action.value= 'excel';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	function thongbao()
	 {
		 var tt = document.forms["khtt"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["khtt"].msg.value);
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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BcXuatKmKhongHDSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
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
                            <TD align="left" colspan="2" class="tbnavigation"> Báo cáo quản trị > Báo cáo khác > Báo cáo xuất KM không hóa đơn </TD>  
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
                             	<TD class="plainlabel" >Từ ngày </TD>
								<TD class="plainlabel">
									<input name="txtTungay" class="days" value="<%=obj.getTungay() %>" onchange="submitform()">
								</TD>
								
								<td class="plainlabel">Đến ngày</td>
								<td class="plainlabel">
									<input name="txtDenngay" class="days" value="<%=obj.getDenngay() %>" onchange="submitform()">
								</td>
                             </TR>

                              <TR>
                             	<TD class="plainlabel" >Đối tác / ETC </TD>
								<TD class="plainlabel">
									<select name = "khachhang" class="select2" style="width: 200px" onchange=submitform(); >
				                    		<option value=""> </option>
				                        	<%
				                        		if(khRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(khRs.next())
				                        			{  
				                        			if( khRs.getString("pk_seq").equals(obj.getKhachhang())){ %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
				                        		 <% } } khRs.close();} catch(SQLException ex){}
				                        		}
				                        	%>
				                    	</select>
								</TD>
								
                             	<TD class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Đã hủy</option>
											<option value="">Tất cả </option>
										<%} else { if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>	
											<option value="2">Đã hủy</option>									
											<option value="">Tất cả </option>											
										<% } else { if ( obj.getTrangthai().equals("2") ){%>
											<option value="0" >Chưa chốt</option>
											<option value="1" >Đã chốt</option>	
											<option value="2" selected >Đã hủy</option>									
											<option value="">Tất cả </option>	
											<%}else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>		
											<option value="2">Đã hủy</option>								
											<option value="" selected>Tất cả </option>
										 <% }} }  %>
										
									 </select>
								</TD>
                             </TR >                          
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
										
									<a class="button2" href="javascript:xuatExcel();">
										<img style="top: -4px;" src="../images/button.png" alt="">Xuất dữ liệu</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td>
								<td class="plainlabel"></td>
								<td class="plainlabel"></td>
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
                            <LEGEND class="legendtitle">&nbsp;Xuất khuyến mại không hóa đơn &nbsp;&nbsp;	
                            	<%-- <% if(quyen[Utility.THEM]!=0 ) { %> 
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	<%} %> --%>
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="18%" align="center">Đơn vị</TH>
                                    <TH width="10%" align="center">Mã đơn hàng</TH>
                                    <TH width="8%" align="center">Ngày xuất</TH>
                                    <TH width="8%" align="center">Mã vật tư</TH>
                                    <TH width="20%" align="center">Tên vật tư</TH>
                                    <TH width="8%" align="center">Số lượng</TH>
                                    <TH width="8%" align="center">Đơn vị tính</TH>
                                    <TH width="10%" align="center">Lý do</TH>
                                    <TH width="10%" align="center">Mã vụ việc</TH>
                                </TR>
                                <% 
                                   
                                int count = 0;
                         		if(csxRs!= null)
                         		{ 
                         			while(csxRs.next())
                         			{                         				
                    					if((count % 2 ) == 0) {%>
        		                         	<TR class='tbdarkrow'>
        		                        <%}else{ %>
        		                          	<TR class='tblightrow'>
        		                        <%} %>
                                                <TD align="center"><%= csxRs.getString("Donvi")%></TD> 
                                                <TD align="center"><%= csxRs.getString("Madonhang")%></TD>                            
                                                <TD align="center"><%= csxRs.getString("Ngayxuat")%></TD>
                                                <TD align="center"><%= csxRs.getString("Mavattu") %> </TD>
                                                <TD align="center"><%= csxRs.getString("Tenvattu") %> </TD>
                                                <TD align="center"><%= csxRs.getString("soluong") %> </TD>
                                                <TD align="center"><%= csxRs.getString("DVT") %> </TD>
                                                <TD align="center"> </TD>
                                                <TD align="center"><%= csxRs.getString("mavuviec") %> </TD>

                                            </TR>
                                   <% count++;  
                         		    }} %>
								 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('khtt', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('khtt', eval(khtt.nxtApprSplitting.value) -1, 'view')">
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
															href="javascript:View('khtt', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('khtt', eval(khtt.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('khtt', -1, 'view')"> &nbsp; <%} %>
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
</html>
<% 	
	try
    {
		if(csxRs != null)
			csxRs.close();
				
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>