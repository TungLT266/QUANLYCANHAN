<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="geso.traphaco.erp.beans.khaibaomau.*"%>
<%@page import="geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMau"%>
<%@page import="geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMauChiTiet"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%-- <%@page import="geso.dms.center.util.DinhKhoanKeToan"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.xuatdungccdc.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<!-- geso.traphaco.center.util.DinhKhoanKeToan -->
<%@ page import = "geso.traphaco.center.util.DinhKhoanKeToan" %>
<%@ page import = "geso.traphaco.center.util.IDinhKhoanKeToan" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	ErpKhaiBaoMau obj = (ErpKhaiBaoMau)session.getAttribute("obj");
	String action = (String)session.getAttribute("action");
	List<Erp_Item> taiKhoanList = obj.getTaiKhoanList();
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	quyen1 = util.Getquyen("58",userId);
	
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoERP - Project</TITLE>
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
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spXuatDungVatTuList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<script language="javascript" type="text/javascript">

	
	
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var stt = document.getElementById("stt");
		 var maSo = document.getElementById("maSo");
		 var tenTieuChi = document.getElementById("tenTieuChi");
		 var error = document.getElementById("msg");
		 
		 
		 if(stt == null || stt.value == "")
		 {
			 stt.focus();
			 error.value = 'Vui lòng nhập "STT"!';
			 return;
		 }
		 
		 if(maSo == null || maSo.value == "")
		 {
			 maSo.focus();
			 error.value = 'Vui lòng nhập "Mã số"!';
			 return;
		 }
		 
		 if(tenTieuChi == null || tenTieuChi.value == "")
		 {
			 tenTieuChi.focus();
			 error.value = 'Vui lòng nhập "Tên tiêu chí"!';
			 return;
		 }

		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
// 	 	 document.forms['dmhForm'].action.value='new';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function submit()
	 { 		
		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 	 
	 function goBack()
	 {
	  	window.history.back();
	 }

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpKhaiBaoMauUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="<%=action %>" />
		<input type="hidden" name="id" value="<%= obj.getStt() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if (action.trim().equals("display")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý kế toán &gt; Báo cáo &gt; Bảng cân đối phát sinh (TT200) &gt; Khai báo mẫu &gt; Hiển thị</div>
			<%}else if (action.trim().equals("update")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý kế toán &gt; Báo cáo &gt; Bảng cân đối phát sinh (TT200) &gt; Khai báo mẫu &gt; Cập nhật</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý kế toán &gt; Báo cáo &gt; Bảng cân đối phát sinh (TT200) &gt; Khai báo mẫu &gt; Tạo mới</div>
			<%} %>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave">
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				<%} %>
				</span>
				
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="msg" rows="1" readonly="readonly" style="width: 100%%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Khai báo mẫu</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">

							<TR>
								<TD class="plainlabel" valign="top" width="150px">STT</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="stt" id="stt" value="<%=obj.getStt() %>" style="text-align: left;">
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Mã số</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="maSo" id="maSo" value="<%=obj.getMaSo() %>" style="text-align: left; width: 400px;">
								</TD>
							</TR>						
<%-- 							<%if (action.trim().equals("display")){ %> --%>
<!-- 							<TR> -->
<!-- 								<TD class="plainlabel" valign="top" width="150px">Ngày chuyển</TD> -->
<!-- 								<TD class="plainlabel" valign="top"  colspan="3" > -->
<%-- 									<input type="text" name="ngayChuyen" class="days" id="ngayChuyen" value="<%=obj.getNgayChuyen() %>" style="text-aligns: right;" readonly="readonly"> --%>
<!-- 								</TD> -->
<!-- 							</TR> -->
<%-- 							<%} %> --%>
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Thuyết minh</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="thuyetMinh" id="thuyetMinh" value="<%=obj.getThuyetMinh() %>" style="text-align: left; width: 400px;">
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Tên tiêu chí</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="tenTieuChi" id="tenTieuChi" value="<%=obj.getTenTieuChi() %>" style="text-align: left; width: 400px;">
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Là công thức</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
								<%if (obj.getIsCongThuc() == 1){ %>
									<input name="isCongThuc" type="checkbox" value="1" checked>
								<%}else{ %>
									<input name="isCongThuc" type="checkbox" value="1">
								<%} %>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Công thức</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="congThuc" id="congThuc" value="<%=obj.getCongThuc() %>" style="text-align: left; width: 400px;">
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">In đậm</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
								<%if (obj.getInDam() == 1){ %>
									<input name="inDam" type="checkbox" value="1" checked>
								<%}else{ %>
									<input name="inDam" type="checkbox" value="1">
								<%} %>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Công thức Excel</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
								<%if (obj.getIsCongThucExcel() == 1){ %>
									<input name="isCongThucExcel" type="checkbox" value="1" checked>
								<%}else{ %>
									<input name="isCongThucExcel" type="checkbox" value="1">
								<%} %>
								</TD>
							</TR>
							
							<tr>
								<td class="plainlabel">Số hiệu tài khoản</td>
								<td class="plainlabel">
									<a href="" id="spId" rel="subcontentSp">
		           	 							&nbsp; <img alt="Thông tin loại sản phẩm" src="../images/vitriluu.png"></a>
		           	 							
									<DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
					                    <table width="90%" align="center">
					                        <tr>
					                            <th width="100px">Số hiệu tài khoản</th>
					                            <th width="80px">Mã TSNV</th>
					                            <th width="80px">Lấy số dư</th>
					                            <th width="150px">Lấy số dư nợ</th>
					                            <th width="150px">Lấy số dư có</th>
					                            <th width="80px">Không âm</th>
					                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
					                        </tr>
			                            	<%
				                        		if(obj.getTaiKhoanList() != null)
				                        		{
				                        			int index = 0;
				                        			for(Erp_Item item : obj.getTaiKhoanList())
				                        			{  
				                        			%>
				                        			<tr>
				                        				<td><input style="width: 100%" value="<%= item.getValue() %>" readonly="readonly" ></td>
				                        				<% 
				                        				ErpKhaiBaoMauChiTiet ct = ErpKhaiBaoMauChiTiet.isContainValue(obj.getChiTietList(), item.getValue());
				                        				if(ct != null){ %>
				                        				<td align="right">
				                        					<%if (ct.getLoaiTSNV() == 1){ %>
				                        					<input type="checkbox" name="loaiTSNV_<%= item.getValue() %>" checked="checked" value="1">
					                        				<%}else{ %>
				                        					<input type="checkbox" name="loaiTSNV_<%= item.getValue() %>" value="1">
					                        				<%} %>
				                        				</td>
				                        				<td align="right">
					                        				<%if (ct.getLaySoDu() == 1){ %>
				                        					<input type="checkbox" name="laySoDu_<%= item.getValue() %>" checked="checked" value="1">
					                        				<%}else{ %>
				                        					<input type="checkbox" name="laySoDu_<%= item.getValue() %>" value="1">
					                        				<%} %>
					                        			</td>
				                        				<td align="right">	
					                        				<%if (ct.getLaySoDuNo() == 1){ %>
				                        					<input type="checkbox" name="laySoDuNo_<%= item.getValue() %>" checked="checked" value="1">
					                        				<%}else{ %>
				                        					<input type="checkbox" name="laySoDuNo_<%= item.getValue() %>" value="1">
					                        				<%} %>
					                        			</td>
				                        				<td align="right">	
					                        				<%if (ct.getLaySoDuCo() == 1){ %>
				                        					<input type="checkbox" name="laySoDuCo_<%= item.getValue() %>" checked="checked" value="1">
					                        				<%}else{ %>
				                        					<input type="checkbox" name="laySoDuCo_<%= item.getValue() %>" value="1">
					                        				<%} %>
					                        			</td>
				                        				<td align="right">	
					                        				<%if (ct.getKhongAm() == 1){ %>
				                        					<input type="checkbox" name="khongAm_<%= item.getValue() %>" checked="checked" value="1">
					                        				<%}else{ %>
				                        					<input type="checkbox" name="khongAm_<%= item.getValue() %>" value="1">
					                        				<%}%>
				                        				</td>
				                        				
				                        				<td align="right"><input type="checkbox" name="checkSoHieuTaiKhoan" checked="checked" value="<%= item.getValue() %>"></td>
				                        				<% }else{%>
				                        				<td align="right"><input type="checkbox" name="loaiTSNV_<%= item.getValue() %>" value="1"></td>
				                        				<td align="right"><input type="checkbox" name="laySoDu_<%= item.getValue() %>" value="1"></td>
				                        				<td align="right"><input type="checkbox" name="laySoDuNo_<%= item.getValue() %>" value="1"></td>
				                        				<td align="right"><input type="checkbox" name="laySoDuCo_<%= item.getValue() %>" value="1"></td>
				                        				<td align="right"><input type="checkbox" name="khongAm_<%= item.getValue() %>" value="1"></td>
				                        				<td align="right"><input type="checkbox" name="checkSoHieuTaiKhoan" value="<%= item.getValue() %>"></td>
				                        				<% }%>
				                        			</tr>
				                        			
				                        		 <% index++;
				                        		 }} 
				                        		 %>
					                    </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
					                     </div>
					            	</DIV>
								</td>
							</tr>
						</TABLE>
						<hr>
						
						<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1" style="display: none;">
							<tbody id="chiTiet">
								<TR class="tbheader" >
									<TH width="20%">Tài khoản</TH>
									<TH width="16%">Loại TSNV</TH>
									<TH width="16%">Lấy sô dư </TH>
									<TH width="16%">Lấy số dư nợ</TH>
									<TH width="16%">Lấy số dư có</TH>
									<TH width="16%">Không âm</TH>
								</TR>
								<%for (ErpKhaiBaoMauChiTiet item : obj.getChiTietList()){%>
								<tr>
									<td>
										<input name="soHieuTaiKhoan" value="<%=item.getSoHieuTaiKhoan()%>" style="width:100%" >
									</td>
									<td>
										<%if (item.getLoaiTSNV() == 1){ %>
											<input name="loaiTSNV" type="checkbox" value="1" checked>
										<%}else{ %>
											<input name="loaiTSNV" type="checkbox" value="1">
										<%} %>
									</td>
									<td>
										<%if (item.getLaySoDu() == 1){ %>
											<input name="laySoDu" type="checkbox" value="1" checked>
										<%}else{ %>
											<input name="laySoDu" type="checkbox" value="1">
										<%} %>
									</td>
									<td>
										<%if (item.getLaySoDuNo() == 1){ %>
											<input name="laySoDuNo" type="checkbox" value="1" checked>
										<%}else{ %>
											<input name="laySoDuNo" type="checkbox" value="1">
										<%} %>
									</td>
									<td>
										<%if (item.getLaySoDuCo() == 1){ %>
											<input name="laySoDuCo" type="checkbox" value="1" checked>
										<%}else{ %>
											<input name="laySoDuCo" type="checkbox" value="1">
										<%} %>
									</td>
									<td>
										<%if (item.getKhongAm() == 1){ %>
											<input name="khongAm" type="checkbox" value="1" checked>
										<%}else{ %>
											<input name="khongAm" type="checkbox" value="1">
										<%} %>
									</td>
								</tr>
								<%} %> 
<!-- 								Luôn có 1 dòng trống -->
								<tr>
									<td>
										<input name="soHieuTaiKhoan" value="" style="width:100%" >
									</td>
									<td>
										<input name="loaiTSNV" type="checkbox" value="1" checked>											<input name="loaiTSNV" type="checkbox" value="1">
									</td>
									<td>
										<input name="laySoDu" type="checkbox" value="1" checked>											<input name="laySoDu" type="checkbox" value="1">
									</td>
									<td>
										<input name="laySoDuNo" type="checkbox" value="1" checked>
									</td>
									<td>
										<input name="laySoDuCo" type="checkbox" value="1" checked>
									</td>
									<td>
										<input name="khongAm" type="checkbox" value="1" checked>
									</td>
								</tr>
							</tbody>
						</TABLE>
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<a class="button2" href="javascript:ThemSanPham()" style="display: none;">
						<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-top", 300, "click");
</script>

</form>
</BODY>
</html>
<% } %>