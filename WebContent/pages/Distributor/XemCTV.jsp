<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.donhangctv.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IDonhangctvList obj = (IDonhangctvList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getNhapkhoRs(); %>
<% NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); %>

<%	ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[11];
		quyen = util.GetquyenNew("DonhangctvSvl", "&duyet=" + obj.getDuyetSS(), userId);
		ResultSet rs=obj.getRsxemctv();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
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
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
		<style type="text/css">

		/* Big box with list of options */
		#ajax_listOfOptions {
			position: absolute; /* Never change this one */
			width: auto; /* Width of box */
			height: 200px; /* Height of box */
			overflow: auto; /* Scrolling features */
			border: 1px solid #317082; /* Dark green border */
			background-color: #C5E8CD; /* White background color */
			color: black;
			text-align: left;
			font-size: 1.0em;
			z-index: 100;
		}
		
		#ajax_listOfOptions div {
			/* General rule for both .optionDiv and .optionDivSelected */
			margin: 1px;
			padding: 1px;
			cursor: pointer;
			font-size: 1.0em;
		}
		
		#ajax_listOfOptions .optionDiv { /* Div for each item in list */
			
		}
		
		#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
			background-color: #317082; /*mau khi di chuyen */
			color: #FFF;
		}
		
		#ajax_listOfOptions_iframe {
			background-color: #F00;
			position: absolute;
			z-index: 5;
		}
		
		form {
			display: inline;
		}
		
		#dhtmltooltip {
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
				);
		}
		
		#dhtmlpointer {
			position: absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
}
</style>
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
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].sochungtu.value = "";
	    document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	 
	 function taobaocao()
	 {
		 document.forms["ckParkForm"].action.value = "taobaocao";
		 document.forms["ckParkForm"].submit();
	 }
	 
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
     $(".select2").select2();
 });
</script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../DonhangctvSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="CTV" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="loaidonhang" value='<%= obj.getLoaidonhang() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	<% if(obj.getDuyetSS().length() <= 0) {%>
				Quản lý bán hàng > Báo cáo CTV > Báo cáo cộng tác viên
				<%} else { %>
				Quản lý bán hàng > Báo cáo CTV > Duyệt đơn hàng cộng tác viên (SS)
				<%} %>
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
                        <TD class="plainlabel" width="15%">Từ ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" name="tungay" value="<%= obj.getTungayTao() %>" class="days" maxlength="10" onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" width="15%">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   name="denngay" value="<%= obj.getDenngayTao() %>" class="days" maxlength="10" onchange="submitform();"  />
                        </TD>
                    </TR>	
                    
                    
                     <TR>
                     <TD class="plainlabel" width="15%">Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sochungtu" value="<%= obj.getSochungtu()%>"  onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onchange="submitform();" >
                           		<%if (obj.getTrangthai().equals("0")){ %>								  		
							    	<option value="0" selected>Chưa chốt </option>
							    	<option value="1">Đã chốt</option>
							    	<option value=""></option>
								<%  } else if (obj.getTrangthai().equals("1")){ %>								  		
							    	<option value="0" >Chưa chốt </option>
							    	<option value="1" selected>Đã chốt</option>
							    	<option value=""></option>
								<% } else { %>
									<option value="0" >Chưa chốt </option>
							    	<option value="1">Đã chốt</option>
							    	<option value="" selected ></option>
								<% } %>
                           </select>
                        </TD>
                                                
                    </TR> 
                     <TR>
                     <TD class="plainlabel" width="15%">Khách hàng</TD>
                        <TD class="plainlabel" colspan = "4">
                            <input type="text" name="timkiem" value="<%= obj.getTimkiem()%>"  onchange="submitform();" />
                        </TD>
                        </TR>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                             
                            <% if( quyen[Utility.XUATEXCEL] != 0 ) { %>
                            <a class="button2" href="javascript:taobaocao();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất EXCEL </a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <% } %>
                            
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Báo cáo cộng tác viên </span>&nbsp;&nbsp;
            	
                </legend>
            	
            		
					<TABLE id="table" class="tabledetail sortable" width="100%" border="1" cellspacing="1" cellpadding="4" >
						<thead>
						<TR class="tbheader">
								<!-- <TH align="center" style="width: 5%" >Order No</TH> -->
								<TH align="center" style="width: 5%" >Salesman</TH>
								<TH align="center" style="width: 15%" >Reseller code</TH>
								<TH align="center" style="width: 5%" >Reseller name</TH>
								<TH align="center" style="width: 5%" >Customer code</TH>
								<TH align="center" style="width: 5%" >Customer</TH>
								<TH align="center" style="width: 5%" >Category order</TH>
								<TH align="center" style="width: 5%" >Area name</TH>
								<TH align="center" style="width: 5%" >Locality</TH>
					
								<TH align="center" style="width: 5%">TDV</TH>
														
								<TH align="center" style="width: 5%" >Product code</TH>
								<TH align="center" style="width: 5%" >Product name</TH>
								<TH align="center" style="width: 5%" >Unit</TH>
								<TH align="center" style="width: 5%" >Quantity</TH>
								<TH align="center" style="width: 5%" >Product price</TH>
								<TH align="center" style="width: 5%" >Total amount</TH>
								
							</TR>
							
							</thead>
			                <tbody>
			                <!-- trinh duoc vien -->
			                <%if(rs!=null) while(rs.next()){ int stt=1; %>
			                	<%if(rs.getInt("stt")==1 && rs.getInt("type")==1){ %>
			                	<TR style="background-color:  #00EEEE !important ; font-size:10px; ">	
			           				<%-- <td align="center" style="width: 5%" ><%=stt %></td> --%>
									<td align="center" style="width: 5%" colspan="5" ><%=rs.getString("ddkdten") %></td>
									
									<td align="center" style="width: 5%" colspan="9" ></td>
									
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("tongdoanhso"))  %></td>
								</TR>
			                	<% stt=1;} %>
			                	<%if(rs.getInt("type")==1) {%>
			           		<TR style="background-color:  #00EEEE !important ; font-size:10px;">	
			           				<%-- <td align="center" style="width: 5%" ><%=stt %></td> --%>
									<td align="left" style="width: 5%" colspan="9" ><%=rs.getString("ddkdten") %></td>
							
									<td align="center" style="width: 5%" ><%=rs.getString("masp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tensp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("donvi") %></td>
									<td align="center" style="width: 5%"><%=rs.getString("soluong") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dongia") %></td>
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("doanhso")) %></td>
								</TR>	
							<% stt++; } if(rs.getInt("type")==2 && rs.getInt("stt")==1) { stt=1;  %>
							<!-- chanel -->
								<TR style="background-color: #FFCC66 !important; font-size:10px;">	
			           					<td align="center" style="width: 5%"   >chanel</td>
									 <td align="center" style="width: 5%"  colspan="4"></td> 
									<td align="center" style="width: 5%"  ><%=rs.getString("kenh") %></td>
									<td align="center" style="width: 5%"  ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("tongdoanhso"))  %></td>
									
									
								</TR>
			                <% } if(rs.getInt("type")==2) {  %>
			           		<TR style="background-color: #FFCC66 !important; font-size:10px;">	
			           				<%-- <td align="center" style="width: 5%" ><%=stt %></td> --%>
									<td align="center" style="width: 5%" colspan="5"  >Chanel</td>
							
									<td align="center" style="width: 5%" ><%=rs.getString("kenh") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tenkv") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dbten") %></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("masp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tensp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("donvi") %></td>
									<td align="center" style="width: 5%"><%=rs.getString("soluong") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dongia") %></td>
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("doanhso"))  %></td>
								</TR>
								<% stt++;} if (rs.getInt("type")==3 && rs.getInt("stt")==1) {%>
								<!-- khach hang cap 2 -->
								<TR style="background-color: #FF66CC !important; font-size:10px;">	
			           					<!-- <td align="center" style="width: 5%"   >STT</td> -->
									<td align="center" style="width: 5%"  ><%=rs.getString("makhcap2") %></td>
									<td align="center" style="width: 5%"  ><%=rs.getString("tenkhcap2") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" colspan="5"></td>
									<td align="center" style="width: 5%"  colspan="5"><%=formatter1.format(rs.getDouble("tongdoanhso"))  %></td>
									
								</TR>
			                 <% } if(rs.getInt("type")==3) {stt=1; %>
			           		<TR style="background-color: #FF66CC !important; font-size:10px;">	
			           				<%-- <td align="center" style="width: 5%"   ><%=stt %></td> --%>
									<td align="center" style="width: 5%"  ><%=rs.getString("makhcap2") %></td>
									<td align="center" style="width: 15%"  ><%=rs.getString("tenkhcap2") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tenkv") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dbten") %></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("masp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tensp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("donvi") %></td>
									<td align="center" style="width: 5%"><%=rs.getString("soluong") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dongia") %></td>
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("doanhso"))  %></td>
								</TR>
								<% }  if(rs.getInt("type")==4 && rs.getInt("stt")==1){ %>
									<!-- cong tac vien -->
								<TR style="background-color: #FFFF99 !important; font-size:10px;">	
			           				<%-- 	<td align="center" style="width: 5%"   ><%=stt %></td> --%>
									<td align="center" style="width: 5%"  ><%=rs.getString("makhcap2") %></td>
									<td align="center" style="width: 15%"  ><%=rs.getString("tenkhcap2") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("MACTV") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tenctv") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ><%=rs.getString("kenh") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" colspan="4"></td>
									<td align="center" style="width: 5%"  ><%=formatter1.format(rs.getDouble("tongdoanhso"))  %></td>
									
								</TR>
			               <% stt++; } if(rs.getInt("type")==4) { %>
			           		<TR style="font-size:10px;">	
			           					<%-- <td align="center" style="width: 5%"   ><%=stt %></td> --%>
									<td align="center" style="width: 5%"  ><%=rs.getString("makhcap2") %></td>
									<td align="center" style="width: 15%"  ><%=rs.getString("tenkhcap2") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("MACTV") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tenctv") %></td>
									<td align="center" style="width: 5%" ></td>
									<td align="center" style="width: 5%" ><%=rs.getString("kenh") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tenkv") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dbten") %></td>
									<td align="center" style="width: 10%" ><%=rs.getString("ddkdten") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("masp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("tensp") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("donvi") %></td>
									<td align="center" style="width: 5%"><%=rs.getString("soluong") %></td>
									<td align="center" style="width: 5%" ><%=rs.getString("dongia") %></td>
									<td align="center" style="width: 5%" ><%=formatter1.format(rs.getDouble("doanhso"))  %></td>
								</TR>
								<%stt++; } }%>
							</tbody>
                     <tfoot>
							<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="16" class="tbfooter">
								
							</tr>
</tfoot>
						</TABLE>
            	
            	
            	
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>
</html><%}%>