<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.banggiablnpp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoDMS/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("BanggiablSvl","",userId);

	%>

<% IBanggiablnppList obj = (IBanggiablnppList)session.getAttribute("obj"); %>
<% List<IBanggiablnpp> bgbllist = (List<IBanggiablnpp>)obj.getBgblList(); %>
<% ResultSet ncc = (ResultSet)obj.getNcc(); %>
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
	function submitform()
	{
		document.forms['bgblForm'].action.value= 'search';
		document.forms['bgblForm'].submit();
	}
	function clearform()
	{
		document.bgblForm.bgblTen.value = "";
		document.bgblForm.dvkdTen.selectedIndex = 0;
		submitform();	    
	}



	function newform()
	{
		document.forms['bgblForm'].action.value= 'new';
		document.forms['bgblForm'].submit();
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgblForm" method="post" action="../../BanggiablSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%">
    
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">Thiết lập dữ liệu nền > Dữ liệu nền sản phẩm >  Bảng giá bán lẻ</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= obj.getNppTen() %>&nbsp;</TD>
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
                                    <INPUT name="bgblTen" value="<%= obj.getTenbanggia() %>" type="text" size="40" onChange = "submitform();"/></TD></TR>
                               
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
                            <LEGEND class="legendtitle">&nbsp;Bảng giá bán lẻ  &nbsp; &nbsp; &nbsp;
							      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader" align=center>
                                    <TH width="15%">Bảng giá </TH>
                                    <TH width="10%">ĐVKD </TH>
                                    <TH width="10%">Ngày tạo</TH>
                                    <TH width="15%">Người tạo</TH>
                                    <TH width="10%">Ngày sửa </TH>
                                    <TH width="15%">Người sửa</TH>
                                    <TH width="5%" align="center">&nbsp;Tác vụ</TH>

                                </TR>

                                <% 
                                    IBanggiablnpp bgbl = null;
                                    int size = bgbllist.size();
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (m < size){
                                        bgbl = bgbllist.get(m);
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=bgbl.getTenbanggia()%></div></TD>                                   
                                                <TD><div align="center"><%=bgbl.getDonvikinhdoanh()%></div></TD>
                                                <TD align="center"><%=bgbl.getNgaytao()%></TD>
                                                <TD align="left"><%=bgbl.getNguoitao()%></TD>
                                                <TD align="center"><%=bgbl.getNgaysua()%></TD>
                                                <TD align="left"><%= bgbl.getNguoisua()%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR><TD>
                                                    <%if(quyen[Utility.SUA]!=0){ %>
                                                        <A href = "../../BanggiablUpdateSvl?userId=<%=userId%>&update=<%=bgbl.getId()%>"><img src="../images/Edit20.png" alt="Cap Nhat" width="20" height="20" longdesc="Cap Nhat" border = 0></A>
                                                   <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                        </TD>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>
                                        <tr class="tbfooter" > <td colspan="10" >&nbsp;</td> </tr>                                                   
                            </TABLE>
                            </TD>
                        </TR>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>

            </TBODY>
        </TABLE>
        <!--end body Dossier--></TD>
    </TR>
</TABLE>
</form>
</BODY>
</html>
<%
	try
	{
		if(!(dvkd == null))
			dvkd.close();
		if(!(ncc == null))
			ncc.close();
		if(bgbllist!=null){
			bgbllist.clear();
		}
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}

		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>
