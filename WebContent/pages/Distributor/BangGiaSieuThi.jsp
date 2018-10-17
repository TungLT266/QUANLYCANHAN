<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.banggiasieuthi.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IBanggiasieuthiList obj = (IBanggiasieuthiList)session.getAttribute("obj"); %>
<% List<IBanggiasieuthi> bgstlist = (List<IBanggiasieuthi>)obj.getBgstList(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
	    document.bgstForm.bgstTen.value = "";      
	    document.bgstForm.dvkdTen.selectedIndex = 0;
	    submitform();
	}

	function submitform()
	{
		document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function newform()
	{
		document.forms['bgstForm'].action.value= 'new';
		document.forms['bgstForm'].submit();
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgstForm" method="post" action="../../BanggiasieuthiSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
        <!--begin common dossier info---> <!--End common dossier info--->
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Thiết lập dữ liệu nền > Bảng giá siêu thị</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %>&nbsp;  </TD>
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
                                    <TD width="19%" class="plainlabel">Tên bảng giá</TD>
                                    <TD width="81%" class="plainlabel">
                                    <INPUT name="bgstTen" value="<%= obj.getTenbanggia() %>" type="text" size="40" onChange = "submitform();" /></TD></TR>
                               

                               <TR>
                                <TD class="plainlabel">Đơn vị kinh doanh</TD>
                                <TD class="plainlabel">
                                <SELECT name="dvkdTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% if(dvkd != null){
										  try{ while(dvkd.next()){ 
								    			if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      				<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} }%>	 
                                </SELECT>
                                </TD> </TR>
                               
                               <TR>
									<TD class="plainlabel" colspan="3">
                           		 		<a class="button2" href="javascript:clearform()">
                                		<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									  
                                    </TD>
								</TR>
                               
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
                            <LEGEND class="legendtitle">&nbsp;Bảng giá siêu thị &nbsp;&nbsp;	                         
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                              
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Bảng giá </TH>
                                    <TH width="">Đơn vị kinh doanh </TH>
                                    <TH width="">Ngày tạo</TH>
                                    <TH width="">Người tạo</TH>
                                    <TH width="">Ngày sửa</TH>
                                    <TH width="">Người sửa</TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>

                                </TR>

                                <% 
                                    IBanggiasieuthi bgst = null;
                                    int size = bgstlist.size();
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (m < size){
                                        bgst = bgstlist.get(m);
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=bgst.getTenbanggia()%></div></TD>                                   
                                                <TD><div align="center"><%=bgst.getDonvikinhdoanh()%></div></TD>
                                                <TD align="center"><%=bgst.getNgaytao()%></TD>
                                                <TD align="center"><%=bgst.getNguoitao()%></TD>
                                                <TD align="center"><%=bgst.getNgaysua()%></TD>
                                                <TD align="center"><%= bgst.getNguoisua()%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR><TD>
                                                        <A href = "../../BanggiastUpdateSvl?userId=<%=userId%>&update=<%=bgst.getId()%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
													<A href = "../../BanggiasieuthiSvl?userId=<%=userId%>&assign=<%= bgst.getId() %>"><img src="../images/Next20.png" alt="Chon Nha Phan Phoi" width="20" height="20" longdesc="Chọn khách hàng Chi nhánh / Đối tác" border = 0></A>
													</TD>
													<TD>&nbsp;</TD>
                                                    <TD>
                                                        <A href = "../../BanggiasieuthiSvl?userId=<%=userId%>&delete=<%=bgst.getId()%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa bảng giá này?')) return false;"></A></TD>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                         <tr class="tbfooter" > <td colspan="8" >&nbsp;</td> </tr>                                                   
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
<%
	try
	{
		if(!(dvkd == null))
			dvkd.close();
		if(bgstlist!=null){
			bgstlist.clear();
		}
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
%>
</BODY>
</html>
<%}%>