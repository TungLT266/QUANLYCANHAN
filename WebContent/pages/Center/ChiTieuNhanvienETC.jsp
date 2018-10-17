<%@page import="java.util.Calendar"%>
<%@page import="geso.traphaco.center.beans.chitieunhanvienetc.imp.ChiTieuNhanvienETC"%>
<%@page import="geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IChiTieuNhanvienETC obj=(ChiTieuNhanvienETC)session.getAttribute("obj");
	ResultSet ctList = obj.getChitieuListRs();

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/OPV/index.jsp");
	}else{
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ChiTieuNhanvienETCSvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].quy.value=0;
	    document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
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
	function moform(value)
	{
		document.forms['bgstForm'].loaichitieu.value=value;	
		document.forms['bgstForm'].action.value='search';
		document.forms['bgstForm'].submit();
	}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgstForm" method="post" action="../../ChiTieuNhanvienETCSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="nppId" value="" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý chỉ tiêu > Khai báo > Chỉ tiêu nhân viên</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn &nbsp;<%=userTen%>  </TD>
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
                             	<TD width="100px" class="plainlabel" >Tháng </TD>
								<TD class="plainlabel">
									<select name="thang" style="width :100px"  >
									<option value=0> </option>  
									<%
	 									for(int k = 1; k <= 12; k++){
	 									  if( Integer.toString(k) == obj.getThang() ){
	 									%>
										<option value=<%=k%> selected="selected" > <%=k%></option> 
									<% } else { %>
										<option value=<%=k%> ><%=k%></option> 
									<% } } %>
									</select>
								</TD>
                             </TR>
                             <TR>
                             	<TD width="15%" class="plainlabel" >Năm </TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :100px" >
										<option value=0> </option>  
										<%
									  		Calendar c2 = Calendar.getInstance();
	 										int t=c2.get(Calendar.YEAR) + 6;
	 										for(int n = 2015; n < t; n++){
	 										if( Integer.toString(n) == obj.getNam() ){
	 									%>
										<option value=<%=n%> selected="selected" > <%=n%></option> 
										<% }else{ %>
											<option value=<%=n%> ><%=n%></option> 
										<% } } %>
									</select>
								</TD>
                             </TR >
                             
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                            		 <a class="button3" href="javascript:submitform()">
                           				<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
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
                            <LEGEND class="legendtitle">&nbsp;Quản lý chỉ tiêu &nbsp;&nbsp;	   
                            <%if(quyen[Utility.THEM]!=0) {%>                        
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                           <%} %>  
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Năm </TH>
                                    <TH width="">Tháng </TH>
                                    <TH width="">Diễn giải </TH>
                                    <TH width="">Trạng thái</TH> 
                                    <TH width="">Ngày tạo</TH>
                                    <TH width="">Người tạo</TH>
                                    <TH width="">Ngày sửa</TH>
                                    <TH width="">Người sửa </TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while ( ctList != null && ctList.next() ){
                                    	String tt = ctList.getString("trangthai");
                                    	String trangthai = "";
                                    	if( tt.equals("0") )
                                    		trangthai = "Chưa duyệt";
                                    	else if( tt.equals("1") )
                                    		trangthai = "Đã duyệt";
                                    	else
                                    		trangthai = "Đã hủy";
                                    	
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><%= ctList.getString("nam") %></TD>                                   
                                                <TD><%= ctList.getString("thang") %></TD>
                                                <TD><%= ctList.getString("diengiai") %></TD>
                                                <TD align="center"><%= trangthai %></TD>
                                                <TD ><%= ctList.getString("ngaytao") %></TD>
                                                <TD ><%= ctList.getString("nguoitao") %></TD>
                                                <TD ><%= ctList.getString("ngaysua") %></TD>
                                                <TD ><%= ctList.getString("nguoisua") %></TD>
                                                <TD align="center" >
                                              		<% if(quyen[Utility.XEM] != 0 ){ %>
                                                        <A href = "../../ChiTieuNhanvienETCNewSvl?userId=<%=userId%>&display=<%= ctList.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>&nbsp;
                                                    <% } %>
                                                   
                                                    <%
                                                    if( tt.equals("0") ){ %>
              
                                                    	<%if(quyen[Utility.SUA]!=0) {%>
                                                        	<A href = "../../ChiTieuNhanvienETCNewSvl?userId=<%=userId%>&update=<%= ctList.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" title="Cập nhật" border = 0></A>&nbsp;
                                                    	<%} %>
                                                    
                                                   		<%if(quyen[Utility.XOA]!=0) {%>
                                                        	<A href = "../../ChiTieuNhanvienETCSvl?userId=<%=userId%>&delete=<%= ctList.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" title="Xóa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa chỉ tiêu này?')) return false;"></A>&nbsp;
                                                     	<%} %>
                                                     
                                                     	<%if(quyen[Utility.CHOT]!=0) {%>
                                                        	<A href = "../../ChiTieuNhanvienETCSvl?userId=<%=userId%>&chot=<%= ctList.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt chỉ tiêu" border = 0></A>&nbsp;
                                                     	<%} %>
                                                     
                                                     <%} else if( tt.trim().equals("1")){ %>
                                                     
                                                     	<%if(quyen[Utility.CHOT]!=0) {%>
                                                        	<A href = "../../ChiTieuNhanvienETCSvl?userId=<%=userId%>&unchot=<%= ctList.getString("pk_seq") %>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>&nbsp;
                                                    	<%} %>
                         
                                                    <%}%>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                  <tr class="tbheader"> <td colspan="15"> &nbsp;</td></tr>                                                 
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
<%
if(obj!=null)obj.closeDB();
	
	} %>
</html>

