<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.uynhiemchi.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpUynhiemchiList obj = (IErpUynhiemchiList)session.getAttribute("obj"); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>
<% ResultSet htttList = (ResultSet)obj.getHtttList(); %>
<% ResultSet nvList = (ResultSet)obj.getNvList(); %>
<% ResultSet loaihoadonList = (ResultSet)obj.getLoaihoadonList(); %>
<% ResultSet tthdList = (ResultSet)obj.getTThoadonList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>


<%  
NumberFormat formater = new DecimalFormat("#######");
System.out.print("fmt: fmt: fmt: " + formater.format(2333432434.987));
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 

List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	

	 int[] quyen = new  int[6];
	 quyen = util.Getquyen("ErpUynhiemchiSvl","",userId);


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
   
    <script type="text/javascript" src="../scripts/jquery.js"></script>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 300px;
			border: 1px solid black;
			padding: 5px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			font-size: 1.2em;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		
  	</style>  	
  	
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
		document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].sohoadon.value = "";
	    document.forms["erpDmhForm"].sochungtu.value = "";
	    document.forms["erpDmhForm"].soPost.value = "";
	    document.forms["erpDmhForm"].loaihoadon.value = "";
	    document.forms["erpDmhForm"].nvId.value = "";
	    document.forms["erpDmhForm"].sotientt.value = "";
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
	</SCRIPT>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
	
	
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpUynhiemchiSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ &gt; Công nợ phải trả &gt; Ủy nhiệm chi
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
                        <TD class="plainlabel"  width="30%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" width="15%">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     
                    <TR>
                        <TD class="plainlabel" >Số (Post No.)</TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sohoadon" name="soPost" value="<%= obj.getSoPost() %>"  onchange="submitform()" />
                        </TD>
                         <TD class="plainlabel" >Số chứng từ </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sochungtu" name="sochungtu" value="<%= obj.getSochungtu() %>" maxlength="15" onchange="submitform()" />
                        </TD>
                    </TR>
                   <TR>
                        <%-- <TD class="plainlabel" valign="middle" >Hình thức thanh toán </TD>
                        <TD class="plainlabel" valign="middle">
                            <select id= "httt" name="httt" onchange="submitform()"  style="width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(htttList != null)
	                        		{
	                        			while(htttList.next())
	                        			{  
	                        			if( htttList.getString("pk_seq").equals(obj.getHtttId())){ %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ten") %></option>
	                        		 <% } } htttList.close();
	                        		}
	                        	%>
                            </select>
                        </TD> --%>   
                                          
                    </TR> 
                    <TR>
                    	<TD class="plainlabel" >Số hóa đơn</TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sohoadon" name="sohoadon" value="<%= obj.getSohoadon() %>"  onchange="submitform()" />
                        </TD>
                    	
                        
                        <TD class="plainlabel" valign="middle" >Nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select id = "nccId" name="nccId" onchange="submitform()"  style="width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			if( nccList.getString("pk_seq").equals(obj.getNccId())){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("nccTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("nccTen") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                   
                   <TR>
                         
                        <TD class="plainlabel" valign="middle" >Loại hóa đơn </TD> 
                        <TD class="plainlabel" valign="middle">
							 <select id = "loaihoadon" name="loaihoadon" multiple="multiple" onchange="submitform()"  style="width:200px">
                            	<option value=""></option>
                            	<%
		                        		if(loaihoadonList != null)
		                        		{
		                        			String[] tempLoaiHoaDon;
											if (obj.getLoaihoadon().indexOf(", ") >= 0) {
												tempLoaiHoaDon = new String[obj.getLoaihoadon().length()];
											tempLoaiHoaDon = obj.getLoaihoadon().split(", ");	
											} else {
												tempLoaiHoaDon = new String[1];
												tempLoaiHoaDon[0] = obj.getLoaihoadon();
											}
		                        			while(loaihoadonList.next())
		                        			{  
		                        				for (int iLoaiHoaDon = 0; iLoaiHoaDon < tempLoaiHoaDon.length; iLoaiHoaDon++) {
		                        			if( tempLoaiHoaDon[iLoaiHoaDon].equals(loaihoadonList.getString("pk_seq")) ){ %>
											<option value="<%= loaihoadonList.getString("pk_seq") %>"
												selected="selected"><%= loaihoadonList.getString("ten") %></option>
											<%}else { %>
											<option value="<%= loaihoadonList.getString("pk_seq") %>"><%= loaihoadonList.getString("ten") %></option>
											<% } } } loaihoadonList.close();
		                        		}
		                        	%>
                            </select>
                        </TD>  
                         <TD class="plainlabel" valign="middle" >Nhân viên </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select id = "nvId" name="nvId" onchange="submitform()"  style="width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nvList != null)
	                        		{
	                        			while(nvList.next())
	                        			{  
	                        			if( nvList.getString("pk_seq").equals(obj.getNvId())){ %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" selected="selected" ><%= nvList.getString("nvTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" ><%= nvList.getString("nvTen") %></option>
	                        		 <% } } nvList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                  
                    </TR> 
                    
                    <TR>
                    <TD class="plainlabel" valign="middle" >Trạng thái </TD> 
                        <TD class="plainlabel" valign="middle">
							<select name = "trangthai" id= "trangthai" style = "width:200px" onchange="submitform()">
								<%if(obj.getTrangthai().equals("0")){ %>
								<option value=""></option>
								<option value="0" selected="selected">Chưa duyệt</option>
								<option value="-1">Đã duyệt</option>
								<option value="1">Đã chi</option>
								<option value="2">Đã xóa</option>
								<option value="3">Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("-1")){ %>
								<option value=""></option>
								<option value="0" >Chưa duyệt</option>
								<option value="-1" selected="selected">Đã duyệt</option>
								<option value="1">Đã chi</option>
								<option value="2">Đã xóa</option>
								<option value="3">Đã hủy</option>
								<%} else if(obj.getTrangthai().equals("1")){ %>
								<option value=""></option>
								<option value="0" >Chưa duyệt</option>
								<option value="-1" >Đã duyệt</option>
								<option value="1" selected="selected">Đã chi</option>
								<option value="2">Đã xóa</option>
								<option value="3">Đã hủy</option>
								<%} else if(obj.getTrangthai().equals("2")){ %>
								<option value=""></option>
								<option value="0" >Chưa duyệt</option>
								<option value="-1" >Đã duyệt</option>
								<option value="1">Đã chi</option>
								<option value="2" selected="selected">Đã xóa</option>
								<option value="3">Đã hủy</option>								
								<%} else if(obj.getTrangthai().equals("3")){ %>
								<option value=""></option>
								<option value="0" >Chưa duyệt</option>
								<option value="-1" >Đã duyệt</option>
								<option value="1">Đã chi</option>
								<option value="2">Đã xóa</option>
								<option value="3" selected="selected">Đã hủy</option>								
								<%} else { %>
								<option value="" selected="selected"></option>
								<option value="0" >Chưa duyệt</option>
								<option value="-1" >Đã duyệt</option>
								<option value="1">Đã chi</option>
								<option value="2" >Đã xóa</option>
								<option value="3">Đã hủy</option>
								<%} %>
							</select>
                        </TD>  
                         
                        <TD class="plainlabel" valign="middle" >Số tiền </TD> 
                        <TD class="plainlabel" valign="middle">
							 <input type="text" id="sotientt" name="sotientt" value="<%= obj.getSotien() %>"  onchange="submitform()" />
                        </TD>                  
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
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Ủy nhiệm chi </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="6%">Số (Post No.)</TH>
	                    <TH align="center" width="6%">Số chứng từ</TH>
						<TH align="center" width="8%">Ngày ghi nhận</TH>
						<TH align="center" width="15%">Nội dung</TH>
						<TH align="center" width="12%">Đối tượng</TH>
						<TH align="center" width="8%">Số tiền</TH>
						<TH align="center" width="6%">Trạng thái</TH>
						<TH align="center" width="6%">Ngày tạo</TH>
						<TH align="center" width="6%">Người tạo</TH>
						<!-- <TH align="center" width="6%">Ngày sửa</TH> -->
						<TH align="center" width="6%">Người sửa</TH>
						<TH align="center" width="6%">Tác vụ</TH>
	                </TR>
	                
	                	<% if(tthdList!= null)  { 
								int m = 0;
								String trangthai = "";
								while (tthdList.next())
								{
									String tt = tthdList.getString("trangthai");
									
									if((m % 2 ) == 0) {%>
										<TR class="tbdarkrow">
									<%}else{ %>									
										<TR class="tblightrow">
									<%} %>
									  <% String msg= tthdList.getString("tooltip")==null?"":tthdList.getString("tooltip"); 
	                     			 %>
									<TD align="center" <% if( Integer.parseInt(tt) >= 2){ %>style='color: red;' <% }else { %> style='color: black;' <%} %>
									<% if(tthdList.getString("iskttduyet").equals("1"))  {%> onMouseover='ddrivetip("<%= msg %>" , 400)'
									onMouseout="hideddrivetip()" <%} %>><%= tthdList.getString("machungtu") %></TD>	
									
									<TD align="center" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("SOCTTUDONG") %></TD>
									
									<TD align="center" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  tthdList.getString("ngayghinhan")  %></TD>	
									
									<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("nccTen") %></TD>
									<% if(tthdList.getString("doituongId").equals("0")) {
											ResultSet nvList1 = obj.getNvList();
											
											if(nvList1 != null)
			                        		{
			                        			try
			                        			{
			                        			while(nvList1.next())
			                        			{  
			                        			if( nvList1.getString("pk_seq").equals( tthdList.getString("nhanvien_fk"))){ System.out.println("HERE111111111 :" + tthdList.getString("nhanvien_fk")); %>
			                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  nvList1.getString("nvten")  %></TD>
			                        			<%
			                        			break;
			                        			}
			                        			} nvList1.close();} catch(SQLException ex){}
			                        		}
										%>
										<%} else if(tthdList.getString("doituongId").equals("2")|| tthdList.getString("doituongId").equals("5")){
											ResultSet khList = obj.getKhachhangRs();
											if(khList != null)
			                        		{
			                        			try
			                        			{
			                        			while(khList.next())
			                        			{  
				                        			if( khList.getString("pk_seq").equals(tthdList.getString("khachhang_fk"))){ %>
				                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  khList.getString("ten")  %></TD>
				                        			<%}
				                        			break;
				                        			}  khList.close();} catch(SQLException ex){}
			                        		}
											%>
										<%} else if(tthdList.getString("doituongId").equals("3")){
											%>
				                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  tthdList.getString("nguoinhantien")  %></TD>
				                        		
										<%} else if(tthdList.getString("doituongId").equals("4")){
											%>
				                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  tthdList.getString("dvth_fk")  %></TD>
				                        		
										
										<%}else if(tthdList.getString("doituongId").equals("6")){
											obj.initDoiTuongKhacList();
											List<Erp_Item> doiTuongKhacList = obj.getDoiTuongKhacList();
											if (doiTuongKhacList != null)
											{
												for (Erp_Item item : doiTuongKhacList)
												{
													if (item.getValue().equals(tthdList.getString("doiTuongKhac_FK")))
													{
													%>
														<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  item.getName() %></TD>
													<% break;} } } 
											%>
										<%}else if(tthdList.getString("doituongId").equals("7")){
											obj.initNppList();
											List<Erp_Item> doiTuongKhacList = obj.getNppList();
											if (doiTuongKhacList != null)
											{
												for (Erp_Item item : doiTuongKhacList)
												{
													if (item.getValue().equals(tthdList.getString("khachhang_NPP_FK")))
													{
													%>
														<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  item.getName() %></TD>
													<% break;} } } 
											%>
										<%} else if(tthdList.getString("doituongId").equals("1")){
											if(tthdList.getString("nhomncccn").equals("1")){
												ResultSet nccList1 = obj.getNhomNCCRs();
												if(nccList1 != null)
				                        		{
				                        			try
				                        			{
				                        			while(nccList1.next())
				                        			{  
					                        			if( nccList1.getString("pk_seq").equals(tthdList.getString("ncc_fk"))){ %>
					                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  nccList1.getString("ten")  %></TD>
					                        			<%
					                        			break;
					                        			}
					                        			}  nccList1.close();} catch(SQLException ex){}
				                        		}
												
											}else{//! tthdList.getString("nhomncccn").equals("1")
											ResultSet nccList1 = obj.getNccList();
											if(nccList1 != null)
			                        		{
			                        			try
			                        			{
			                        			while(nccList1.next())
			                        			{  
				                        			if( nccList1.getString("pk_seq").equals(tthdList.getString("ncc_fk"))){ %>
				                        				<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%=  nccList1.getString("nccTen")  %></TD>
				                        			<%
				                        			break;
				                        			}
				                        			}  nccList1.close();} catch(SQLException ex){}
			                        		}
											}
											%>
										<%} else{ %>
										<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= "LIEN HE MS TRANG" %></TD>
										<%} %>	
									
									<TD align="right" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= formatterVND.format(Double.parseDouble(tthdList.getString("sotientt")))%></TD>		
									
									<TD align="center" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>>
									<%										
											if(tt.equals("0"))
											{
												trangthai = "Chưa duyệt";
												if(tthdList.getString("iskttduyet").equals("1"))  trangthai = "Đã duyệt";
											}
											else
											{
												if(tt.equals("1"))
												{
													trangthai = "Đã chi";
												}
												else
												{
													if(tt.equals("2"))
														trangthai = "Đã xóa";
													else
														trangthai = "Đã hủy";
												}
											}
										%> <%= trangthai %></TD>
										
									<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("NGAYTAO") %></TD>
									<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("NGUOITAO")%></TD>
									<%-- <TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("NGAYSUA") %></TD> --%>
									<TD align="left" <% if( Integer.parseInt(tt) >= 2){ %>style="color: red" <%} %>><%= tthdList.getString("NGUOISUA") %></TD>
									<TD align="center">
										<% if(tt.equals("0")){ %> 
											<%if(quyen[2]!=0){ %> 
												<%if( tthdList.getString("iskttduyet").equals("1")){ %>
											<A	href="../../ErpUynhiemchiUpdateSvl?userId=<%=userId%>&update=<%= tthdList.getString("tthdId") %>">
												<%} else{%>
											<A	href="../../ErpUynhiemchiUpdateSvl?userId=<%=userId%>&update=<%= tthdList.getString("tthdId") %>">
												<%} %>
												<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0>
											</A>&nbsp; 
										<%} %>
										
										<%if(quyen[4]!=0){ if( tthdList.getString("iskttduyet").equals("1")) { %>
										<A id='chotphieu<%=tthdList.getString("tthdId")%>' href="">
										<img src="../images/Chot.png" alt="Chốt thanh toán" width="20" height="20" title="Chốt thanh toán" border="0"
											onclick="if(!confirm('Bạn có chắc chốt phiếu thanh toán này?')) {return false ;}else{ processing('<%="chotphieu"+tthdList.getString("tthdId")%>' , '../../ErpUynhiemchiSvl?userId=<%=userId%>&chot=<%= tthdList.getString("tthdId") %>&stt=<%=obj.getNxtApprSplitting()%>');}">
										</A> <%}%> <% } %> 
										
										<%if(quyen[1]!=0){ %> 
										<A href="../../ErpUynhiemchiSvl?userId=<%=userId%>&delete=<%= tthdList.getString("tthdId") %>">
										<img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa thanh toán" width="20" height="20" border=0
											onclick="if(!confirm('Bạn có muốn xóa phiếu thanh toán này?')) return false;">
										</A> <% }%> 
										
										<%if(quyen[3]!=0){ %> 
										<A href="../../ErpUynhiemchiUpdateSvl?userId=<%=userId%>&display=<%= tthdList.getString("tthdId") %>">
										<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
										</A>&nbsp; 
										<%} %>
									
								<%}else{ %> 
									<%if(quyen[3]!=0){ %> 									
									<A href="../../ErpUynhiemchiUpdateSvl?userId=<%=userId%>&display=<%= tthdList.getString("tthdId") %>">
									<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
									</A>&nbsp; 
									
									
									<%} %>
											<%if(!tt.equals("2")&& quyen[5]!=0){ %> 	
											 <A id='unchot<%=tthdList.getString("tthdId")%>'
						       			href=""><img
						       			src="../images/unChot.png" alt="Bỏ chốt thu tiền"
						       			width="20" height="20" title="Bỏ chốt thu tiền"
						      			border="0" onclick="if(!confirm('Bạn có chắc bỏ chốt phiếu thu tiền này?')) {return false ;}else{ processing('<%="unchot"+  tthdList.getString("tthdId")%>' , '../../ErpUynhiemchiSvl?userId=<%=userId%>&unchot=<%=   tthdList.getString("tthdId") %>&trangthai=<%= tthdList.getString("trangthai") %>&stt=<%=obj.getNxtApprSplitting()%>');}">
								   </A>	 		
								   	<%} %>	
								<% } %>
								</TD>
							<% m++; } 
							}%>
							
	                
						 <tr class="tbfooter">
							<TD align="center" valign="middle" colspan="13"
								class="tbfooter">
								<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
								src="../images/first.gif" style="cursor: pointer;"
								onclick="View('erpDmhForm', 1, 'view')"> &nbsp; <%}else {%>
								<img alt="Trang Dau" src="../images/first.gif">
								&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
								alt="Trang Truoc" src="../images/prev.gif"
								style="cursor: pointer;"
								onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) -1, 'view')">
								&nbsp; <%}else{ %> <img alt="Trang Truoc"
								src="../images/prev_d.gif"> &nbsp; <%} %> 
								<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('erpDmhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
								</tr>
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<% 
try{
	if(nccList!=null){
		nccList.close();
	}
	if(htttList!=null){
		htttList.close();
	}
	if(nvList!=null){
		nvList.close();
	}
	if(loaihoadonList!=null){
		loaihoadonList.close();
	}
	if(tthdList!=null){
		tthdList.close();
	}


	session.setAttribute("ddmhBean",null);
	session.setAttribute("backAttribute",obj);
	
	
}catch(Exception er){
	
}
obj.DBclose();
}%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>