<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%
String userId = (String) session.getAttribute("userId");
String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if (!util.check(userId, userTen, sum)) {
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpKichBanSanXuatGiay obj = (IErpKichBanSanXuatGiay) session.getAttribute("kbsxBean");
	ResultSet spRs = obj.getSpRs();
	ResultSet rsNhaMay = obj.getRsNhaMay();
	ResultSet rsVattu = obj.getRsVattu();
	ResultSet rsCongdoan = obj.getRsCongdoan();
	List<IKichBan_CongDoanSx> lstCdsx = obj.getCongdoanSxList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<%-- <link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
    $(document).ready(function()
    {
     <% for( int i = 0; i < 100; i++ ) { %>
     $(".chitiet<%=i %>").colorbox({width:"90%", inline:true, href:"#chitiet<%= i %>"});
        //Example of preserving a JavaScript event for inline calls.
        $("#click").click(function(){ 
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Kich ban SX");
            return false;
        });
        <% } %>
    });
</script> --%>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<!-- <script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script> -->

<script>
	//perform JavaScript after the document is scriptable.
	$(function() {
		// setup ul.tabs to work as tabs for each div directly under div.panes
		$("ul.tabs").tabs("div.panes > div");
	});
</script>

<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({
			changeMonth: true,
			changeYear: true
		});
	});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform() {
	    document.forms["khtt"].submit();
	}
	
	function keypress(e) {    
		var keypressed = null;
		if(window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if((keypressed>31 && keypressed<45) || keypressed == 47 || keypressed>57) {
	        return false;
	    }
	    return true;
	}
	
	function save() {
		if(document.getElementById("sanpham").value == ""){
			document.getElementById("dataerror").value = "Bạn chưa chọn sản phẩm.";
			return false;
		}
		
		if(document.getElementById("diengiai").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập diễn giải.";
			return false;
		}
		
		if(document.getElementById("soluongchuan").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập số lượng chuẩn.";
			return false;
		}
		
		var checkCongdoan = 0;
		var cdsxList = document.getElementsByName("congdoanId");
		var thoigianList = document.getElementsByName("thoigian");
		var thutuList = document.getElementsByName("thutu");
		for(i = 0; i < cdsxList.length; i++){
			if(cdsxList.item(i).value != ""){
				checkCongdoan = 1;
				
				if(thoigianList.item(i).value.trim() == ""){
					document.getElementById("dataerror").value = "Bạn chưa nhập thời gian cho công đoạn sản xuất.";
					return false;
				}
				
				if(thutuList.item(i).value.trim() == ""){
					document.getElementById("dataerror").value = "Bạn chưa nhập thứ tự cho công đoạn sản xuất.";
					return false;
				}
			}
		}
		
		if(checkCongdoan == 0){
			document.getElementById("dataerror").value = "Bạn chưa chọn công đoạn sản xuất.";
			return false;
		}
		
		
		var error = document.getElementById("error");
		if(error.value != "") {
			alert(error.value);
		} else {
	  		document.forms["khtt"].action.value = "save";
		  	document.forms["khtt"].submit();
		}
    }
	
	function checkedAll(stt,size) {
		if(document.getElementById("chonall_"+stt).checked == true){
			$(".checkall_"+stt).attr("checked", "true");
			
			for(j = 0; j < size; j++){
				document.getElementById("upload_"+stt+"_"+j).style.display = "block";
			}
		} else {
			$('.checkall_'+stt).each(function(i,item){
		        $(item).attr('checked', !$(item).is(':checked'));
		    });
			
			for(j = 0; j < size; j++){
				document.getElementById("upload_"+stt+"_"+j).style.display = "none";
			}
		}
		
	}
	
	function hienthiUpload(i,j) {
		if(document.getElementById("chon_"+i+"_"+j).checked == true){
			document.getElementById("upload_"+i+"_"+j).style.display = "block";
		} else {
			document.getElementById("upload_"+i+"_"+j).style.display = "none";
		}
	}
	
	function upload(i,j){
		document.forms["khtt"].uploadi.value = i;
		document.forms["khtt"].uploadj.value = j;
		
		document.forms["khtt"].setAttribute('enctype', "multipart/form-data", 0);
		document.forms["khtt"].submit();
	}
	
	function download(path){
		if(path.trim() == ""){
//			document.getElementById("dataerror").value = "Không có file download.";
			return false;
		} else {
			document.forms["khtt"].pathdownload.value = path;
			document.forms["khtt"].action.value = "download";
		  	document.forms["khtt"].submit();
		}
	}
	
	function deletefile(i,j){
		document.getElementById("upload_path_"+i+"_"+j).value = "";
		document.getElementById("upload_name_"+i+"_"+j).value = "";
		document.getElementById("a_"+i+"_"+j).href = "javascript:download('');'";
	}
	
	function reload(i){
		document.getElementById("congdoanIdcu_"+i).value = "";
		document.forms["khtt"].submit();
	}
	
	function AjaxTINHSOLUONGSXBOM()
	{
		var spId = document.getElementById("sanpham");
		var soluongchuan = document.getElementById("soluongchuan");
    	var vattuId = document.getElementsByName("vattuId");
    	var thutu = document.getElementsByName("thutu");
    	var nhapkho = document.getElementsByClassName("nhapkho");
    	var param = "";
    	for(var i = 0; i < vattuId.length; i++)
   		{
    		if(vattuId.item(i).value != "" && nhapkho.item(i).checked)
    			param = param + thutu.item(i).value + '-' + vattuId.item(i).value + '--';
   		}
    	console.log("&spId=" + spId.value + "&soluongchuan=" + soluongchuan.value.replace(/,/g,"") + "&param=" + param);
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		  	xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		  	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		  	{
		 	 	var soluong = document.getElementsByName("soluong");
		 	 	var error = document.getElementById("error");
				var kq = xmlhttp.responseText;
				console.log("::: kq : " + kq);
				if(parseInt(kq.indexOf("ERROR")) >= 0)
				{
					error.value = kq.substr(parseInt(kq.indexOf("--")) + 2);
					console.log("::: ERROR : " + error.value);
				}
				else
				{
					while(parseInt(kq.indexOf("][")) >= 0)
					{
						var _thutu = kq.substring(0, parseInt(kq.indexOf("-")));
						kq = kq.substr(parseInt(kq.indexOf("-")) + 1);
						var _vattu = kq.substring(0, parseInt(kq.indexOf("-")));
						kq = kq.substr(parseInt(kq.indexOf("-")) + 1);
						var _soluong = kq.substring(0, parseInt(kq.indexOf("][")));
						kq = kq.substr(parseInt(kq.indexOf("][")) + 2);
						soluong.item(_thutu - 1).value = _soluong;
						//console.log("tt=" + _thutu + "vt=" + _vattu + "sl=" + _soluong);
					}
					error.value = "";
				}
		  	}
		}
		xmlhttp.open("POST","../../AjaxTINHSOLUONGSXBOM?action=tinhsoluong&spId=" + spId.value + "&soluongchuan=" + soluongchuan.value.replace(/,/g,"") + "&param=" + param, true);
		xmlhttp.send();
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmaRrgin="0">
	<form name="khtt" method="post" action="../../ErpKichBanSanXuatGiayUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="id" value="<%=obj.getId() %>">
		<input type="hidden" name="uploadi" value="">
		<input type="hidden" name="uploadj" value="">
		<input type="hidden" name="pathdownload" value="">
		<input type="hidden" name="error" id="error" value="">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">
											<%if(obj.getId().length() > 0){ %>
												&nbsp;Dữ liệu nền &gt; Sản xuất &gt; Kịch bản sản xuất &gt; Cập nhật
											<%} else { %>
												&nbsp;Dữ liệu nền &gt; Sản xuất &gt; Kịch bản sản xuất &gt; Tạo mới
											<%} %>
										</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left">
											<A href="../../ErpKichBanSanXuatGiaySvl?userId=<%=userId%>">
												<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
											</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()">
													<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset">
												</A>
											</div>
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" id="dataerror" style="width: 100%; color: red; background-color: white;font-weight: bold" style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1">
										<%=obj.getMsg() %>
									</textarea>
								</FIELDSET>
							</TD>
						</tr>
						
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin kịch bản sản xuất </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="125px">Sản phẩm <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel">
												<select name="sanpham" id="sanpham" style="width: 300px; text-align: left;" class="select2">
													<option value=""></option>
													<%if(spRs != null){
														while (spRs.next()){
															if (obj.getSpSelected().equals(spRs.getString("pk_seq"))){ %>
																<option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ten")%></option>
															<%} else { %>
																<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ten")%></option>
															<%}
														}
														spRs.close();
													} %>
												</select>
											</TD>
											
											<TD class="plainlabel" valign="middle">Hồ sơ lô SX</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="hosolosx" value="<%=obj.getHosolosx() %>">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Diễn giải <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="diengiai" id="diengiai" value="<%=obj.getDiengiai() %>">
											</TD>
											
											<TD class="plainlabel" valign="middle">Ngày ban hành HSL</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" class="days" name="ngaybanhanhhsl" value="<%=obj.getNgaybanhanhhsl() %>">
											</TD>
										</TR>
										
										<TR style="display: none;">
											<TD class="plainlabel" valign="middle">Phân xưởng </TD>
											<TD class="plainlabel" valign="middle">
												<select name="nhamayId" style="width: 350px" id="nhamayId" class="select2">
													<option value=""></option>
													<%if(rsNhaMay != null){
														while (rsNhaMay.next()){
															if (rsNhaMay.getString("pk_seq").equals(obj.getNhaMayId())){%>
																<option value="<%=rsNhaMay.getString("pk_seq")%>" selected="selected"><%=rsNhaMay.getString("Ten")%></option>
															<%} else { %>
																<option value="<%=rsNhaMay.getString("pk_seq")%>"><%=rsNhaMay.getString("Ten")%></option>
															<%}
														}
														rsNhaMay.close();
													} %>
												</select>
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Số lượng chuẩn <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="soluongchuan" id="soluongchuan" value="<%=obj.getSoluongchuan()%>" onchange="AjaxTINHSOLUONGSXBOM();" style="text-align: right;" onkeypress="return keypress(event);">
											</TD>
											
											<TD class="plainlabel" valign="middle">Lần ban hành HSL</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="lanbanhanhhsl" value="<%=obj.getLanbanhanhhsl() %>" style="text-align: right;" onkeypress="return keypress(event);">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Số ngày sản xuất</TD>
											<TD class="plainlabel" valign="middle" colspan="3">
												<input type="text" name="songaysanxuat" value="<%=obj.getSongaysanxuat()%>" style="text-align: right;" onkeypress="return keypress(event);">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Hoạt động</TD>
											<TD class="plainlabel" valign="middle" colspan="3">
												<%if (obj.getTrangThai().equals("1")) { %>
													<input type="checkbox" checked="checked" name="trangthai" value="1" style="text-align: right;">
												<%} else { %>
													<input type="checkbox" name="trangthai" value="1" style="text-align: right;">
												<%} %>
											</TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						
						<TR>
							<TD colspan="6">
								<ul class="tabs">
									<li><a href="#" class="legendtitle">Công đoạn sản xuất</a></li>
								</ul>
								<div>
									<table cellpadding="0px" cellspacing="1px" width="100%">
										<tr class="tbheader">
											<th width="25%">Công đoạn sản xuất</th>
											<th width="7%">Thời gian</th>
											<th width="7%">Thứ tự</th>
											<th width="15%">BOM</th>
											<th width="13%">Số lượng</th>
											<th width="13%">Nhập kho công đoạn</th>
											<th width="5%">Chi tiết</th>
										</tr>
										
										<%int count = 0;
										String chon;
										for (int i=0; i<lstCdsx.size(); i++) {
											IKichBan_CongDoanSx congdoan = lstCdsx.get(i); %>
											<tr>
												<td>
													<input type="hidden" name="congdoanIdcu" id="congdoanIdcu_<%=i %>" value="<%=congdoan.getId() %>">
													<select name ="congdoanId" style="width: 410px" class="select2" id="congdoanId_<%=i %>" onchange="submitform();">
														<option value=""></option>
													  	<%if(rsCongdoan!=null){
													  		rsCongdoan.beforeFirst();
													  		while(rsCongdoan.next()){
													  			if(congdoan.getId().equals(rsCongdoan.getString("PK_SEQ"))){ %>
													  				<option value="<%=rsCongdoan.getString("PK_SEQ")%>" selected="selected"><%=rsCongdoan.getString("DienGiai") %></option>
													  			<%} else { %>
																	<option value="<%=rsCongdoan.getString("PK_SEQ")%>"><%=rsCongdoan.getString("DienGiai") %></option>
													  			<%}
												  			}
												  		} %>
										  			</select>
												</td>
												<td><input type="text" value="<%=congdoan.getThoiGian()%>" name="thoigian" style="width: 100%;text-align:right;" onkeypress="return keypress(event);"></td>
												<td><input type="text" value="<%=congdoan.getThuTu()%>" name="thutu" style="width:100%;text-align:right;" onkeypress="return keypress(event);"></td> 
												<td>
													<select name ="vattuId" id="vattuId_<%=i %>" style="width: 244px" class="select2" onchange="AjaxTINHSOLUONGSXBOM();">
														<option></option>	
													  	<%if(rsVattu!=null){
													  		rsVattu.beforeFirst();
													  		while(rsVattu.next()){
													  			if(congdoan.getVattuId().equals(rsVattu.getString("PK_SEQ"))){ %>
													 	 			<option value="<%=rsVattu.getString("PK_SEQ")%>" selected="selected"><%=rsVattu.getString("DienGiai") %></option>
													  			<%} else { %>
													  				<option value="<%=rsVattu.getString("PK_SEQ")%>"><%=rsVattu.getString("DienGiai") %></option>
													  			<%}
												  			}
												  		} %>
											  		</select>
										  		</td>
										  		<td><input type="text" value="<%= congdoan.getSoluong() %>" name="soluong" style="width:100%;text-align:right;" readonly></td>
												<td align="center"><input type="checkbox" class="nhapkho" name="nhapkho_<%=i %>" <%= congdoan.getNhapkho().equals("1") ? " checked " : "" %> value="1" onchange="AjaxTINHSOLUONGSXBOM();"></td>
												<td align="center">
													<%if(congdoan.getId().length() > 0){ %>
							           	 				<%-- <a class="chitiet<%=i %>" href="" id="sanpham_<%=i%>" rel="chitiet<%=i%>"> 
							           	 					&nbsp;<img alt="Chọn số lô" src="../images/vitriluu.png">
							           	 				</a> --%>
							           	 				
							           	 				<a href="" id="sanpham_<%=i%>" rel="chitiet<%=i%>"> 
							           	 					&nbsp;<img alt="Chọn số lô" src="../images/vitriluu.png">
							           	 				</a>
							           	 				<%if(obj.getId().length() > 0){ %>
								           	 				<a href="javascript:reload(<%=i %>)"> 
								           	 					&nbsp;<img alt="Chọn số lô" src="../images/reload16.png" title="Reload">
								           	 				</a>
							           	 				<%} %>
							           	 				
							           	 				<!-- <div style='display:none'> -->
							           	 				<%-- <div id='chitiet<%=i %>' style='padding:0px 5px; background:#fff; overflow:auto;'> --%>
							           	 				<DIV id="chitiet<%=i%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 90%; max-height:320px; overflow:auto; padding: 4px;">
							           	 					<table width="100%" align="center">
							           	 						<%List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = congdoan.getCdsxChitietList();%>
										                        <%KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;%>
							           	 						<tr>
										                        	<th width="12%" style="font-size: 11px">Khu vực SX</th>
										                            <th width="12%" style="font-size: 11px">Giai đoạn SX</th>
										                            <th width="10%" style="font-size: 11px">TSCĐ/CPTT</th>
										                            <th width="10%" style="font-size: 11px">Thiết bị</th>
										                            <th width="13%" style="font-size: 11px">Thông số chung</th>
										                            <th width="13%" style="font-size: 11px">Thông số</th>
										                            <th width="12%" style="font-size: 11px">Yêu cầu</th>
										                            <th width="8%" style="font-size: 11px">Thông số từ</th>
										                            <th width="8%" style="font-size: 11px">Thông số đến</th>
										                            <th width="5%" style="font-size: 11px">DVT</th>
										                            <th width="3%" style="font-size: 11px">Đạt</th>
										                            <th width="7%" style="font-size: 11px">Chọn<input type="checkbox" id="chonall_<%=i %>" onClick="checkedAll(<%=i %>,<%=cdsxChitietList.size() %>)"></th>
										                            <th width="7%" style="font-size: 11px">Upload chi tiết</th>
										                        </tr>
										                        
										                        <%for(int j = 0; j < cdsxChitietList.size(); j++){ %>
										                        	<%cdsxChitiet = cdsxChitietList.get(j); %>
										                        	
										                        	<tr>
										                        		<td>
										                        			<input type="hidden" name="khuvucsxid_<%=i %>" value="<%=cdsxChitiet.getKhuvucsxId() %>">
										                        			<input type="text" name="khuvucsx_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getKhuvucsx() %>">
										                        		</td>
										                        		<td>
										                        			<input type="hidden" name="giaidoansxid_<%=i %>" value="<%=cdsxChitiet.getGiaidoansxId() %>">
										                        			<input type="text" name="giaidoansx_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getGiaidoansx() %>">
										                        		</td>
										                        		<td>
										                        			<input type="hidden" name="tscd_cptt_id_<%=i %>" value="<%=cdsxChitiet.getTscdCpttId() %>">
										                        			<input type="text" name="tscd_cptt_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getTscdCptt() %>">
									                        			</td>
										                        		<td>
										                        			<input type="hidden" name="thietbiid_<%=i %>" value="<%=cdsxChitiet.getThietbiId() %>">
										                        			<input type="text" name="thietbi_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThietbi() %>">
										                        		</td>
										                        		<td><input type="text" name="thongsochung_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsochung() %>"></td>
										                        		<td><input type="text" name="thongso_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongso() %>"></td>
										                        		<td><input type="text" name="yeucau_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getYeucau() %>"></td>
										                        		<td><input type="text" name="thongsotu_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsotu() %>"></td>
										                        		<td><input type="text" name="thongsoden_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsoden() %>"></td>
										                        		<td>
										                        			<input type="hidden" name="dvtid_<%=i %>" value="<%=cdsxChitiet.getDvtId() %>">
										                        			<input type="text" name="dvt_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getDvt() %>">
										                        		</td>
										                        		<td style="text-align: center;">
										                        			<%-- <input type="hidden" name="id_cdsx_chitiet_<%=i %>" value="<%=cdsxChitiet.getId() %>"> --%>
										                        			<input type="hidden" name="dat_<%=i %>" value="<%=cdsxChitiet.getDat() %>">
										                        			<%if(cdsxChitiet.getDat().equals("1")) {%>
																				<input disabled type="checkbox" checked>
																			<%} else {%>
																				<input disabled type="checkbox">
																			<%} %>
										                        		</td>
										                        		<td style="text-align: center;">
										                        			<%if(cdsxChitiet.getChon().equals("1")) {%>
																				<input class="checkall_<%=i %>" name="chon_<%=i %>_<%=j %>" id="chon_<%=i %>_<%=j %>" value="1" type="checkbox" checked onchange="hienthiUpload(<%=i %>,<%=j %>);">
																			<%} else {%>
																				<input class="checkall_<%=i %>" name="chon_<%=i %>_<%=j %>" id="chon_<%=i %>_<%=j %>" value="1" type="checkbox" onchange="hienthiUpload(<%=i %>,<%=j %>);">
																			<%} %>
										                        		</td>
										                        		<td>
										                        			<div <%=cdsxChitiet.getChon().equals("1") ? "" : "style=\"display: none;\"" %> id="upload_<%=i %>_<%=j %>">
											                        			<input type="hidden" name="upload_path_<%=i %>" id="upload_path_<%=i %>_<%=j %>" value="<%=cdsxChitiet.getUploadPath() %>">
													                        	<input type="text" style="width:130px" readonly name="upload_name_<%=i %>" id="upload_name_<%=i %>_<%=j %>" value="<%=cdsxChitiet.getUploadName() %>">
													                        	<input type="file" accept="application/vnd.ms-excel" name="file_upload_<%=i %>_<%=j %>" title="" style="width: 70px;" value='' onchange="upload(<%=i %>,<%=j %>);">
													                        	
												                        		<div style="float: right;" <%=cdsxChitiet.getUploadPath().trim().length() > 0 ? "" : "style=\"display: none;\"" %>>
													                        		<a class="button2" id="a_<%=i %>_<%=j %>" href="javascript:download('<%=cdsxChitiet.getUploadPath().replace("\\", "\\\\") %>');">
													                        			<img style="top: -4px;" src="../images/download.png" alt="Download" title="Download">
													                        		</a>
													                        		<a class="button2" href="javascript:deletefile(<%=i %>,<%=j %>);">
													                        			<img style="top: -4px;" src="../images/Delete20.png" alt="Delete" title="Delete">
													                        		</a>
												                        		</div>
													                        </div>
										                        		</td>
										                        	</tr>
										                        <%} %>
							           	 					</table>
							           	 					
							           	 					<div align="right">
										                     	<label style="color: red" ></label>
										                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										                     	<a href="javascript:dropdowncontent.hidediv('chitiet<%=i%>')" > Đóng lại </a>
									                     	</div>
							           	 				</div>
							           	 				<!-- </div> -->
							           	 				
							           	 				<script type="text/javascript">
										            		dropdowncontent.init('sanpham_<%=i %>', "left-top", 300, "click");
										            	</script>
													<%} %>
													<%-- <input type="hidden" name="countthongso" value="<%=count %>"> --%>
												</td>
											</tr>
										<%} 
										for(int j=lstCdsx.size(); j<10; j++){ %>
											<tr>
												<td>
													<input type="hidden" name="congdoanIdcu" value="">
													<select name ="congdoanId" style="width: 410px" class="select2" id="congdoanId_<%=j %>" onchange="submitform();">
														<option></option>	
													  	<%if(rsCongdoan!=null){
													  		rsCongdoan.beforeFirst();
													  		while(rsCongdoan.next()){ %>
													  			<option value="<%=rsCongdoan.getString("PK_SEQ")%>"><%=rsCongdoan.getString("DienGiai") %></option>
													  		<%} }%>
											  		</select>
												</td>
												<td><input type="text" name="thoigian" style="width:100%;text-align:right;" onkeypress="return keypress(event);"></td>
												<td><input type="text" name="thutu" value="<%=j+1 %>" style="width:100%;text-align:right;"  onkeypress="return keypress(event);"></td>
												<td>
													<select name ="vattuId" id="vattuId_<%=j %>" style="width: 244px" class="select2">
														<option></option>	
													  	<%if(rsVattu!=null){
													  		rsVattu.beforeFirst();
												  			while(rsVattu.next()){%>
												  				<option value="<%=rsVattu.getString("PK_SEQ")%>"><%=rsVattu.getString("DienGiai") %></option>
												  			<%}
											  			}%>
											  		</select>
												</td>
												<td><input type="text" value="" name="soluong" style="width:100%;text-align:right;" readonly></td>
												<td align="center"><input type="checkbox" class="nhapkho" name="nhapkho_<%=j %>" value="1" ></td>
												<td></td>
											</tr>
										<%} %>
									</TABLE>
								</div>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</FORM>
	
	<script type="text/javascript">
		$(".select2").select2();
		
		if(<%=obj.getUploadi().length() %> > 0){
			document.getElementById("sanpham_"+"<%=obj.getUploadi() %>").click();
		}
	</script>
</BODY>
</HTML>

<%
	if (spRs != null)
		spRs.close();
	if (rsNhaMay != null)
		rsNhaMay.close();
	if (rsCongdoan != null)
		rsCongdoan.close();
	if (lstCdsx != null)
		lstCdsx.clear();
	if (rsVattu != null)
		rsVattu.close();
	obj.DbClose();
	session.setAttribute("kbsxBean", null) ; 
}
%>