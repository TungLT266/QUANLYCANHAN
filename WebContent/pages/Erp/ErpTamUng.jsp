<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.tamung.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% IErpTamUngList tuList = (IErpTamUngList)session.getAttribute("tuList"); %>
<% ResultSet rsTienTe =tuList.getRsTienTe(); %>
<% ResultSet rsTamUng =tuList.getRsTamUng(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpTamUngSvl","",userId);
		ResultSet dvthList = (ResultSet)tuList.getDvthList(); 
		ResultSet nguoitaoRs = (ResultSet)tuList.getNguoitaoRs(); 
			
		tuList.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
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
		document.forms["erpDmhForm"].action.value = "new";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    
	    document.forms["erpDmhForm"].TuNgay.value = "";
	    document.forms["erpDmhForm"].DenNgay.value = "";
	    document.forms["erpDmhForm"].DoiTuongTamUng.value = "";
	    document.forms["erpDmhForm"].SoTienTamUng.value="";
	    document.forms["erpDmhForm"].TienTeId.value="";
	    document.forms["erpDmhForm"].trangthai.value="";
	    document.forms["erpDmhForm"].sochungtu.value="";
	    document.forms["erpDmhForm"].dvth.value="";
	    document.forms["erpDmhForm"].nguoitao.value="";
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
<form name="erpDmhForm" method="post" action="../../ErpTamUngSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= tuList.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= tuList.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= tuList.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ &gt; Công nợ phải trả &gt; Đề nghị tạm ứng
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
                                   id="TuNgay" name="TuNgay" value="<%= tuList.getTuNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                  
                        <TD class="plainlabel" width="15%">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days"
                                   id="DenNgay" name="DenNgay" value="<%= tuList.getDenNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                          		<TD class="plainlabel" valign="middle" >Đối tượng </TD>
                          		<TD class="plainlabel" valign="middle"  >
									<select name="DoiTuongTamUng" id="DoiTuongTamUng" style="width: 200px" onchange="submitform()">
										<option value=""></option>
										<%if(tuList.getDoiTuongTamUng().equals("1")){ %>
										<option value="1" selected="selected"> Nhân viên</option>
										<option value="2">Nhà cung cấp</option>
										<%} else if(tuList.getDoiTuongTamUng().equals("2")){%>
										<option value="2" selected="selected"> Nhà cung cấp</option>
										<option value="1">Nhân viên</option>
										<%} else {%>
										<option value="1">Nhân viên</option>
										<option value="2" > Nhà cung cấp</option>
										<%} %>
										</select>
								</TD>   
								<%if(tuList.getDoiTuongTamUng().equals("1")) {%>
                       	 
		                        	<TD class="plainlabel" valign="middle" >Nhân viên /NCC </TD>   
		                        	<TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="NhanVienId" id="NhanVienId" value="<%= tuList.getTenHienThi() %>"  style="width:400px" >
		                        	</TD>   
		                        
		                       	<%}else if(tuList.getDoiTuongTamUng().equals("2")){ %>
		                       	 
		                        	<TD class="plainlabel" valign="middle"  >Nhân viên /NCC </TD>   
		                        	<TD class="plainlabel" valign="middle"  >
		                       		<input type="text" name="NccId" id="NccId" value="<%= tuList.getTenHienThi() %>"  style="width:400px" >
		                       		
		                       <%} else {%>
		                       <TD class="plainlabel" valign="middle"  ></TD>   
		                       <TD class="plainlabel" valign="middle"  ></TD>   
		                       
		                       <% }%>        
		              </TR> 
		                   	
		                  
		                 <%--  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Nhân viên /NCC </TD>   
		                        <TD class="plainlabel" valign="middle"  >		                   
		                        	<input type="text" name="NhanVienId" id="NhanVienId" value=""  style="width:200px" >
		                        <%} %> 
		                        </TD>          
		                  </TR>  --%>
                    
                    <TR>
                        <TD class="plainlabel" >Số tiền tạm ứng </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="SoTienTamUng" name="SoTienTamUng" value="<%= tuList.getSoTienTamUng() %>"  onchange="submitform()" />
                        </TD>
                    
                        <TD class="plainlabel" >Tiền tệ </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="TienTeId" name="TienTeId" value="<%= tuList.getTienTeId()%>"  onchange="submitform()" />
                        </TD>
                    </TR>
                    
                    <TR>
                      	<TD class="plainlabel" valign="middle" >Trạng thái </TD>
                      	<TD class="plainlabel" valign="middle" >
							<select name="trangthai" id="trangthai"  style="width: 200px" onchange="submitform()">
								<option value=""></option>
								<option value="0" <%= tuList.getTrangThai().equals("0") ? "selected=\"selected\"" : "" %>>Chưa chốt</option>								
								<option value="1" <%= tuList.getTrangThai().equals("1") ? "selected=\"selected\"" : "" %>>Đã chốt</option>
								<option value="2" <%= tuList.getTrangThai().equals("2") ? "selected=\"selected\"" : "" %>>Đã xóa</option>
								<option value="3" <%= tuList.getTrangThai().equals("3") ? "selected=\"selected\"" : "" %>>Đã thanh toán</option>

							</select>
						</TD>   
						
						<TD class="plainlabel" >Số chứng từ </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sochungtu" name="sochungtu" value="<%= tuList.getSochungtu()%>"  onchange="submitform()" />
                        </TD>
                               
                    </TR> 

                    <TR>
                        <TD class="plainlabel" valign="middle" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="dvth" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("pk_seq").equals(tuList.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                        <TD class="plainlabel" valign="middle">Người tạo</TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nguoitao" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(nguoitaoRs != null)
	                        		{
	                        			while(nguoitaoRs.next())
	                        			{  
	                        			if( nguoitaoRs.getString("pk_seq").equals(tuList.getNguoitaoIds())){ %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
	                        		 <% } } nguoitaoRs.close();
	                        		}
	                        	%>
                            </select>
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
            	<legend><span class="legendtitle">Đề nghị tạm ứng </span>&nbsp;&nbsp;
            	
					<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width = "7%">Số chứng từ</TH>
	                    <TH align="center" width = "7%">Ngày tạm ứng</TH>
	                    <TH align="center" width = "15%">Nhân viên/NCC</TH>
	                    <TH align="center" width = "7%">Số tiền</TH>
	                   <!--  <TH align="center">Thời gian hoàn ứng</TH> -->
	                     <TH align="center" width = "10%">Lý do tạm ứng</TH> 
	                    <TH align="center" width = "7%">Ngày tạo</TH>
	                    <TH align="center" width = "7%">Người tạo</TH>
	                    <!-- <TH align="center">Ngày sửa</TH> -->
	                    <TH align="center" width = "7%">Người sửa</TH>
	                    <TH align="center" width = "7%">Trạng thái</TH>
	                    <TH align="center" width = "7%">Tác vụ</TH>
	                </TR>
					<%
					  NumberFormat formatter = new DecimalFormat("#,###,###");
						if(rsTamUng != null)
						{
							try
							{
								int m = 0;
								while(rsTamUng.next())
								{
									double sotienTU = 0;
									
									String isthanhtoan = "";
									isthanhtoan = rsTamUng.getString("isThanhToan");
									
									String ISDACHOT = rsTamUng.getString("ISDACHOT");
									System.out.println("aaaaaaa"+ISDACHOT);
		                    		String ISTP = rsTamUng.getString("ISTP");
		                    		System.out.println("bbbbbbbb"+ISTP);
		                    		String ISKTV = rsTamUng.getString("ISKTV");
		                    		String ISKTT = rsTamUng.getString("ISKTT");
		                    		String ISTHANHTOAN = rsTamUng.getString("ISTHANHTOAN");
		                    		String ISHOANTAT = rsTamUng.getString("ISHOANTAT");
									
									if(rsTamUng.getString("SoTienTamUng") != null)
										sotienTU = rsTamUng.getDouble("SoTienTamUng");
									
									if((m % 2 ) == 0 & rsTamUng.getString("trangthai").equals("2")) {%>
		                         		<TR class="tbdarkrow" style="color: red;">
		                         	<%}else if((m % 2 ) != 0 & rsTamUng.getString("trangthai").equals("2")) { %>
		                         		<TR class="tblightrow" style="color: red;">
		                         	<%}else if((m % 2 ) == 0) { %>
		                         		<TR class="tbdarkrow" >
			                     	<%}else{ %>
			                          	<TR class="tblightrow">
			                        <%} %>
									<TD align="center" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= rsTamUng.getString("PK_SEQ") %></TD>
									<TD align="center" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= rsTamUng.getString("NgayTamUng") %></TD>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %>>
										<pre style="font-family: Arial; word-wrap: break-word; white-space: pre-wrap;"><%= rsTamUng.getString("NhanVien") %></pre>
									</TD>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= formatter.format(sotienTU) %></TD>
									<%-- <TD align="left"><%= rsTamUng.getString("ThoiGianHoanUng") %></TD> --%>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %> ><%= rsTamUng.getString("LYDOTAMUNG") %></TD>
									<%-- <TD align="left"><%= rsTamUng.getString("TienTe") %></TD> --%>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= rsTamUng.getString("NgayTao") %></TD>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= rsTamUng.getString("NguoiTao") %></TD>
									<%-- <TD align="left"><%= rsTamUng.getString("NgaySua") %></TD> --%>
									<TD align="left" <%if(rsTamUng.getString("trangthai").equals("2")) {%> style="color: red" <%} %>><%= rsTamUng.getString("NguoiSua") %></TD>
									<TD align="left">
										<%
											String trangthai = "";
											String tt = rsTamUng.getString("trangthai");
											if(tt.equals("0"))
											{
												trangthai = "Chưa chốt";
																								
												if(ISDACHOT.equals("1"))
												{
													trangthai = "Đã chốt";
												}
													
												if(ISTP.equals("1"))
												{
													trangthai = "Đã duyệt (Trưởng phòng)";
												}
												
												if(ISKTV.equals("1"))
												{
													trangthai = "Đã duyệt (Kế toán gán mã chi phí)";
												}
												
												if(ISKTT.equals("1"))
												{
													trangthai = "Đã duyệt (Kế toán trưởng)";
												}
												
											}
											else if(tt.equals("1"))
											{													
												if(ISTHANHTOAN.equals("1"))
												{
													trangthai = "Đã thanh toán";
												}

												if(ISHOANTAT.equals("1"))
												{
													trangthai = "Đã hoàn tất";
												}
												
												if(ISDACHOT.equals("1"))
												{
													trangthai = "Đã chốt";
												}
													
												if(ISTP.equals("1"))
												{
													trangthai = "Đã duyệt (Trưởng phòng)";
												}
												
												if(ISKTV.equals("1"))
												{
													trangthai = "Đã duyệt (Kế toán gán mã chi phí)";
												}
												
												if(ISKTT.equals("1"))
												{
													trangthai = "Đã duyệt (Kế toán trưởng)";
												}
											}
											else
											{
												if(tt.equals("2"))
													trangthai = "Đã xóa";
												else 
													trangthai = "Đã hủy";
											}
											
												
											
										%>
										<%= trangthai %>
									</TD>
								
									<TD align="center"> 
				                    <% if(tt.equals("0") && ISDACHOT.equals("0")){ %>
				                  
		                                 <% if(ISDACHOT.equals("0")) { %>
					                    	<%if(quyen[2]!=0){ %>
			                                <A href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&update=<%= rsTamUng.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
			                                <%} %>
		                               

			                                <%if(quyen[1]!=0){ %>
			                                <A href = "../../ErpTamUngSvl?userId=<%=userId%>&delete=<%= rsTamUng.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa tạm ứng" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa tạm ứng này?')) return false;"></A>
			                                <%} %>	

			                               <%if(quyen[4]!=0){ %>
			                                 <A id='chotphieu<%=rsTamUng.getString("PK_SEQ")%>'
								       			href=""><img
								       			src="../images/Chot.png" alt="Chốt tạm ứng"
								       			width="20" height="20" title="Chốt tạm ứng"
								      			border="0" onclick="if(!confirm('Bạn có chắc chốt tạm ứng này?')) {return false ;}else{ processing('<%="chotphieu"+rsTamUng.getString("PK_SEQ")%>' , '../../ErpTamUngSvl?userId=<%=userId%>&chot=<%= rsTamUng.getString("PK_SEQ") %>');}"  >
											 </A>
											 <%} %>
										 <%}else{ %>
					                    	<A href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&display=<%= rsTamUng.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
		                                
		                                <%} %>							
				                    <%}else{ %>
				                    	<A href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&display=<%= rsTamUng.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
				                    <%} %>
				                    </TD>
				                    </TR>
								<% m++;}
							}
							catch(SQLException ex){System.out.print("Exception But toan "+ ex.getMessage() );}
						}
					%>
						 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(tuList.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('erpDmhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(tuList.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = tuList.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + tuList.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == tuList.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=tuList.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(tuList.getNxtApprSplitting() < tuList.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(tuList.getNxtApprSplitting() == tuList.getTheLastSplitting()) {%>
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
<%tuList.DBClose(); %>
</form>
<script type="text/javascript">
jQuery(function()
		{		
			$("#NhanVienId").autocomplete("ErpTamUng_NhanVien_NCC_List.jsp");
		});	
jQuery(function()
		{		
			$("#NccId").autocomplete("ErpTamUng_NhanVien_NCC_List.jsp");
		});	
</script>
<% 
try{
	if( rsTienTe!=null){
		rsTienTe.close();
	}
	if(rsTamUng!=null){
		rsTamUng.close();
	}
}catch(Exception er){
	
}
if(tuList!=null)
{
	tuList.DBClose();
}
} %>
</body>
</html>