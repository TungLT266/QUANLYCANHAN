<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.banggiaban.IErpBanggiabanGiayList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpBanggiabanGiayList obj =(IErpBanggiabanGiayList)session.getAttribute("obj");
	ResultSet bglist = obj.getBanggiaRs();
	ResultSet dvkd = (ResultSet)obj.getDvkd(); 
	ResultSet kenh = (ResultSet)obj.getKenh(); 
	ResultSet loaikh = (ResultSet)obj.getLoaikh(); 
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpBanggiabanGiaySvl","",userId);
	
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
		var msg = "<%=obj.getMsg()%>".trim();
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
			
			if(msg.length > 0 && msg !== "null") {
				alert(msg);
			}
        });	
			
	</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['khtt'].bgTen.value= "";
	    document.forms['khtt'].dvkdId.value= "";
	    document.forms['khtt'].kenhId.value = "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].diengiai.value = "";
	    document.forms['khtt'].loaikhId.value = "";
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
<form name="khtt" method="post" action="../../ErpBanggiabanGiaySvl">
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Định giá bán </TD>  
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
                            <TD width="100%" align="left" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
									<TD width="10%" class="plainlabel">Tên bảng giá</TD>
									<TD width="25%" class="plainlabel">
									<INPUT name="bgTen" type="text"  value='<%=obj.getMa() %>' onChange = "submitform();"/></TD>
									<TD width="10%" class="plainlabel">Đơn vị kinh doanh </TD>
								      <TD  class="plainlabel">
								      	<SELECT name="dvkdId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
							</TR>
								
								<TR>
								  <TD class="plainlabel">Kênh bán hàng </TD>
								  <TD class="plainlabel">
								      	<SELECT name="kenhId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(kenh.next()){ 
								    	if(kenh.getString("kenhId").equals(obj.getKenhId())){ %>
								      		<option value='<%=kenh.getString("kenhId") %>' selected><%=kenh.getString("kenh") %></option>
								      	<%}else{ %>
								     		<option value='<%=kenh.getString("kenhId") %>'><%=kenh.getString("kenh") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  </SELECT></TD>
                                  <TD class="plainlabel">Loại khách hàng </TD>
								   <TD class="plainlabel">
								      	<SELECT name="loaikhId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{
								  		 if(loaikh != null){
								  		 while(loaikh.next()){ 
								    	if(loaikh.getString("loaikhId").equals(obj.getLoaikhId())){ %>
								      		<option value='<%=loaikh.getString("loaikhId") %>' selected><%=loaikh.getString("loaikh") %></option>
								      	<%}else{ %>
								     		<option value='<%=loaikh.getString("loaikhId") %>'><%=loaikh.getString("loaikh") %></option>
								     	<%}}
								  		 }
								  	 	}catch(java.sql.SQLException e){} %>	
								     	
                                  </SELECT></TD>
							  </TR>
							   
							  <TR>
								  <TD class="plainlabel">Khách hàng </TD>
								  <TD class="plainlabel">
								  	<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai() %>' onChange = "submitform();"/>
								  </TD>
							 
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("2")){ %>
								    	<option value=""> </option>
								    	<option value="1">Chờ duyệt</option>
								    	<option value="2">Đã duyệt</option>
								    	<option value="0"  selected> Ngưng hoạt động </option>
									<%}else  if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected="selected"> Chờ duyệt</option>
								    	<option value="2">Đã duyệt</option>
								    	<option value="0"   > Ngưng hoạt động </option>
							    	<%}else  if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected="selected"> Chờ duyệt</option>
								    	<option value="2">Đã duyệt</option>
								    	<option value="0"  selected> Ngưng hoạt động </option>
									<%}else { %>
								    	<option value=""> </option>
								    	<option value="1"> Chờ duyệt</option>
								    	<option value="2">Đã duyệt</option>
								    	<option value="0"> Ngưng hoạt động </option>
									<%}  %>
								    	</select></TD>
								</TR>
                              
                             <tr class="plainlabel"> 
                             	<td colspan="4" > 
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
                            <LEGEND class="legendtitle">&nbsp;Định bảng giá &nbsp;&nbsp;	
                            	
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
									<TH width="15%" align="center">Tên bảng giá </TH>
									<TH width="15%" align="center">Đơn vị kinh doanh </TH>
									<TH width="10%" align="center">Kênh </TH>
									<TH width="10%" align="center">Trạng thái </TH>
									<TH width="10%" align="center">Từ ngày</TH>
									<TH width="10%" align="center">Đến ngày </TH>
									<TH width="10%" align="center">Ngày sửa </TH>
									<TH width="10%" align="center">Người sửa </TH>
									<TH width="10%" align="center">Tác vụ </TH>
								</TR>
                                
                            <% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
							while(bglist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
										<TD align="left"><%= bglist.getString("ten") %></TD>
										<TD align="left"><%= bglist.getString("dvkd") %></TD>
										<TD align="left"><%= bglist.getString("kenh") %></TD>	
											
									<% if (bglist.getString("trangthai").equals("1")){ %>
										<TD align="left">Chờ duyệt</TD>							
									<%} else if(bglist.getString("trangthai").equals("2")){ %>
										<TD align="left">Đã duyệt</TD>
									<%}else {%>
										<TD align="left">Ngưng hoạt động</TD>
									<%}%>
									
										<TD align="center"><%= bglist.getDate("tungay").toString() %></TD>	
										<TD align="center"><%= bglist.getString("denngay").toString()  %></TD>
										<TD align="center"><%= bglist.getDate("ngaysua").toString() %></TD>
										<TD align="left"><%= bglist.getString("nguoisua") %></TD>
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
																						
											<TR>											
											<TD>
										<% if (!bglist.getString("trangthai").equals("0")){  
										 
													%>
														<%if(quyen[2]!=0){ 
												 
												%>										
													<A href = "../../ErpBanggiabanGiayUpdateSvl?userId=<%=userId%>&update=<%= bglist.getString("pk_seq") %>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>&nbsp;
													<%} %>
													<%if(quyen[1]!=0 ) { %>		
													<A href = "../../ErpBanggiabanGiaySvl?userId=<%=userId%>&delete=<%= bglist.getString("pk_seq") %>"><img title="Xóa" src="../images/Delete20.png" alt="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A>&nbsp;  
													<%} %>
													<A href = "../../ErpBanggiabanGiayUpdateSvl?userId=<%=userId%>&display=<%= bglist.getString("pk_seq") %>"><img title="Hiển thị" src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>&nbsp;
												<%}else{%>
													<A href = "../../ErpBanggiabanGiayUpdateSvl?userId=<%=userId%>&display=<%= bglist.getString("pk_seq") %>"><img title="Hiển thị" src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>&nbsp;
												<%} %>
												
												<A href = "../../ErpBanggiabanGiayUpdateSvl?userId=<%=userId%>&copy=<%= bglist.getString("pk_seq") %>"><img title="Sao chép" src="../images/convert.gif" alt="Sao chép" width="20" height="20" longdesc="Sao chép" border = 0></A>&nbsp;
												 
											</TD>
																					
											<%if (!bglist.getString("TINHTRANG").equals("2")){  
													if(!bglist.getString("trangthai").equals("2")){%>
												
											<TD>
												<%if(quyen[2]!=0){ %>		
												<A href = "../../ErpBanggiabanGiaySvl?userId=<%=userId%>&approve=<%= bglist.getString("pk_seq") %>"><img title="Duyệt" src="../images/Chot.png" alt="Duyet" width="20" height="20" longdesc="Duyet" border = 0></A>&nbsp;
												<%} %>
											</TD>
  
											</TR>
											<% }} %>
											
											</TABLE>
										</TD>
									</TR>
								<% m++; }
								
								} catch( Exception e){ System.out.println("Exception: " + e.getMessage()); }%>
                                
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
	try
    {
		if (dvkd != null)
			dvkd.close(); 
		if (kenh != null)
			kenh.close(); 
		if (loaikh != null)
			loaikh.close(); 
		if(bglist != null)
			bglist.close();
	}
	catch (SQLException e) {
		e.printStackTrace();
	}finally{
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}%>
<%}%>