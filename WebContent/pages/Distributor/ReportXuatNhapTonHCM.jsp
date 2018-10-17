<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();
	ResultSet rsKho = obj.getkho();
	ResultSet rsKhachhang = obj.getkh();
	ResultSet rsNcc = obj.getNccRs();
	ResultSet rsTdv = obj.getRsddkd();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#khId").select2();
	$("#nccId").select2();
	$("#ddkdId").select2();
});
</script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search()
	{
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnTaoBC").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	//	for ( var i = 0; i < fieldShow.length; ++i) {
	//		fieldShow.item(i).checked = false;
	//	}
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
	
 
	function capnhat_check(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			layctnxsolo.checked =!layctnxsolo_tong.checked;
		}
		
	}
	
	function capnhat_check1(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			 layctnxsolo_tong.checked=false ;
		}
		
	}
	
	
	function checkedAll(){
		var chkAll = document.getElementById("checkKho");
	 	var khoId = document.getElementsByName("khoId");
		 console.log("vao day");
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khoId.length; i++)
			 {
				 khoId.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khoId.length; i++)
			 {
				 khoId.item(i).checked = false;
			 }
		 }
	 }
</script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".kholist").colorbox({width:"46%", inline:true, href:"#kholist"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
        });
    </script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post"
		action="../../ReportXuatNhapTonHCM">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" id="userId" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý tồn kho &#62; Báo cáo &#62; Xuất nhập tồn</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xuất nhập tồn</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td><input type="text" name="Edays" class="days"
										value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<%-- <TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select name="kenhId" id="kenhId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {
											%>
											<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>
								</TR> --%>
								
								<TR>
									<%-- <TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>	 --%>
										<TD class="plainlabel">Kho</TD>
									<TD class="plainlabel">
										<a class="kholist" href="#" >
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Danh sách kho"></a>
				                		<div style='display:none'>
			                        	<div id='kholist' style='padding:0px 5px; background:#fff;'>
			                        		<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                        			<tr class="tbheader">
			                        				<th align="center">Kho</th>
			                        				<th align="center">Chọn <input type="checkbox" id="checkKho" onchange="checkedAll();" ></th>
			                        			</tr>
		                        				<% if(rsKho != null){
		                        					while(rsKho.next()){%>
				                        			<tr>
			                        					<td class="plainlabel" align="left"><%= rsKho.getString("ten") %></td>
			                        					<td class="plainlabel">
			                        					<% if(obj.getkhoId().contains(rsKho.getString("pk_seq"))) { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>" checked="checked" >
					                    					<% } else { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>"  >
					                    					<% } %>
			                        					</td>
			                        				</tr>
		                        				<%} }%>
			                        		</table>
			                        	</div>
			                        	</div>
			                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        								<a class="button" href="javascript:search();">
        								<img style="top: -4px;" src="../images/button.png" alt=""> Chọn </a>
									</TD>								

								</TR>
								
								<%
								System.out.println("kho id " + obj.getkhoId());
								if(obj.getkhoId().equals("100003") || obj.getkhoId().equals("100013") || obj.getkhoId().equals("100014"))
								{
								%>
								<TR>
									<TD class="plainlabel">Khách hàng</TD>
									<TD class="plainlabel"><select name="khId" id="khId" style="width: 200px;">
										<option value="" ></option>
										<%
										if (rsKhachhang != null)
											while (rsKhachhang.next()) {
												if (rsKhachhang.getString("pk_seq").equals(obj.getkhId())) {
												%>
											<option value="<%=rsKhachhang.getString("pk_seq")%>" selected><%=rsKhachhang.getString("TEN")%></option>
											<%
											} else {
											%>
											<option value="<%=rsKhachhang.getString("pk_seq")%>"><%=rsKhachhang.getString("TEN")%></option>
											<%
											}
										}
										%>
									</select></TD>
									
								</TR>
								<%
								}else if(obj.getkhoId().equals("100004") || obj.getkhoId().equals("100009")){
										
								%>
								<TR>
									<TD class="plainlabel">Nhà cung cấp</TD>
									<TD class="plainlabel"><select name="nccId" id="nccId" style="width: 200px;">
										<option value="" ></option>
										<%
										if (rsNcc != null)
											while (rsNcc.next()) {
												if (rsNcc.getString("pk_seq").equals(obj.getNccId())) {
												%>
											<option value="<%=rsNcc.getString("pk_seq")%>" selected><%=rsNcc.getString("TEN")%></option>
											<%
											} else {
											%>
											<option value="<%=rsNcc.getString("pk_seq")%>"><%=rsNcc.getString("TEN")%></option>
											<%
											}
										}
										%>
									</select></TD>
									
								</TR>
								
								<%} else if(obj.getkhoId().equals("100008")){
										
								%>
								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel"><select name="ddkdId" id="ddkdId" style="width: 200px;">
										<option value="" ></option>
										<%
										if (rsTdv != null)
											while (rsTdv.next()) {
												if (rsTdv.getString("pk_seq").equals(obj.getDdkd())) {
												%>
											<option value="<%=rsTdv.getString("pk_seq")%>" selected><%=rsTdv.getString("TEN")%></option>
											<%
											} else {
											%>
											<option value="<%=rsTdv.getString("pk_seq")%>"><%=rsTdv.getString("TEN")%></option>
											<%
											}
										}
										%>
									</select></TD>
									
								</TR>
								<%} %>
								<%-- <TR>
                           			<TD class="plainlabel" colspan = '3'>
                           			<%if(obj.getLaytheo().trim().equals("1")){ %>
							  				<input name="laysolo" checked="checked" value="1"  type="checkbox" /> Lấy có số Lô
							  				<%}else{ %>
							  				<input name="laysolo" value="1"  type="checkbox" /> Lấy có số Lô
							  				<%} %>	
                           			</TD>
                           		</TR>  --%>
                           		
                           		<TR>
                           			<TD class="plainlabel" >
                           			 
							  		   
                           			</TD>
                           			<TD class="plainlabel"  >
                           			 
                           			</TD>
                           			<TD class="plainlabel"  >
                           			 
                           			</TD>
                           			
                           		</TR> 
                           		
                           		<tr>
                           			<td colspan="4">
                           			   
							  		  
                           			</td>
                           		</tr>
                           		
								<TR>
									<td colspan="4">
									<div id="btnTaoBC">
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
											</div>
									</td>
								</TR>
								
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none;display: none">
							<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Fields hiện </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn ẩn </th>
											</tr>
											<tr class="tbdarkrow">
												<td>Kênh bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Channel">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Khu Vực</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Region">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Khu Vực</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Area">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Kho</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Warehouse">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Chi nhánh / Đối tác</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Distributor">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Tồn đầu</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Begin_Invetory">
												</td>
											</tr>

											<tr class="tbdarkrow">
												<td>Lượng hàng thực tế</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Purchase">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Doanh số bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Secondary_Sales">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Lượng hàng khuyến mãi đã xuất</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Promotion_Out">
												</td>
											</tr>


											<tr class="tblightrow">
												<td>Lượng hàng khuyến mãi đã nhập</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Promotion_In">
												</td>
											</tr>

											<tr class="tbdarkrow">
												<td>Lượng Kiểm kho</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Adjust_Inventory">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Tồn cuối</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Ending_Inventory">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Số tiền</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Amount">
												</td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle;display: none"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick="ChuyenSangPhai();"><br />
								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
							<div style="width: 30%; float: left;display: none">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Tat cac cac Fields </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chon hien</th>
											</tr>

											<tr class="tbdarkrow">
												<td>Mã Chi nhánh / Đối tác</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Distributor_Code">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Đơn vị kinh doanh</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Business_Unit">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Nhãn hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Brands">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Chủng loại</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Catergory">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Tên sản phẩm</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="SKU">
												</td>
											</tr>
											<tr class="tblightrow">
												<td>Số thùng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Quantity">
												</td>
											</tr>
											<tr class="tbdarkrow">
												<td>Số lượng lẻ</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Piece">
												</td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>


						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
</body>
</html>

<% 
	if (dvdl != null) dvdl.close();  
		if(gsbh != null) gsbh.close();
		if(kenh != null) kenh.close();
		if(khuvuc != null) khuvuc.close();
		if(npp != null) npp.close();
		if(vung != null) vung.close();
		if(sanpham!=null){
			sanpham.close();
		}
		if(rsKho!=null){
			rsKho.close();
		}
	 if(obj != null)  obj.DBclose(); 
	session.setAttribute("obj",null);
}%>