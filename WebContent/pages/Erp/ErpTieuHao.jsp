 
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.tieuhao.IErpTieuHaoList"%>
 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
 
<%@ page import="java.sql.ResultSet"%>
 
<% IErpTieuHaoList obj = (IErpTieuHaoList)session.getAttribute("obj"); %>
<% ResultSet tieuhaoRs = (ResultSet)obj.getTieuHaoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUp/index.jsp");
	} else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpTieuHaoSvl","",userId);
		quyen = new int[] {1,1,1,1,1};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">

<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript"
	src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>


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

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
<SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["erpThForm"].submit();
	 }
	 function clearform()
	 {   
	   
	    document.forms["erpThForm"].tungay.value = "";
	    document.forms["erpThForm"].denngay.value = "";
	    document.forms["erpThForm"].trangthai.value = "";
	    document.forms["erpThForm"].nhanhangId.value = "";
	    document.forms["erpThForm"].tieuhaoid.value="";
	    document.forms["erpThForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpThForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpThForm"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
	<form name="erpThForm" method="post" action="../../ErpTieuHaoSvl">
		<input type="hidden" name="userId" value="<%= userId %>"> <input
			type="hidden" name="action" value="1"> <input type="hidden"
			name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting"
			value="<%= obj.getNxtApprSplitting() %>"> <input
			type="hidden" name="msg" value='<%= obj.getMsg() %>'>
		<script language="javascript" type="text/javascript">
    thongbao();
</script>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản lý mua hàng > Mua hàng trong nước > Phiếu tiêu hao</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%= userTen %>
				</div>
			</div>
			<div id="cotent" style="width: 100%; float: none">
				<div align="left"
					style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset style="margin-top: 5px">
						<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
						<TABLE width="100%" cellpadding="6px" cellspacing="0px"
							style="margin-top: 5px">
							<TR>
								<TD class="plainlabel" width="100px">Từ ngày</TD>
								<TD width="220px" class="plainlabel"><input type="text" class="days"
									id="tungay" name="tungay" value="<%= obj.getTungay() %>"
									maxlength="10" onchange="submitform()" /></TD>
							
								<TD class="plainlabel" width="100px">Đến ngày</TD>
								<TD class="plainlabel"><input type="text" class="days"
									id="denngay" name="denngay" value="<%= obj.getDenngay() %>"
									maxlength="10" onchange="submitform()" /></TD>
							</TR>
							<TR>
								<TD class="plainlabel" >Số nhận hàng</TD>
								<TD  class="plainlabel" >
									<input type="text" id="nhanhangId" name="nhanhangId" value="<%= obj.getNhanHangId() %>" onchange="submitform()" />
								</TD>
								<TD class="plainlabel" >Số tiêu hao</TD>
								<TD  class="plainlabel"  >
									<input type="text" id="tieuhaoid" name="tieuhaoid" value="<%= obj.getId() %>" onchange="submitform()" />
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" >Số đơn mua hàng</TD>
								<TD  class="plainlabel" >
									<input type="text" id="SoPo" name="SoPo" value="<%= obj.getSoPo() %>" onchange="submitform()" />
								</TD>
								<TD class="plainlabel" > </TD>
								<TD  class="plainlabel"  >
									
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="middle">Trạng thái</TD>
								<TD class="plainlabel" valign="middle" colspan="3" ><select
									name="trangthai" onChange="submitform();">
										<option value=""></option>
										<% if (obj.getTrangthai().equals("1")){%>
										<option value="1" selected>Đã chốt</option>
										<option value="0">Chưa chốt</option>
										<option value=""></option>

										<%}else if(obj.getTrangthai().equals("0")) {%>
										<option value="0" selected>Chưa chốt</option>
										<option value="1">Đã chốt</option>
										<option value=""></option>

										<% } else { %>
										<option value="" selected></option>
										<option value="0">Chưa chốt</option>
										<option value="1">Đã chốt</option>
										<%} %>
								</select></TD>
							</TR>
							<tr>
								<td colspan="4" class="plainlabel"><a class="button"
									href="javascript:submitform()"> <img style="top: -4px;"
										src="../images/Search30.png" alt="">Tìm kiếm
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
									class="button2" href="javascript:clearform()"> <img
										style="top: -4px;" src="../images/button.png" alt="">Nhập lại
								</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</TABLE>
					</fieldset>
				</div>
				<div align="left"
					style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset>
						<legend>
							<span class="legendtitle"> Phiếu tiêu hao </span>&nbsp;&nbsp;							
						</legend>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
							<TR class="tbheader">
								<TH align="center">Số tiêu hao</TH>
								<TH align="center">Sản phẩm</TH>
								<TH align="center">Số nhận hàng</TH>
								<TH align="center">Trạng thái</TH>
								<TH align="center">Ngày tạo</TH>
								<TH align="center">Người tạo</TH>
								<TH align="center">Ngày sửa</TH>
								<TH align="center">Người sửa</TH>
								<TH align="center">Tác vụ</TH>
							</TR>
							<%
                 		if(tieuhaoRs != null)
                 		{
                 			int m = 0;
                 			while(tieuhaoRs.next())
                 			{  	
                 				if((m % 2 ) == 0) {%>
									<TR class='tbdarkrow'>
							<%	} else{ %>
									<TR class='tblightrow'>
							<%	} %>
										
										<TD align="left"><%="210"+ tieuhaoRs.getString("PK_SEQ") %></TD>
										<TD align="left"><%= tieuhaoRs.getString("SPTEN") %></TD>
											<TD align="center"><%= "120" + tieuhaoRs.getString("NHANHANG_FK") %></TD>
										<TD align="center">
										<%
					                        String trangthai = "";
					                        String tt = tieuhaoRs.getString("trangthai");
					                        if(tt.equals("0")) { %> 
					                        	<span>Chưa chốt</span> 
		                               	<%	} else if(tt.equals("1")) {  %> 
		                               			<span>Đã chốt</span> 
		                                <%	}  else if(tt.equals("2")) {  %> 
		                               			<span>Đã Xóa</span> 
		                                <%	} %>
										</TD>
										
										<TD align="center"><%= tieuhaoRs.getString("NGAYTAO") %></TD>
										<TD align="left"><%= tieuhaoRs.getString("NGUOITAO") %></TD>
										<TD align="center"><%= tieuhaoRs.getString("NGAYSUA") %></TD>
										<TD align="left"><%= tieuhaoRs.getString("NGUOISUA") %></TD>
										<TD align="center">
										<% if(tt.equals("0") ) { %> 
											<%		if(quyen[2]!=0) { %>
												<A href="../../ErpTieuHaoUpdateSvl?userId=<%=userId%>&update=<%= tieuhaoRs.getString("PK_SEQ") %>">
													<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0>
												</A>
											<%		} %>&nbsp; 
											<%		if(quyen[4]!=0 && tieuhaoRs.getString("ISBOOKTIEUHAO").equals("1")) { %>
												<A id='chotphieu<%=tieuhaoRs.getString("PK_SEQ")%>' href="../../ErpTieuHaoSvl?userId=<%=userId%>&chot=<%= tieuhaoRs.getString("PK_SEQ") %>">
													<img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn chốt phiếu tiêu hao này?')) {return false ;}">
												</A><%} %> 
											<%		
										   } else if(quyen[3]!=0) { %>
												<A href="../../ErpTieuHaoUpdateSvl?userId=<%=userId%>&display=<%= tieuhaoRs.getString("PK_SEQ") %>">
													<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
												</A> 
											<%	
										   }
											%>
										</TD>
									</TR>
							<% 
								m++; 
							} 
							tieuhaoRs.close(); 
						} %>
							<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
									src="../images/first.gif" style="cursor: pointer;"
									onclick="View('erpThForm', 1, 'view')"> &nbsp; <%}else {%>
									<img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%} %>
									<% if(obj.getNxtApprSplitting() > 1){ %> <img alt="Trang Truoc"
									src="../images/prev.gif" style="cursor: pointer;"
									onclick="Prev('erpThForm', 'prev')"> &nbsp; <%}else{ %> <img
									alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%} %>

							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++) {
							%> <% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View('erpThForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;" onclick="Next('erpThForm', 'next')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
									<img alt="Trang Cuoi" src="../images/last.gif"
									style="cursor: pointer;"
									onclick="View('erpThForm', -1, 'view')"> &nbsp; <%} %>
								</TD>
							</tr>
						</TABLE>
					</fieldset>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<%
	obj.DBclose(); 
}
%>
