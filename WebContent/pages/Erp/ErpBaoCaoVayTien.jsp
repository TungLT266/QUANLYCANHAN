<%@page import="geso.traphaco.erp.servlets.baocao.*"%>
<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.text.DecimalFormat;" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% List<BaoCaoVayTienPoJo> list = obj.getListBaoCaoVayTien(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
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
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
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
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 var khoId = document.getElementById("khoId");
	
		 //alert(document.getElementById("khoTen").value);
		 
		 document.forms["erpDmhForm"].action.value = "Taomoi";
	     document.forms["erpDmhForm"].submit();
	 }
	 function submitformView(){
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 var khoId = document.getElementById("khoId");
	
		 //alert(document.getElementById("khoTen").value);
		 
		 document.forms["erpDmhForm"].action.value = "View";
	     document.forms["erpDmhForm"].submit();
	 }
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 
	</SCRIPT>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBCVayTienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý kế toán > Chức năng > Báo cáo theo dõi vay tiền
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
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.gettungay() %>" maxlength="10"  onchange="submitformView();" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" onchange="submitformView();" />
                        </TD>
                    </TR>
                    
                     <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       </fieldset>  
       
       
       <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Thông tin tài khoản vay</legend>
                <TABLE width="100%" width="100%" border="0" cellspacing="1" cellpadding="4" style="margin-top: 5px " >								                          
                    <TR class="tbheader">
                        <TH  width="15%">Số hợp đồng</TH>
                        <TH  >Tài khoản vay</TH>
                        <TH  >Ngày nhận</TH>
                        <TH >Ngày đáo hạn</TH>
                        <TH >Số tiền </TH>
                        <TH >Loại tiền </TH>
                        <TH  >Lãi suất </TH>
                        <TH  >Đã trả </TH>
                        <TH  >Ghi chú </TH>
                    </TR>
                     
                     <% 
                     String lightrow = "tblightrow";
                     String darkrow = "tbdarkrow";
                     
                     if(list.size() >0) {
                     for(int i=0 ; i< list.size(); i++){
                    	 
                    	 DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
                    	 
                    	 if (i % 2 != 0) {%>                     
                         <TR align="center" align = "left" class=<%=lightrow%> >
                     <%} else { %>
                         <TR align="center" align = "left" class=<%= darkrow%> >
                     <%}%>
               			<TD><%= list.get(i).getSoHopDong() %></TD>
               			<TD><%= list.get(i).getTaiKhoanVay() %></TD>
               			<TD><%= list.get(i).getNgayNhan() %></TD>
               			<TD><%= list.get(i).getNgayDaoHan() %></TD>
               			<TD><%= df2.format(list.get(i).getSoTien())%></TD>
               			<TD><%= list.get(i).getLoaiTien() %></TD>
               			<TD><%= list.get(i).getLaiSuat() %></TD>
               			<TD><%= df2.format(list.get(i).getDaTra()) %></TD>
               			<TD><%= list.get(i).getGhiChu() %></TD>
                 	</TR>
                    <%}} %>
                    
                    <TR>
						<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
					</TR>
                    </TABLE>                  
       </fieldset>          					                    
    	</div>
    </div>  
</div>
</form>
</body>
 
</html>
<% }%>