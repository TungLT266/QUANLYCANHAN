<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.xoakhachhangtt.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpXoakhachhangttList obj = (IErpXoakhachhangttList)session.getAttribute("obj"); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>
<% ResultSet htttList = (ResultSet)obj.getHtttList(); %>
<% ResultSet tthdList = (ResultSet)obj.getTThoadonList(); %>
<% ResultSet xnttList = (ResultSet)obj.getxnttList(); %>
<% ResultSet kbhList = (ResultSet)obj.getKbhRs(); %>
<% ResultSet nhomkhList = (ResultSet)obj.getNhomkhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  

	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
	NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpXoakhachhangttSvl","",userId);
 obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TraphacoERP - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
      <script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

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
   
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
	$(document).ready(function() {
	 
	    var div = $('#headerKH');
	    var start = $(div).offset().top;
	 
	    $.event.add(window, "scroll", function() {
	        var p = $(window).scrollTop();
	        $(div).css('position',((p)>start) ? 'fixed' : 'static');
	        $(div).css('top',((p)>start) ? '0px' : '');
	    });
	 
	});
	</script>
	
	<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>
	<script type="text/javascript">
            $(function() {
                $('#tableKH').scrollTableBody({rowsToDisplay:10});
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
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["erpDmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "Tao moi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].nppId.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].maphieu.value = "";
	    document.forms["erpDmhForm"].kbhId.value = "";
	    document.forms["erpDmhForm"].nhomkhId.value = "";
	    document.forms["erpDmhForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	 function duyetform(Id)
	 {
	 	 if(!confirm('Bạn có chắc chốt cấn trừ công nợ khách hàng này?')) 
	 	 {
	 		 return false ;
	 	 }
	 	 
	 	 document.forms['erpDmhForm'].chungtu.value = Id;
	 	 document.forms['erpDmhForm'].action.value= 'chot';
	 	 document.forms['erpDmhForm'].submit();
	 }
	 
	 function xoaform(Id)
	 {
	 	 if(!confirm('Bạn có chắc Xóa phiếu cấn trừ công nợ khách hàng này?')) 
	 	 {
	 		 return false ;
	 	 }
	 	 
	 	 document.forms['erpDmhForm'].chungtu.value = Id;
	 	 document.forms['erpDmhForm'].action.value= 'delete';
	 	 document.forms['erpDmhForm'].submit();
	 }
	</SCRIPT>
</head>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

<body>
<form name="erpDmhForm" method="post" action="../../ErpXoakhachhangttSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="chungtu" id="chungtu" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải thu > Xóa nợ khách hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                   		 <TD class="plainlabel" width="15%" >Mã phiếu </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="maphieu" name="maphieu" value="<%= obj.getMaPhieu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" valign="middle" >Khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nppId" id="nppId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			if( nccList.getString("pk_seq").equals(obj.getNccId())){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nppTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nppTen") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                     <TR>
                   		 <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="kbhId" id="kbhId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(kbhList != null)
	                        		{
	                        			while(kbhList.next())
	                        			{  
	                        			if( kbhList.getString("pk_seq").equals(obj.getKbhId())){ %>
	                        				<option value="<%= kbhList.getString("pk_seq") %>" selected="selected" ><%= kbhList.getString("diengiai") %></option>
	                        			<%}else { %>
	                        				<option value="<%= kbhList.getString("pk_seq") %>" ><%= kbhList.getString("diengiai") %></option>
	                        		 <% } } kbhList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>     
                        <TD class="plainlabel" valign="middle" >Nhóm khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nhomkhId" id="nhomkhId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nhomkhList != null)
	                        		{
	                        			while(nhomkhList.next())
	                        			{  
	                        			if( nhomkhList.getString("pk_seq").equals(obj.getNhomkhId())){ %>
	                        				<option value="<%= nhomkhList.getString("pk_seq") %>" selected="selected" ><%= nhomkhList.getString("ma") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nhomkhList.getString("pk_seq") %>" ><%= nhomkhList.getString("ma") %></option>
	                        		 <% } } nhomkhList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Số tiền</TD>
                        <TD class="plainlabel" valign="middle">
                         <input type="text" class="days" id="sotien" name="sotien" value="<%= obj.getSotien() %>" maxlength="10" onchange="submitform()" />
                        </TD>  
                        <TD class="plainlabel" valign="middle" ></TD>   
                        <TD class="plainlabel" valign="middle" ></TD>  
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
    	
    	 <%-- <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    	 	<div id = "headerKH" style="width:100%; "> 
    	 		<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
    	 			<TR class="tbheader">
	    	 			<TH align="left" style="width:10%; " >&nbsp;&nbsp;&nbsp;&nbsp; STT</TH>
	    	 			<TH align="left" style="width:30%; ">Tên khách hàng</TH>
		                <TH align="left" style="width:12%; "> &nbsp;&nbsp; Số tiền</TH>
		                <TH align="center" style="width:10%; ">Tác vụ</TH>
	    	 		</TR>
    	 		</TABLE>
    	 	</div>
	    	 <TABLE id ="tableKH" width="100%" border="0" cellspacing="1" cellpadding="4">
	    	 		
	    	 		<%
						int e = 0;		               
						String lightrow = "tblightrow";
						String darkrow = "tbdarkrow";
						if(xnttList!=null){
						while (xnttList.next()){
							if (e % 2 != 0) {%>						
								<TR class= <%=lightrow%> >
							<%} else {%>
								<TR class= <%= darkrow%> >
							<%}%>

							<TD align="center" style="width:8%; " ><%= e+1 %></TD>
							<TD align="left" style="width:50%; "><%=xnttList.getString("MA") + ',' +  ' ' +  xnttList.getString("TEN") %></TD>
							<TD align="right" style="width:18%; "><%= formatter.format(xnttList.getDouble("SOTIEN"))%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
							<TD align="right"> 
								<A href = "../../ErpXoakhachhangttUpdateSvl?userId=<%=userId%>&khId=<%=xnttList.getString("PK_SEQ") %>&loai=<%= 0 %>&Tao moi"><IMG src="../images/add.png" width="20" alt="Tạo mới" title="Tạo mới" border=0></A>&nbsp;										
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</TD>
						</TR>
					<% 	e++;
					}}%>
	    	 </TABLE>
    	 </div>
    	  --%>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Xóa nợ khách hàng </span>&nbsp;&nbsp;
            	
					<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã phiếu</TH>
	                    <TH align="center">Ngày chứng từ</TH>
	                    <!-- <TH align="center">Khách hàng</TH> -->
	                    <TH align="center">Số chứng từ </TH>
	                    <TH align="center">Đối tượng</TH>
	                    <TH align="center">Thực thu</TH>
	                    <TH align="center">Diễn giải</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center">Người tạo </TH>
<!-- 	                    <TH align="center"> Ngày sửa </TH> -->
<!-- 	                    <TH align="center"> Người sửa </TH> -->

	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
							int m = 0;
							for(int i =0; i < htList.size(); i ++)
							{
								IThongTinHienThi ht = htList.get(i);
									if((m % 2 ) == 0) {%>
		                         		<TR class="tbdarkrow">
			                     <%}else{ %>
			                          	<TR class="tblightrow">
			                        <%} %>
	
									<TD align="center"><%= ht.getId() %></TD>
									<TD align="center"><%= ht.getNgaychungtu() %></TD>
										<TD align="center"><%= ht.getSochungtu() %></TD>
									<TD align="center"><%= ht.gettendoituong() %></TD>
									<TD align="center"><%= formatterVND.format(Double.parseDouble(ht.getSOTIENVND())) %></TD>
									<TD align="center"><%= ht.getGhichu() %></TD>
									<%-- <TD align="left"><%= ht.gettendoituong() %></TD> --%>
									<TD align="left" <%if(ht.getTrangthai().equals("2")) {%> style="color: red" <%} %>>
										<%
											String trangthai = "";
											String tt = ht.getTrangthai();
											if(tt.equals("0"))
												trangthai = "Chưa chốt";
											else
											{
												if(tt.equals("1"))
												{
													trangthai = "Đã chốt";
												}
												else
												{
													if(tt.equals("2"))
														trangthai = "Đã xóa";
													else
														trangthai = "Đã hủy";
												}
											}
										%>
										<%= trangthai %>
									</TD>
									<TD align="left"><%= ht.getNGAYTAO() %></TD>
									<TD align="left"><%= ht.getNGUOITAO()%></TD>
<%-- 									<TD align="left"><%= ht.getNGAYSUA() %></TD> --%>
<%-- 									<TD align="left"><%= ht.getNGUOISUA() %></TD> --%>
									<TD align="center"> 
				                    <% if(tt.equals("0")){ %>
				                    
										<%if(quyen[1]!=0){ %>
		                                <A href = "../../ErpXoakhachhangttUpdateSvl?userId=<%=userId%>&update=<%= ht.getId() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
		                               <%} %>
		                               
									<%if(quyen[4]!=0){ %>
		                          <%--        <A id='chotphieu<%= ht.getId()%>'
							       			href=""><img
							       			src="../images/Chot.png" alt="Chốt xóa khách hàng trá trước"
							       			width="20" height="20" title="Chốt xóa khách hàng trá trước"
							      			border="0" onclick="if(!confirm('Bạn có chắc chốt xóa khách hàng trá trước này?')) {return false ;}else{ processing('<%="chotphieu"+ht.getId()%>' , '../../ErpXoakhachhangttSvl?userId=<%=userId%>&chot=<%= ht.getId() %>');}"  >
										 </A> --%>
										 
										 <A href="javascript:duyetform(<%= ht.getId() %>);" >
										 	<img  src="../images/Chot.png" alt="Chốt xóa khách hàng trá trước" width="20" height="20"  border='0' title="Chốt xóa khách hàng trá trước"	 >
										</A>
										 <%} %> 
		                                
									<%if(quyen[1]!=0){ %>
		                                <%-- <A href = "../../ErpXoakhachhangttSvl?userId=<%=userId%>&delete=<%= ht.getId()%>"><img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa thanh toán" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa khách hàng trả trước này?')) return false;"></A> --%>
		                                
		                                <A href="javascript:xoaform(<%= ht.getId() %>);" >
										 	<img  src="../images/Delete20.png" alt="Xóa khách hàng trá trước" width="20" height="20"  border='0' title="Xóa khách hàng trá trước"	 >
										</A>
										
		                                <%} %>	
		                           <%--      <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
					                                 		if(ktList.size() > 0)
										               		 {							                       	 	
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>									                        			
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<%if(kt.getSotien().trim().length()>0) {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
												                            	<%} %>
												                            </td>	
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>	 --%>							
				                    <%}else if(tt.equals("1")){ %>
				                    	<A href = "../../ErpXoakhachhangttUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				                    	
				                    <%-- 	<A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m%>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
													
												<DIV id="subcontentKT<%=m%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="90%" align="center">
							                        <tr>
								                        <th width="200px">Nợ/Có</th>
							                            <th width="150px">Số hiệu tài khoản</th>
							                            <th width="200px">Số tiền</th>
							                            <th width="150px">Đối tượng</th>
							                            <th width="150px">Trung tâm CP</th>	
							                            <th width="150px">Trung tâm DT</th>									                       
							                        </tr>
	                        
						                            <% 		List<IDinhKhoanKeToan> ktList = ht.getLayDinhkhoanKT();
					                                 		if(ktList.size() > 0)
										               		 {						                       	 	
								                        		for(int sd = 0; sd < ktList.size(); sd++)
								                        		{
								                        			IDinhKhoanKeToan kt = ktList.get(sd);
									                        		%>
									                        			<tr>									                        			
									                        				<td>
									                        					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
									                        				</td>
												                            <td>											                            	
												                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
												                            </td>
												                            <td>
												                            	<%if(kt.getSotien().trim().length()>0) {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
												                            	<%} else {%>
												                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
												                            	<%} %>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
												                            </td>
												                            <td>
												                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
												                            </td>
												                        </tr>
									                        <%  }}
								        
								                         %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     		<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m%>')">Hoàn tất</a>
								                     </div>
	                							</DIV>	 --%>				
	                							 
				                    <%} else{ %>
				                    	<A href = "../../ErpXoakhachhangttUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				                    <%} %>
				                    </TD>
				                    </TR>
								<% m++;}
					%>
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<%-- <script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script> --%>

<% 


try{
	
	
	if(tthdList!=null){ tthdList.close(); }
	
	if(nccList!=null){ nccList.close();	}
	
	if(htttList!=null){	htttList.close(); }
	
	if(xnttList!=null){ xnttList.close(); }
	
	if(kbhList!=null){ 	kbhList.close(); }
	
	if(nhomkhList!=null){ nhomkhList.close();}
	
	obj.DBclose(); 
	
}catch(Exception er){
	
}
	}
%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>