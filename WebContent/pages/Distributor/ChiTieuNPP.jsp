<!--desigs : khoana, chi tieu trung tam cho tung vung -->
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="geso.traphaco.distributor.beans.chitieunpp.IChiTieuNhaPP"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.distributor.beans.chitieunpp.imp.ChiTieuNhaPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "geso.traphaco.distributor.beans.banggiasieuthi.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>


<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ChiTieuNppSvl","",userId);

	%>
<%	
	IChiTieuNhaPP obj=(IChiTieuNhaPP)session.getAttribute("obj");
	ResultSet chitieunhapplist=obj.getListChiTieu();
	Integer thang=obj.getThang();
	Integer nam=obj.getNam();
	
	String tenhapp=(String)session.getAttribute("tennhapp");
 
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
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].thang.value=0;
	  //  document.forms['bgstForm'].action.value= 'search';
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
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgstForm" method="post" action="../../ChiTieuNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
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
                            <TD align="left" colspan="2" class="tbnavigation">Quản lý chỉ tiêu > Chỉ tiêu Sells Out</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng   &nbsp;<%=obj.GetTenNpp() %>  </TD>
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
                             <TD width="15%" class="plainlabel" >Tháng &nbsp;&nbsp; <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1;k<=12;k++){
									  if(k==thang){
									  
									%>
									<option value=<%=k %> selected="selected" > <%=k %></option> 
									<%
									  }else{
									 %>
									<option value=<%=k %> ><%=k %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="20%" class="plainlabel" >Năm &nbsp;&nbsp;  <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008;n<year_+3;n++){
									  if(n==nam){									  
									%>
									<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
									<option value=<%=n %> ><%=n %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR >
                     
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                           	
                             </td> </tr>
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
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Tháng </TH>
                                    <TH width="">Năm  </TH>
                                    <TH > Đơn vị KD </TH>
                                    <TH > Kênh bán hàng </TH>
                                    <TH width="">Chỉ tiêu</TH>
                                    <TH width="">Số ngày làm việc</TH>
                                    <TH width="">Ngày kết thúc</TH>
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
                                    if(chitieunhapplist!=null)
                                    while ( chitieunhapplist.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><div align="left"><%=chitieunhapplist.getInt("THANG")%></div></TD>                                   
                                                <TD><div align="center"><%=chitieunhapplist.getInt("NAM")%></div></TD>
                                                 <TD align="center"><%=chitieunhapplist.getString("donvikinhdoanh")%></TD>
                                                 <TD align="center"><%=chitieunhapplist.getString("kenh")%></TD>
		                                          
                                                <TD align="center"><%=formatter.format(chitieunhapplist.getDouble("CHITIEU"))%></TD>
                                                 <TD align="center"><%= chitieunhapplist.getString("SONGAYLAMVIEC")%></TD>
                                                 <td align ="center"><%= chitieunhapplist.getString("NGAYKETTHUC") %> </td>
                                                <%if( chitieunhapplist.getString("trangthai").trim().equals("0") ) {%>
                                                <TD align="center">Chờ xử lý</TD>
                                                <%}else{ %>
                                                <TD align="center">Đã chốt</TD>
                                                <%} %> 
                                                <TD align="center"><%= chitieunhapplist.getString("NGAYTAO")%></TD>
                                                 <TD align="center"><%= chitieunhapplist.getString("nguoitao")%></TD>
                                                  <TD align="center"><%= chitieunhapplist.getString("NGAYsua")%></TD>
                                                   <TD align="center"><%=chitieunhapplist.getString("NGuoisua")%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                   <%--   <%if( chitieunhapplist.getString("trangthai").trim().equals("0")) {%>
                                                    <TD>
                                                        <A href = "../../ChiTieuNhaPPNewSvl?userId=<%=userId%>&update=<%=chitieunhapplist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" title="Cap nhat" border = 0></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                  		<TD>
                                                        <A href = "../../ChiTieuNppSvl?userId=<%=userId%>&delete=<%=chitieunhapplist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" title="Xóa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa chỉ tiêu này?')) return false;"></A>
                                                        </TD>
                                                        <TD>
                                                        <A href = "../../ChiTieuNppSvl?userId=<%=userId%>&chot=<%=chitieunhapplist.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border=0 ></A>
                                                        </TD>
                                                    
                                                     <%}else{ %> --%>
	                                                     <TD>
	                                                     <%if(quyen[Utility.XEM]!=0){ %>
	                                                    <A href = "../../ChiTieuNhaPPNewSvl?userId=<%=userId%>&display=<%=chitieunhapplist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
                                                        <%} %>
                                                        </TD>
                                                  <%--   <%} %> --%>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                          <TR class="tbfooter" >
						<TD align="center" colspan="15"> |< < 1 to 20 of 100 > >| </TD>
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
	 
		if(chitieunhapplist!=null){
			chitieunhapplist.close();
		}
	
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>