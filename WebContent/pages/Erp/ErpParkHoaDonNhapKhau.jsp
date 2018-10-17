<%@page import="org.codehaus.groovy.tools.shell.ParseCode"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.parknhapkhau.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IErpParkList obj = (IErpParkList)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
   List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<%  

String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	


		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpParkHoadonnhapkhauSvl","",userId);


obj.setNextSplittings(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color
{
	background-color: red;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	    document.forms["erpParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpParkForm"].action.value = "Tao moi";
	    document.forms["erpParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpParkForm"].ngayghinhan.value = "";
	    document.forms["erpParkForm"].sopark.value = "";
	    document.forms["erpParkForm"].ncc.value = "";
	    document.forms["erpParkForm"].nccId.value = "";
	    document.forms["erpParkForm"].trangthai.value = "";
	    document.forms["erpParkForm"].sohoadon.value = "";
	    document.forms["erpParkForm"].sonhanhang.value = "";
	    document.forms["erpParkForm"].nguoitao.value = "";
	    document.forms["erpParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpParkForm"].msg.value);
	 }
	 
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["erpParkForm"].submit();
		}
			
		setTimeout(replaces, 300);
	}

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
<form name="erpParkForm" method="post" action="../../ErpParkHoadonnhapkhauSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng &gt; Mua hàng nhập khẩu &gt; Hóa đơn nhập khẩu
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
                        <TD class="plainlabel" width="15%">Ngày ghi nhận </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="ngayghinhan" name="ngayghinhan" value="<%= obj.getNgayghinhan() %>" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%">Số chứng từ </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sopark"  name="sopark" value="<%= obj.getId() %>" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" width="15%">Số hóa đơn </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sohoadon"  name="sohoadon" value="<%= obj.getSOHOADON() %>"  onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" width="15%">Số nhập kho nhập khẩu </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sonhanhang"  name="sonhanhang" value="<%= obj.getSonhanhang() %>"  onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" width="15%">Người tạo </TD>
                        <TD class="plainlabel">
                            <input type="text" id="nguoitao"  name="nguoitao" value="<%= obj.getNGUOITAO() %>"  onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" width="15%">Số đơn mua hàng </TD>
                        <TD class="plainlabel">
                            <input type="text" id="donmuahang"  name="donmuahang" value="<%= obj.getDonMuaHang()%>"  onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        <TD class="plainlabel" valign="middle">
		                     <input type="text" id="ncc" name="ncc" value = "<%= obj.getNcc() %>" style="width: 500px"  >  
		                     <input type="hidden" id="nccId" name="nccId" value = "<%= obj.getNccId() %>" style="width: 500px"  >
                        </TD>                        
                    </TR>

                    <TR style=display:none>
                        <TD class="plainlabel" valign="middle" >Loại hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="loaihang" id="loaihang" onChange="submitform();">
								<% if(obj.getLoaihang().equals("1")) { %>
									<option value="1" selected="selected">Tài sản cố định</option>
									<option value="0">Sản phẩm nhập kho</option>
									<option value="2">Chi phí dịch vụ</option>
									<option value=""> </option>
								<% } else { if(obj.getLoaihang().equals("2")){ %>
									<option value="2"  selected="selected">Chi phí dịch vụ</option>
									<option value="1">Tài sản cố định</option>
									<option value="0">Sản phẩm nhập kho</option>
									<option value=""> </option>
								<% } else { if(obj.getLoaihang().equals("0")){ %> 
									<option value="0" selected="selected">Sản phẩm nhập kho</option>
									<option value="1">Tài sản cố định</option>
									<option value="2">Chi phí dịch vụ</option>
									<option value=""> </option>
								<%} else { %>
									<option value="" selected="selected"> </option>
									<option value="0">Sản phẩm nhập kho</option>
									<option value="1">Tài sản cố định</option>
									<option value="2">Chi phí dịch vụ</option>
								<% } } } %>
							</select>
                        </TD>                        
                    </TR> 
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onChange="submitform()">
                           		
								<% if (obj.getTrangthai().equals("1")){ %>
								  	<option value=""> </option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="1" selected>Đã chốt</option>								  
								  	<option value="2">Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4">Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("0")) { %>
								 	<option value=""> </option>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2">Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4">Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("2")) {  %>
									<option value=""> </option>									
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" selected>Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4">Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
								 <%}else if(obj.getTrangthai().equals("3")) {  %>
									<option value=""> </option>
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2">Đã duyệt</option>
								  	<option value="3" selected>Đã thanh toán</option>
								  	<option value="4">Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
								  <%}else if(obj.getTrangthai().equals("4")) {  %>
									<option value=""> </option>
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4" selected>Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
								  <%}else if(obj.getTrangthai().equals("5")) {  %>
								  	<option value=""> </option>
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="5" selected>Đã hủy</option>
							<% } else { %> 
									<option value="" > </option>
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3">Đã thanh toán</option>
								  	<option value="4">Hoàn tất</option>
								  	<option value="5">Đã hủy</option>
							<%}  %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="2" class="plainlabel">
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
            	<legend><span class="legendtitle"> Hóa đơn nhà cung cấp </span>&nbsp;&nbsp;
            				<% if(quyen[0]!=0){ %>
                		 	<a class="button3" href="javascript:newform()">
                             <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                            <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%">Số chứng từ</TH>
	                    <TH align="center" style="width: 8%">Ngày ghi nhận</TH>
	                     <TH align="center" style="width: 8%">Số hóa đơn</TH>
	                    <TH align="center" style="width: 10%">Tên nhà cung cấp</TH>
	                    <TH align="center" style="width: 8%">Trạng thái</TH>
	                    <TH align="center" style="width: 8%">Ngày tạo</TH>
	                    <TH align="center" style="width: 8%"> Người tạo </TH>
	                    <TH align="center" style="width: 8%"> Ngày sửa </TH>
	                    <TH align="center" style="width: 8%"> Người sửa </TH>
	                    <TH align="center" style="width: 8%">Tác vụ</TH>
	                </TR>
					<%
                 			int m = 0;
	                    for(int i =0; i < htList.size(); i ++)
						{ 		
							IThongTinHienThi ht = htList.get(i);
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>

									<TD align="center"><%= ht.getPREFIX()+ ht.getId() %></TD>
				                    <TD align="center"><%= ht.getNgayghinhan() %></TD>
				                    <TD align="center"><%= ht.getSohoadon() %></TD>
				                    <TD align="left"><%= ht.getTenNCC() %></TD> 	
		                    
		                    	 <TD align="center">
		                    	<%		  
		                    	    String trangthai = "";
		                    		String tt = ht.getTrangthai();
		                    		String htat = ht.getDahoantat();
		                    		String dtt = ht.getDathanhtoan();
		                    		int danhanhang = Integer.parseInt(ht.getDaNhanHang());
		                    		if(tt.equals("0")) {
		                    			trangthai = "Chưa chốt";
		                    		}
		                    		else if(tt.equals("1")) {
		                    				trangthai = "Đã chốt";
		                    		}
		                    		else if(tt.equals("2") && ! htat.equals("0") && dtt.equals("0") )
		                    		{
			                    		trangthai = "Đã duyệt";
		                    		}
		                    		else if(tt.equals("2")&& ! htat.equals("0") && dtt.equals("1"))
		                    		{
			                    		trangthai = "Đã thanh toán";
		                    		}
		                    		else if( (tt.equals("2") && htat.equals("0")) || tt.equals("3"))
		                    		{
			                    		trangthai = "Hoàn tất";
		                    		}
		                    		else if(tt.equals("4")){
		                    			trangthai = "Đã hủy";
		                    		}
		                    		
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   	
														                                    
 					     	<TD align="center"><%= ht.getNgaytao() %></TD>	
		                    <TD align="left"><%=  ht.getNguoitao() %></TD>
         					<TD align="center"><%=  ht.getNgaysua()  %></TD>
							<TD align="left"><%= ht.getNguoisua() %></TD> 
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                   		 <%if(quyen[2]!=0){ %>
                                <A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&update=<%= ht.getId()%>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                	
                                <%} %>
                                <%if(quyen[4]!=0){ %>
                                 <A id='chotphieu<%= ht.getId()%>'
							       href=""><img
							       src="../images/Chot.png" alt="Chốt"
							       width="20" height="20" title="Chốt"
							       border="0" onclick="if(!confirm('Bạn có muốn chốt ghi nhận này?')) {return false ;}else{ processing('<%="chotphieu"+ ht.getId()%>' , '../../ErpParkHoadonnhapkhauSvl?userId=<%=userId%>&chot=<%= ht.getId() %>');}"  >
								</A>											     
								<%} %>
                                <%if(quyen[1]!=0){ %>
                                     <A href = "../../ErpParkHoadonnhapkhauSvl?userId=<%=userId%>&delete=<%= ht.getId() %>&poId=<%= ht.getId() %>"><img src="../images/Delete20.png" alt="Xóa Chứng từ" title="Xóa Chứng từ" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa Chứng từ này?')) return false;"></A>
                                <%} %>									
		                    <%}else{ if(tt.equals("1")){ %>
		                    	<A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;	
		                    	 <%if(quyen[4]!=0){ %>
                                 <A id='chotphieu<%= ht.getId()%>'
							       href=""><img
							      src="../images/unChot.png" alt="Bỏ chốt" title="Bỏ chốt"
									width="20" height="20" longdesc="Bỏ chốt"							
							       border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt ghi nhận này?')) {return false ;}else{ processing('<%="chotphieu"+ ht.getId()%>' , '../../ErpParkHoadonnhapkhauSvl?userId=<%=userId%>&unchot=<%= ht.getId()%>');}"  >
								</A>
								<%} %>
		                    	 
		                    <%-- <% if(danhanhang ==0) { %>
		                    	<A href="../../ErpNhanhangnhapkhau_GiaySvl?userId=<%=userId%>&convert=<%= ht.getId()  %>&NCCId=<%= ht.getNCCId()  %>&hoadonnccId=<%= ht.getHoadonId()  %>&loaihd=<%= ht.getLoaiHd()  %>">
								<IMG src="../images/Next.png" alt="Chuyển thành nhận hàng" title="Chuyển thành nhận hàng" border=0></A>&nbsp;&nbsp;
								
		                  	 <% } %> --%>
		                   
		                   
		                    <%} else if(tt.equals("2")) { %>
		                    	
		                    	<%-- <% if(danhanhang ==0) { %>
		                    	<A href="../../ErpNhanhangnhapkhau_GiaySvl?userId=<%=userId%>&convert=<%= ht.getId()  %>&NCCId=<%= ht.getNCCId()  %>&hoadonnccId=<%= ht.getHoadonId()  %>&loaihd=<%= ht.getLoaiHd()  %>">
								<IMG src="../images/Next.png" alt="Chuyển thành nhận hàng" title="Chuyển thành nhận hàng" border=0></A>&nbsp;&nbsp;
								
		                  	 <% } %> --%>
		                    	
		                    	<A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                   <% } 
		                    else { %>
		                    	<A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&display=<%= ht.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    	<% }
		                    } %>
		                    </TD>
		                </TR>
                     <% m++; } %>
					 
					 <tr class="tbfooter">
							<TD align="center" valign="middle" colspan="13"
								class="tbfooter">
								<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
								src="../images/first.gif" style="cursor: pointer;"
								onclick="View('erpParkForm', 1, 'view')"> &nbsp; <%}else {%>
								<img alt="Trang Dau" src="../images/first.gif">
								&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
								alt="Trang Truoc" src="../images/prev.gif"
								style="cursor: pointer;"
								onclick="View('erpParkForm', eval(erpParkForm.nxtApprSplitting.value) -1, 'view')">
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
								href="javascript:View('erpParkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
								<%} %> <input type="hidden" name="list"
								value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
								style="cursor: pointer;"
								onclick="View('erpParkForm', eval(erpParkForm.nxtApprSplitting.value) +1, 'view')">
								&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
								src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
								<img alt="Trang Cuoi" src="../images/last.gif">
								&nbsp; <%}else{ %> <img alt="Trang Cuoi"
								src="../images/last.gif" style="cursor: pointer;"
								onclick="View('erpParkForm', -1, 'view')"> &nbsp; <%} %>
							</TD>
					</tr>
					  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
<script type="text/javascript">
	jQuery(function()
	{		
		$("#ncc").autocomplete("ErpNhaCungCapList.jsp");
		replaces();
	});	
	
	 <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
	  
</script>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>

<%
try{

 	session.setAttribute("obj",null);
	obj.close(); 
}catch(Exception er){
	
}
}
%>
