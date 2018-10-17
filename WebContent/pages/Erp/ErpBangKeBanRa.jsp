<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>

<%@ page  import = "java.sql.ResultSet" %>

<%  
	System.out.print("");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<%  String userId = (String) session.getAttribute("userId");  %>
<%  String userTen = (String) session.getAttribute("userTen");   
	
	ResultSet ctyRs = obj.getCtyRs();
	ResultSet nhomkhachhangRs = obj.getRsNhomKh();
	String ctyIds = obj.getErpCongtyId();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Traphaco - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>

			<script type="text/javascript" src="../scripts/jquery.min.js"></script>
		<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
		<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


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
	 
	 function Change()
	 {   
	
	    document.forms["erpDmhForm"].action.value="change";
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function submitform()
	 {   
		if(!kiemTra()){
			return false;
		}
			
	    document.forms["erpDmhForm"].action.value="Taomoi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function kiemTra(){
		var congTy = document.getElementById("ctyIds").value;
		if (congTy == "")
		{
			alert("Vui lòng chọn công ty");return;
		}
		 
		/* var tuNgay = document.getElementById("tuNgay");
		var denNgay = document.getElementById("denNgay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true; */
		var thoiDiem = document.getElementById("thoiDiem").value.trim(); 
		var year = document.getElementById("year").value.trim(); 
		if (thoiDiem.length ==0){
			alert ('Vui lòng nhập tháng lấy báo cáo .');
			return false;
		}
		if (year.length ==0){
			alert ('Vui lòng nhập năm lấy báo cáo .');
			return false;
		}
		var thoiDiemTemp = parseInt(thoiDiem);
		if(thoiDiemTemp < 10 && year == 2016){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
	}
	 
	 
	 
	 function submitform1()
	 {   
	
	    document.forms["erpDmhForm"].action.value="submit";
	    document.forms["erpDmhForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	
	</SCRIPT>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<body>
<form name="erpDmhForm" method="post" action="../../BcBangKeBanRa">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Báo cáo quản trị &gt; Thuế &gt; Hóa đơn bán ra
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
        	 <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" COLSPAN = 3 >
                            <SELECT NAME = "ctyIds"  id = "ctyIds"   style = "width:300px;size:5">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
					<%	if(ctyRs != null){ 
						
							while (ctyRs.next()) {
								String selected = "";
								if (ctyRs.getString(1).trim().equals(obj.getErpCongtyId())){
									selected = "selected";
								}
						
					%>
						<option value="<%=ctyRs.getString("PK_SEQ") %>" <%=selected %>><%=ctyRs.getString("MA") %> - <%=ctyRs.getString("TEN") %></option>
								
					<% 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
				<%} }%>
				
				
				
				      <TR>
                        <TD class="plainlabel" width="15%">Nhóm khách hàng </TD>
                        <TD class="plainlabel" COLSPAN = 3 >
                            <SELECT NAME = "nhomkhachhang"  id = "nhomkhachhang"   style = "width:300px;size:5">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
					<%	if(nhomkhachhangRs != null){ 
						
							while (nhomkhachhangRs.next()) {
								String selected = "";
								if (nhomkhachhangRs.getString(1).trim().equals(obj.getNhomKhId())){
									selected = "selected";
								}
						
					%>
						<option value="<%=nhomkhachhangRs.getString("PK_SEQ") %>" <%=selected %>><%=nhomkhachhangRs.getString("DIENGIAI") %></option>
								
					<% 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
				<%}%>

                      
                						                          
                    <TR>
						<TD width="19%" class="plainlabel">Từ ngày</TD>
						<TD class="plainlabel" ><INPUT name="tuNgay" id="tuNgay" class="days" type="text" size="30" value='<%=obj.gettungay()%>' readonly="readonly"></TD>
					
						<TD width="19%" class="plainlabel">Đến ngày</TD>
						<TD class="plainlabel" ><INPUT name="denNgay" id="denNgay" class="days" type="text" size="30" value='<%=obj.getdenngay()%>' readonly="readonly"></TD>
					</TR>
					<tr style="display: none">
                   					<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<select id="year" name="year">
									  <script>
									  var myDate = new Date();
									  var year = myDate.getFullYear();
								      var myYear = <%=obj.getYear()%>;
									  for(var i = 2010; i < year+1; i++){
										  selected = (myYear == i) ? "selected" : "";
										  document.write('<option value="'+i+'" '+selected+'>'+i+'</option>');
									  }
									  </script>
									</select>
																			
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD class="plainlabel">
										<SELECT name = "thoiDiem" id="thoiDiem" style="width:40%;">
										<script>
											var month = <%=obj.getMonth()%>;
										  for(var i = 1; i < 13; i++){
											  selected = (month == i) ? 'selected' : '';
											  document.write('<option value="'+i+'" '+selected+'>Tháng '+i+'</option>');
										  }
										  </script>

										</SELECT>
									
									</TD>
								</TR>
								       
                           
                     <tr>
                        <td colspan="4" class="plainlabel">
                           <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       				</fieldset>            					                    
    				</div>
    	  				   
    </div>  
</div>
</form>
</body>
</html>
<%obj.DBclose();%>