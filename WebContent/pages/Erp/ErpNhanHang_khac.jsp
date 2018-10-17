<%@page import="geso.traphaco.erp.beans.nhanhangkhac.IErpNhanhangList_khac"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
 
<%@ page import="java.sql.ResultSet"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% 	IErpNhanhangList_khac obj = (IErpNhanhangList_khac)session.getAttribute("obj");
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<% ResultSet dvthList = (ResultSet)obj.getDvthList(); %>
<% ResultSet nhanhangList = (ResultSet)obj.getNhList(); %>
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpNhanhang_khacSvl","",userId);
		
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SalesUp - Project</title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">

<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>   

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
	    document.forms["erpNhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpNhForm"].action.value = "Tao moi";
	    document.forms["erpNhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpNhForm"].dvth.value = "";
	   
	    document.forms["erpNhForm"].tungay.value = "";
	    document.forms["erpNhForm"].denngay.value = "";
	    document.forms["erpNhForm"].trangthai.value = "";
	    document.forms["erpNhForm"].sonhanhang.value = "";
	    
	    document.forms["erpNhForm"].mactsp.value = "";
	    document.forms["erpNhForm"].nguoitao.value = ""; 
	    document.forms["erpNhForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpNhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpNhForm"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
	<form name="erpNhForm" method="post" action="../../ErpNhanhang_khacSvl">
		<input type="hidden" name="userId" value="<%= userId %>"> 
		<input type="hidden" name="action" value="1"> 
		<input type="hidden" name="loaimh" value="<%= obj.getLoaimh() %>"> 
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> 
		<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
		<script language="javascript" type="text/javascript">
    	thongbao();
		</script>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản lý cung ứng > Quản lý tồn kho > Nhập hàng khác</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%= userTen %>
				</div>
			</div>
			<div id="cotent" style="width: 100%; float: none">
				<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset style="margin-top: 5px">
						<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">
								<TR>
									<TD class="plainlabel" width="100px">Từ ngày</TD>
									<TD width="230px" class="plainlabel">
										<input type="text" class="days" id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
									</TD>
							
									<TD class="plainlabel" width="150px">Đến ngày</TD>
									<TD class="plainlabel">
										<input type="text" class="days" id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
									</TD>
								</TR>
							
								<TR >
									<TD class="plainlabel" valign="middle">Đơn vị thực hiện</TD>
									<TD class="plainlabel" valign="middle">
										<select name="dvth" onchange="submitform()">
											<option value=""></option>
												<%
					                        		if(dvthList != null)
					                        		{
					                        			while(dvthList.next())
					                        			{  
					                        			if( dvthList.getString("pk_seq").equals(obj.getDvthId())){ %>
														<option value="<%= dvthList.getString("pk_seq") %>"
															selected="selected"><%= dvthList.getString("ten") %></option>
														<%}else { %>
														<option value="<%= dvthList.getString("pk_seq") %>"><%= dvthList.getString("ten") %></option>
														<% } } dvthList.close();
					                        		}
		                        				%>
										</select>
									</TD>
									<TD class="plainlabel" >Mã nhận hàng</TD>
									<TD  class="plainlabel">
										<input type="text" id="sonhanhang" name="sonhanhang" value="<%= obj.getSoNhanhang() %>" onchange="submitform()" />
									</TD>
								 
								</TR>
							 
							
								<TR>
									<TD class="plainlabel" valign="middle">Trạng thái</TD>
									<TD class="plainlabel" valign="middle"  >
										<select name="trangthai" onChange="submitform();">
											<% if (obj.getTrangthai().equals("1")){%>
											<option value="1" selected>Đã chốt</option>
											<option value="0">Chưa chốt</option>
											<option value="2">Hoàn tất</option>
											<option value="3">Đã xóa</option>
											<option value="4">Đã hủy</option>
											<option value=""></option>
	
											<%}else if(obj.getTrangthai().equals("0")) {%>
												<option value="0" selected>Chưa chốt</option>
												<option value="1">Đã chốt</option>
												<option value="2">Hoàn tất</option>
												<option value="3">Đã xóa</option>
												<option value="4">Đã hủy</option>
												<option value=""></option>
	
											<%}else if(obj.getTrangthai().equals("2")){%>
												<option value="2" selected>Hoàn tất</option>
												<option value="0">Chưa chốt</option>
												<option value="1">Đã chốt</option>
												<option value="3">Đã xóa</option>
												<option value="4">Đã hủy</option>
												<option value=""></option>
	
											<%}else{ if(obj.getTrangthai().equals("3")){ %>
											<option value="3" selected>Đã xóa</option>
											<option value="0">Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Hoàn tất</option>
											<option value="4">Đã hủy</option>
											<option value=""></option>
	
											<%} else { if(obj.getTrangthai().equals("4")){  %>
											<option value="4" selected>Đã hủy</option>
											<option value="0">Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Hoàn tất</option>
											<option value="3">Đã xóa</option>
											<option value=""></option>
											
											<% } else { %>
											<option value="" selected></option>
											<option value="0">Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Hoàn tất</option>
											<option value="3">Đã xóa</option>
											<option value="4">Đã hủy</option>
											<%} }} %>
									</select>
								</TD>
								
								<TD class="plainlabel" valign="middle"> </TD>
		                        <TD class="plainlabel" valign="middle">
		                           
		                        </TD> 
		                        
							</TR>
						
							<TR>
		                        <TD class="plainlabel" valign="middle" >Người tạo</TD>
		                        <TD class="plainlabel" valign="middle" >
			                       <select name="nguoitao" onchange="submitform()" >
			                           <option value=""></option>
			                            	<%
				                        		if(nguoitaoRs != null)
				                        		{
				                        			while(nguoitaoRs.next())
				                        			{  
				                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitaoIds())){ %>
				                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
				                        		 <% } } nguoitaoRs.close();
				                        		}
				                        	%>
			                       </select>
			                   	</TD>   
			                        
							    <TD class="plainlabel" >Mã Sản Phẩm</TD>
								<TD class="plainlabel">
									<input type="text" id="mactsp" name="mactsp" value="<%= obj.getMaCtSp() %>" onchange="submitform()" />
								</TD>                                          
			             	  </TR>
		                   
						   	<tr>
								<td colspan="4" class="plainlabel">
									<a class="button" href="javascript:submitform()"> 
										<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm
									</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<a class="button2" href="javascript:clearform()"> 
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
									</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</TABLE>
					</fieldset>
				</div>
			
				<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset>
						<legend>
							<span class="legendtitle"> Nhận hàng </span>&nbsp;&nbsp;
								 
									<a class="button3" href="javascript:newform()"> 
										<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
							 </a> 
						</legend>
						
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
						<TR class="tbheader">
							<TH align="center" width="7%">Mã nhận hàng</TH>
						 <TH align="center" width="7%">Mã yêu cầu </TH>
							<TH align="center" width="7%">Ngày nhận</TH>
							 
							<TH align="center" width="6%">Trạng thái</TH>
							<TH align="center" width="7%">Ngày tạo</TH>
							<TH align="center" width="8%">Người tạo</TH>
							<TH align="center" width="7%">Ngày sửa</TH>
							<TH align="center" width="8%">Người sửa</TH>
							<TH align="center" width="10%">Tác vụ</TH>
						</TR>
						<%
	                 			int m = 0;
								if(nhanhangList!=null)
								while(nhanhangList.next())
									{
										String sochungtu=nhanhangList.getString("SOCHUNGTU");
										
		                 				if((m % 2 ) == 0) {%>
										<TR class='tbdarkrow'>
										<%}else{ %>									
										<TR class='tblightrow'>
										<%} %>
										
										<TD align="center"><%= nhanhangList.getString("SOCHUNGTU") %></TD>
									 
										<TD align="center"><%= nhanhangList.getString("SOYEUCAU") %></TD>
										
										<TD align="center"><%= nhanhangList.getString("NGAYNHAN") %></TD>
									 
										<TD align="center">
										<%
				                        String trangthai = "";
										 
										
				                        String tt = nhanhangList.getString("TRANGTHAI");
				                       
				                        if(tt.equals("0"))
				                        {
	                                        %> <span>Chưa chốt</span> <% }
				                        else
				                        {
	                                        if(tt.equals("1"))
	                                        {  %> <span>Đã chốt</span> <% }
	                                        else
	                                        {
	                                            if(tt.equals("2"))
	                                            { %>
													<span>Hoàn tất</span> <% }
	                                            else
	                                            {
	                                            	if(tt.equals("3")){ %>
														<span style="color: red;">Đã xóa</span> <% 
	                                            } else { %>
	                                              <span style="color: red; font-style:italic">Đã hủy</span>	
	                                               <% } } } } %>
									</TD>
									<TD align="center"><%= nhanhangList.getString("NGAYTAO") %></TD>
									<TD align="center"><%= nhanhangList.getString("NGUOITAO") %></TD>
									<TD align="center"><%= nhanhangList.getString("NGUOISUA") %></TD>
									<TD align="center"><%= nhanhangList.getString("NGAYSUA") %></TD>
									<TD align="center">
										<% if(tt.equals("0")){
											 System.out.println("da vo tt " + tt);
											 System.out.println("quyen[2]" + quyen[2]);
											%> 
										<%if(quyen[2]!=0){ %>
											<A href="../../ErpNhanhangUpdate_khacSvl?userId=<%=userId%>&update=<%=sochungtu %>">
											<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A><%} %>&nbsp; 
										<%if(quyen[4]!=0){ %>
											<A id='chotphieu<%=sochungtu%>' href="../../ErpNhanhang_khacSvl?userId=<%=userId%>&chot=<%=sochungtu%>">
												<img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn chốt nhận hàng này?')) {return false ;}">	
											</A>
										<%} %> 
										<%if(quyen[1]!=0){ %>
											<A href="../../ErpNhanhang_khacSvl?userId=<%=userId%>&delete=<%= sochungtu %>">
											<img src="../images/Delete20.png" alt="Xóa nhận hàng" title="Xóa nhận hàng" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa nhận hàng này?')) return false;"></A>
										<% } } else 
										{
										%>
											<% if(tt.equals("1") && quyen[1]!=0){ %>
			                                	<A href = "../../ErpNhanhang_khacSvl?userId=<%=userId%>&mochot=<%= sochungtu %>"><img src="../images/unChot.png" alt="Mở nhận hàng" title="Mở chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt nhập hàng này?')) return false;"></A>
			                                <%} %>
											<% if(quyen[3]!=0){ %><A href="../../ErpNhanhangUpdate_khacSvl?userId=<%=userId%>&display=<%= sochungtu %>">
												<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
									 	<% 	} 
									  	}               			
									 	 %>
									</TD>
								</TR>
								<% m++; } %>
								
							<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<%if(obj.getNxtApprSplitting() >1) {%> 
										<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpNhForm', 1, 'view')"> &nbsp; <%}else {%>
										<img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%} %>
									<% if(obj.getNxtApprSplitting() > 1){ %> 
										<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpNhForm', 'prev')"> &nbsp; <%}else{ %> 
										<img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%} %>
									<%
										int[] listPage = obj.getNextSplittings();
										for(int i = 0; i < listPage.length; i++){
									%> <% 
											if(listPage[i] == obj.getNxtApprSplitting()){ %> 
												<a style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
											<%}else{ %> 
												<a href="javascript:View('erpNhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
											<%} %> 
											<input type="hidden" name="list" value="<%= listPage[i] %>" /> &nbsp; <%} %> 
										<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpNhForm', 'next')">
											&nbsp; <%}
											else { %> &nbsp; 
											<img alt="Trang Tiep" src="../images/next_d.gif"> &nbsp; <%} %> 
										<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
											<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpNhForm', -1, 'view')"> &nbsp; <%} %>
								</TD>
							</tr>
						</TABLE>
					</fieldset>
				</div>
			</div>
		</div>
	<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
			dropdowncontent.init('ktlist<%=k%>', "left-bottom", 250, "click");
	   
	  <%}%>
	</script>
	</form>
	<script type='text/javascript' src='../scripts/loadingv2.js'></script>
</body>

</html>
<%
	if(obj != null){
		obj.DBclose();
	}
	
	session.setAttribute("obj",null);
}%>
