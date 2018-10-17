<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*"%>
<%@page import="geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP"%>
<%@page import="geso.traphaco.erp.beans.phieuxuatkho.ISpDetail"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IErpBom obj = (IErpBom) session.getAttribute("bomBean");
		ResultSet spRs = (ResultSet) obj.getSpRs();

		IErpDanhMucVatTuThanhPhan bomThanhPhan = (IErpDanhMucVatTuThanhPhan) session
				.getAttribute("bomThanhPhan");
		List<IErpDanhMucVatTuThanhPhan> sptp = (List<IErpDanhMucVatTuThanhPhan>) bomThanhPhan.getSptpList();
		ResultSet rsVattu = (ResultSet) obj.getRsVattu();
		ResultSet loaihhList = (ResultSet) obj.getLoaiList();
		ResultSet rsdvkd = (ResultSet) obj.getRsDvkd();

		ResultSet rsdvdl = (ResultSet) obj.getRsDvdl();

		//	ResultSet rschuannen = (ResultSet)obj.getRsChuanNen();	
		List<IDanhmucvattu_SP> vattuList = (List<IDanhmucvattu_SP>) obj.getListDanhMuc();
		NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
		NumberFormat formatter3 = new DecimalFormat("#,###,###.###");
		NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bom_vattu.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});
				$(".titleTab a").click(function () { 

			        var activeTab = $(this).attr("href"); 
						//var activeTab =
			        $(".titleTab a").removeClass("active"); 
			        $(this).addClass("active");
			        $(".tabContents").hide();
			        $(activeTab).fadeIn();

			    });
	        }); 		
			
	</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
        $(document).ready(function() { $("#sanpham").select2(); });
        $(document).ready(function() { 
        	
        	for(var i=0;i<=10;i++)
        	$("#idVattuTT_"+i).select2(); 
        	
        
        });
    </script>
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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khtt"].submit();
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
	function checkedAll(chk,allquyen) {
			for(i=0; i<chk.length; i++){
			 	//if(document.nccForm.allquyen.checked==true){
			 		
			 if(allquyen.checked==true){
					chk[i].checked = true;
				}else{
					chk[i].checked = false;
				}
			 }
	}
	
	function replaces()
	{
		var vtId=document.getElementsByName("vtId");
		var mavt = document.getElementsByName("mavattu");
		var tenvt = document.getElementsByName("tenvattu");
		var donvitinhvt = document.getElementsByName("donvitinh");  
		var soluong = document.getElementsByName("soluong");
		var hamluong = document.getElementsByName("hamluong");
		var i;
		for(i=0; i < mavt.length; i++)
		{
			if(mavt.item(i).value != "")
			{
				var vt = mavt.item(i).value;
				var pos = parseInt(vt.indexOf("--"));
				if(pos > 0)
				{
					
					
					mavt.item(i).value = vt.substring(0, pos).trim();
					mavt.item(i).focus();
					vt = vt.substr(parseInt(vt.indexOf("--")) + 3);
					tenvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" [")));
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					donvitinhvt.item(i).value = vt.substring(0, parseInt(vt.indexOf("]")));
					vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
					vtId.item(i).value = vt.substring(0, parseInt(vt.indexOf("]")));
					hamluong.item(i).value = 0;
				}	
			}
		else
			{
				vtId.item(i).value = "";
				mavt.item(i).value = "";
				tenvt.item(i).value = "";
				donvitinhvt.item(i).value = "";		
				soluong.item(i).value = "";
			}
		}
		
		var dmid = document.getElementsByName("dmid");
		var dmloai = document.getElementsByName("dmloai");
		var dongia = document.getElementsByName("dmdongia");
		var soluong = document.getElementsByName("dmsoluong");
		var thanhtien = document.getElementsByName("dmthanhtien");
		var i;
		for(i=0; i < dmid.length; i++)
		{
			if(dmid.item(i).value != "" && dmloai.item(i).value != "1")
			{
				if(parseFloat(soluong.item(i).value) > 0)
				{				
					var don_gia = dongia.item(i).value.replace(",","");
					var tt = parseFloat(soluong.item(i).value.replace(",","")) * parseFloat(don_gia);
					thanhtien.item(i).value = tt;
					//thanhtien.item(i).value = roundNumber(tt, 2);
				}
				else			
				{
					thanhtien.item(i).value = "";
				}
			}
		}	
		
		var sanpham_fk=document.getElementsByName("sanpham_fk");
		var sanphamthem = document.getElementsByName("sanphamthem");
		var dvdl_fk = document.getElementsByName("dvdl_fk");
		var donvithem = document.getElementsByName("donvithem");  
		var soluongthem = document.getElementsByName("soluongthem");
		var i;
		for(i = 0; i < sanphamthem.length; i++ ){
			if(sanphamthem.item(i).value != ""){
				var tem = sanphamthem.item(i).value;
				var pos = parseInt(tem.indexOf("["));
				if(pos > 0){
					sanphamthem.item(i).value = tem.substring(0, pos).trim();
					tem = tem.substr(parseInt(tem.indexOf("[")) + 1);
					donvithem.item(i).value = tem.substring(0,parseInt(tem.indexOf("] ["))).trim();
					tem = tem.substr(parseInt(tem.indexOf("] [")) + 3);
					dvdl_fk.item(i).value = tem.substring(0,parseInt(tem.indexOf("] ["))).trim();
					tem = tem.substr(parseInt(tem.indexOf("] [")) + 3);
					sanpham_fk.item(i).value = tem.substring(0,parseInt(tem.indexOf("]"))).trim();
					
				}
			}
		}
		setTimeout(replaces, 300);
	}

	function kiemTraVT(n)
	{
		var vtId=document.getElementsByName("vtId");
		var mavt = document.getElementsByName("mavattu");
		var tenvt = document.getElementsByName("tenvattu");
		var donvitinhvt = document.getElementsByName("donvitinh");  
		var soluong = document.getElementsByName("soluong");
		
		if(vtId.item(n).value == "")
			return;
		
		for(i=0; i < mavt.length; i++)
		{
			if(i != n && vtId.item(i).value != "" && vtId.item(i).value.localeCompare(vtId.item(n).value) == 0)
			{
				alert("Đã có nguyên  liệu này !");
				vtId.item(n).value = "";
				mavt.item(n).value = "";
				tenvt.item(n).value = "";
				donvitinhvt.item(n).value = "";		
				soluong.item(n).value = "";
				return;
			}
		}
	}
	
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	function save()
	{
	
		if($('input[name=trangthai]')[0].checked == false){
			
			 var chkAll = document.getElementById("checktontai").value;
			 if(chkAll =='')
				 chkAll = '0';
			 if(chkAll =='1')
			{
				 var t = confirm("BOM này đã có phát sinh chưa hoàn tất ở lệnh sản xuất, gia công, ban vẫn muốn cập nhật ngưng hoạt động");
				 if(t == false){ return false;}
			}
			
			
		}
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	
	function addRow(pos)
	{
		var table = $('#vattu_'+pos);
		table.append(
                '<tr>'+
                '<td style="display:none;"> <input type="text" name="vtIdTT_'+pos+'" style="width: 100%"  > </td>'+
        		'<td> <input type="text" name="mavtTT_'+pos+'" style="width: 100%"  onkeyup = "ajax_showOptions(this,\'abc\',event);" autocomplete =\'off\' > </td>'+
					'<td> <input type="text" name="tenvtTT_'+pos+'" style="width: 100%" readonly="readonly" > </td>'+
					'<td> <input type="text" name="dvtTT_'+pos+'" style="width: 100%" readonly="readonly"> </td>'+
					'<td> <input type="text" name="soluongTT_'+pos+'" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" > </td>'+
        			'<td><input type="text" name="pthaohutspTT_'+pos+'" style="width: 100%; text-align: right;" onkeypress="return keypress(event);"> </td>'+
				'</tr>'
    			);
	}
	
	function ThemSanPham(pos)
	{
		 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
		 if(isNaN(sl) == false && sl < 30)
		 {
			 for(var i=0; i < sl ; i++)
				 addRow(pos);
		 }
		 else
		 {
			 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
			 return;
		 }
	 }
	function removeVTTT(indexRow){
		
		var vtttList = document.getElementsByName("mavattuTT_"+indexRow);
		var soluongVtttList =  document.getElementsByName("soluongTT_"+indexRow);
		for(var i = 0; i < vtttList.length; i++)
		 {
			//alert(indexRow); 
			vtttList.item(i).value = "";
			soluongVtttList.item(i).value ="";
		 }
	}
	

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../ErpBomUpdateSvl">
<input type="hidden" name="checktontai" id ="checktontai" value='<%=obj.getChecktontai()%>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value="0"> <input type="hidden"
			name="id" value="<%=obj.getId()%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ
											liệu nền > Sản xuất > Danh mục vật tư (BOM) > Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%></TD>
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
										<TD width="30" align="left"><A
											href="../../ErpBomSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
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
									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										readonly="readonly" rows="3"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Danh
										mục vật tư (BOM) </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Đơn
												vị kinh doanh</TD>
											<TD class="plainlabel" valign="middle"><select
												name="dvkdid" id="dvkdid" onChange="submitform();">
													<option></option>

													<%
														if (rsdvkd != null) {
																while (rsdvkd.next()) {
																	if (rsdvkd.getString("pk_seq").equals(obj.getDvkdId())) {
													%>
													<option value="<%=rsdvkd.getString("pk_seq")%>"
														selected="selected"><%=rsdvkd.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rsdvkd.getString("pk_seq")%>"><%=rsdvkd.getString("ten")%></option>
													<%
														}
																}
																rsdvkd.close();
															}
													%>
											</select></TD>
											<TD class="plainlabel" valign="middle" width="15%">Tên sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="diengiai" value="<%=obj.getDiengiai()%>">
											</TD>

										</TR>

										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Mã
												định mức vật tư</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="tenbom" value="<%=obj.getTenBOM()%>">
											</TD>
											<TD class="plainlabel" valign="middle" width="15%">Ghi chú</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="vanbanhuongdan"
												value="<%=obj.getVanBanHuongDan()%>"></TD>

										</TR>

										<TR>
											<TD class="plainlabel" valign="middle">Loại sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><select
												name="loaisp" id="loaisp" onChange="submitform();">
													<%
														if (loaihhList != null) {
																while (loaihhList.next()) {
																	if (loaihhList.getString("pk_seq").equals(obj.getLoaispId())) {
													%>
													<option value="<%=loaihhList.getString("pk_seq")%>"
														selected="selected"><%=loaihhList.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=loaihhList.getString("pk_seq")%>"><%=loaihhList.getString("ten")%></option>
													<%
														}
																}
																loaihhList.close();
															}
													%>
											</select></TD>
											<TD class="plainlabel" valign="middle">Sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><select
												name="spId" id="sanpham" style="width: 70%;"
												onChange="submitform();">
													<option></option>
													<%
														if (spRs != null) {
																while (spRs.next()) {
																	if (spRs.getString("pk_seq").equals(obj.getSpMa())) {
													%>
													<option value="<%=spRs.getString("pk_seq")%>"
														selected="selected"><%=spRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ten")%></option>
													<%
														}
																}
															}
													%>
											</select></TD>
										</TR>
										<tr>
											<TD class="plainlabel" valign="middle">Dạng bào chế</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="dangbaoche"
												value="<%=obj.getDangbaoche()%>"></TD>
											<TD class="plainlabel" valign="middle">Quy cách</TD>
											<TD class="plainlabel" valign="middle">
											<%-- <input
												type="text" name="quycach" value="<%=obj.getquycach()%>"
												style="text-align: right;"> --%>
												<textarea name="quycach" style="width: 70%; color: black; height:50px;"
											><%= obj.getquycach() %></textarea>
												
												</TD>
										</tr>
										<TR>
											<TD class="plainlabel" valign="middle">Đơn vị tính</TD>
											<TD class="plainlabel" valign="middle"><select
												name="dvtSP">
													<option></option>

													<%
														while (rsdvdl.next()) {
													%>
													<%
														if (obj.getDonViTinh().equals(rsdvdl.getString("pk_seq"))) {
													%>
													<option value="<%=rsdvdl.getString("pk_seq")%>"
														selected="selected"><%=rsdvdl.getString("diengiai")%>
													</option>
													<%
														} else {
													%>
													<option value="<%=rsdvdl.getString("pk_seq")%>"><%=rsdvdl.getString("diengiai")%>
													</option>
													<%
														}
													%>
													<%
														}
													%>
											</select></TD>
											<TD class="plainlabel" valign="middle" width="7%">Hao
												hụt</TD>
											<TD class="plainlabel" width="230px"><input
												onkeypress="return keypress(event);" type="text"
												name="pthaohut" value="<%=obj.getPTHaoHut()%>"
												style="text-align: right;" onChange="submitform();">%
											</TD>

										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Số lượng chuẩn</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="soluongchuan"
												value="<%=obj.getSoluongchuan()%>"
												style="text-align: right;"></TD>
												<TD class="plainlabel" valign="middle">Nơi sản xuất</TD>
											<TD class="plainlabel" valign="middle" ><input
												type="text" name="noisanxuat"
												value="<%=obj.getNoisanxuat()%>"
												style="text-align: right;"></TD>
										</TR>

										<TR>
											<TD class="plainlabel" valign="middle">Hiệu lực từ</TD>
											<TD class="plainlabel" width="230px"><input class="days"
												type="text" name="hieuluctu"
												value="<%=obj.getHieuluctu()%>"></TD>
											<TD class="plainlabel" valign="middle" width="150px">Hiệu
												lực đến</TD>
											<TD class="plainlabel"><input class="days" type="text" style = "width:30%;"
												name="hieulucden" value="<%=obj.getHieulucden()%>">
												<%if(obj.getIskhongthoihan().equals("1")){ %>
												<input type="checkbox" style = "width:5%;" checked = "checked"
												name="ISKHONGTHOIHAN" value="1">
												<%} else { %>
												<input type="checkbox" style = "width:5%;"
												name="ISKHONGTHOIHAN" value="1">
												<%} %>
												<span style = "width:25%;">Đến khi thay đổi</span>
											</TD>
										</TR>

										<TR>
											<TD class="plainlabel" valign="middle">Ngày ban hành</TD>
											<TD class="plainlabel"><input type="text" name="ngayBH"
												class="days" value="<%=obj.getNgayBH()%>"></TD>

											<TD class="plainlabel" valign="middle">Lần ban hành</TD>
											<TD class="plainlabel" colspan="3"><input type="text"
												name="lanBH" value="<%=obj.getLanBH()%>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"  valign="middle"> Quy trình sản xuất</TD>
											<TD class="plainlabel"> 
												<input type="text" name="quytrinhsx" id= "quytrinhsx"  value="<%=obj.getQuytrinhsx()%>">
											</TD>
											
											<TD class="plainlabel"  valign="middle">Ngày ban hành QTSX</TD>
											<TD class="plainlabel"> 
												<input class="days" type="text" name="ngaybanhanhqtsx" id= "ngaybanhanhqtsx"  value="<%=obj.getNgaybanhanhQTSX() %>">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" colspan="2"></TD>
											
											<TD class="plainlabel"  valign="middle">Dây chuyền sản xuất</TD>
											<TD class="plainlabel"> 
												<input type="text" name="daychuyensanxuat" id= "daychuyensanxuat"  value="<%=obj.getDaychuyensanxuat() %>">
											</TD>
										</TR>

										<%
											if (!(obj.getLoaispId().equals("100008") || obj.getLoaispId().equals("100012"))) {
										%>
										<TR>
											<TD class="plainlabel" valign="middle" style="display: none;">Cho
												phép thay thế</TD>
											<TD class="plainlabel" valign="middle" style="display: none;">
												<%
													if (obj.getChophepTT().equals("1")) {
												%> <input
												type="checkbox" name="chophepTT" value="1" checked="checked">
												<%
													} else {
												%> <input type="checkbox" name="chophepTT"
												value="1"> <%
 	}
 %>


											</TD>

											<TD class="plainlabel" valign="middle">Sử dụng để lên
												kế hoạch</TD>
											<TD class="plainlabel" colspan="3">
												<%
													if (obj.getSudung().equals("1")) {
												%> <input type="checkbox"
												name="sudung" value="1" checked="checked"> <%
 	} else {
 %>
												<input type="checkbox" name="sudung" value="1"> <%
 	}
 %>
											</TD>

										</TR>


										<%
											}
										%>

										<TR class="plainlabel">
											<TD colspan="1">Hoạt động</TD>
											<TD class="plainlabel" colspan="3" align=left>
												<%
													if (obj.getTrangthai().equals("1")) {
												%> <input
												name="trangthai" type="checkbox" value="1" checked>
												<%
													} else {
												%> <input name="trangthai" type="checkbox" value="1">
												<%
													}
												%>
											</TD>

										</TR>
										<!-- pop-up -->
										<TR class="plainlabel">

											<TD colspan="1">Thành phẩm/BTP</TD>
											<td class="plainlabel" colspan="3" align=left><A href=""
												id="maTP" rel="subcontentMaThanhPham">&nbsp; <img
													alt="Thanh Pham them" src="../images/vitriluu.png">
											</A> &nbsp;
												<DIV id="subcontentMaThanhPham"
													style="position: absolute; visibility: hidden; border: 5px solid #80CB9B; background-color: white; width: 450px; max-height: 250px; overflow-y: scroll; padding: 4px;">

													<TABLE width="100%" border="0" cellspacing="2"
														cellpadding="4">
														<TR class="tbheader">
															<TH width="60%">Tên sản phẩm</TH>
															<TH width="20%">Đơn vị</TH>
															<TH width="20%">Định mức</TH>

														</TR>
														<%
															if (sptp != null) {
																	for (int j = 0; j < sptp.size(); j++) {
																		IErpDanhMucVatTuThanhPhan vt = sptp.get(j);
																	
														%>
														<TR>

															<TD><input type="text" name="sanphamthem"
																style="width: 100%; text-align: left;"
																onkeyup="ajax_showOptions(this,'addProduct',event);"
																value="<%=vt.getTenVTTP()%>"></TD>

															<TD><input type="text" name="donvithem"
																value="<%=vt.getTenDonVi()%>"
																style="width: 100%; text-align: right;"></TD>
															<TD><input type="text" name="soluongthem" value="<%=vt.getSoluong() %>"
																style="width: 100%; text-align: right;"></TD>
															<TD><input type="hidden" name="sanpham_fk"
																value="<%=vt.getSanpham_fk()%>"></TD>
															<TD><input type="hidden" name="dvdl_fk"
																value="<%=vt.getDvdl_fk()%>"></TD>
														</TR>
														<%
															}
																}
														%>

														<%
															for (int m = 0; m < 5; m++) {
														%>
														<TR>

															<TD><input type="text" name="sanphamthem"
																style="width:100%; text-align: left;"
																onkeyup="ajax_showOptions(this,'addProduct',event);"></TD>

															<TD><input type="text" name="donvithem" value=""
																style="width: 100%; text-align: right;"></TD>
															<TD><input type="text" name="soluongthem" value=""
																style="width: 100%; text-align: right;"></TD>
															<TD><input type="hidden" name="sanpham_fk" value=""></TD>
															<TD><input type="hidden" name="dvdl_fk" value=""></TD>
														</TR>
														<%
															}
														%>

													</TABLE>
													<div align="right">
														<label style="color:red"></label>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
															href="javascript:dropdowncontent.hidediv('subcontentMaThanhPham')">
															Đóng lại </a>
													</div>
												</DIV></td>


										</TR>
										<TR style="display: none">
											<TD class="plainlabel" colspan="4"><a class="button2"
												href="javascript:submitform();"> <img style="top: -4px;"
													src="../images/button.png" alt="">Cập nhật
											</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>
										<TR>
											<td colspan="4">
												<div id="tabContaier">
													<div class="titleTab">
														<a class="active" href="#tab_1">Nguyên liệu</a>
													</div>
													<div class="titleTab" style="display: none;">
														<a href="#tab_2">Định mức chi phí</a>
													</div>

													<div class="titleTab" style="display: none;">
														<a href="#tab_3">Chọn Phân xưởng </a>
													</div>
													<div class="titleTab" style="display: none;">
														<a href="#tab_4">Chọn kịch bản sản xuất</a>
													</div>

												</div>

												<div class="tabDetails">
													<div id="tab_1" class="tabContents" style="display: block">

														<table width="100%" cellpadding="4" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="3%">STT</th>
																<th align="center" width="8%">Mã nguyên liệu</th>
																<th align="center" width="15%">Tên nguyên liệu</th>
																<th align="center" width="7%">Đơn vị</th>
																<th align="center" width="7%">Định mức</th>
																<th align="center" width="7%">Dung sai (%)</th>
																<th align="center" width="7%">Nguyên liệu thay thế</th>
																<th align="center" width="7%">Hàm ẩm (%)</th>
																<th align="center" width="7%">Tính hàm ẩm khi SX</th>
																<th align="center" width="7%">Hàm lượng (%)</th>
																<th align="center" width="7%">Tính hàm lượng khi SX</th>


																<th align="center" width="7%">Hao hụt (%)</th>
																<th align="center" width="7%">Ghi chú</th>
																<th align="center" width="7%">Đóng dư</th>


																<th align="center" width="9%">Nhập tiêu hao <input
																	type="checkbox" name="allTieuHao" value='1'
																	onclick="checkedAll(document.khtt.nltieuhao,document.khtt.allTieuHao);">

																</th>


															</tr>
															<%
																int count = 0;
																	if (vattuList.size() > 0) {
																		for (int i = 0; i < vattuList.size(); i++) {
																			IDanhmucvattu_SP vattu = vattuList.get(i);
															%>
															<tr>
																<td align="center" style="font-size: 8pt"><%=count + 1%>
																	<input type="hidden" name="vtId"
																	value="<%=vattu.getIdVT()%>" style="width: 100%">
																</td>

																<td><input type="text" name="mavattu"
																	value="<%=vattu.getMaVatTu()%>" style="width: 100%"
																	onkeyup="ajax_showOptions(this,'abc',event);"
																	autocomplete='off' onchange="removeVTTT(<%=count%>)" onblur="kiemTraVT(<%=count %>)"> 
																</td>
																<td><input type="text" name="tenvattu"
																	value="<%=vattu.getTenVatTu()%>" style="width: 100%"
																	readonly="readonly"></td>
																<td><input type="text" name="donvitinh"
																	value="<%=vattu.getDvtVT()%>" style="width: 100%;"
																	readonly="readonly"></td>
																<%-- <td><input type="text" name="soluong" value="<%= formatter3.format(Double.parseDouble(vattu.getSoLuong())) %>" style="width: 100%; text-align: right;"
																				onkeypress="return keypress(event);">
																			</td> --%>


																<td><input type="text" name="soluong"
																	value="<%=Double.parseDouble(vattu.getSoLuong())%>"
																	style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);"></td>
																	
																<td><input type="text" name="dungsai" value="<%=vattu.getDungsai()%>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);"></td>

																<td align="center"><A href=""
																	id="maNLTT<%=count%>"
																	rel="subcontentMaNLTT<%=count%>">&nbsp; <img
																		alt="Mã vật tư thay thế" src="../images/vitriluu.png">
																</A> &nbsp;
																	<DIV id="subcontentMaNLTT<%=count%>"
																		style="position: absolute; visibility: hidden; border: 5px solid #80CB9B; background-color: white; width: 450px; max-height: 250px; overflow-y: scroll; padding: 4px;">

																		<TABLE width="100%" border="0" cellspacing="2"
																			cellpadding="4">
																			<TR class="tbheader">
																				<TH width="70%">Mã nguyên liệu</TH>
																				<TH width="30%">Định mức</TH>
																			</TR>
																			<%
																				ResultSet rs = obj.getVattuTT(vattu.getIdVT());
																							int n = 0;
																							if (rs != null) {
																								while (rs.next()) {
																			%>
																			<TR>
																				<TD><input type="text"
																					name="<%="mavattuTT_" + count%>"
																					value="<%=rs.getString("TEN")%>"
																					style="width: 100%"
																					onkeyup="ajax_showOptions(this,'abc',event);"
																					autocomplete='off'></TD>

																				<TD><input type="text"
																					name="<%="soluongTT_" + count%>"
																					value="<%=rs.getString("SOLUONG")%>"
																					style="width: 100%; text-align: right;"
																					onkeypress="return keypress(event);"></TD>
																			</TR>

																			<%
																				n++;

																								}
																							}
																							for (int m = n; m < 5; m++) {
																			%>
																			<TR>
																				<TD><input type="text"
																					name="<%="mavattuTT_" + count%>" value=""
																					style="width: 100%"
																					onkeyup="ajax_showOptions(this,'abc',event);"
																					autocomplete='off'></TD>

																				<TD><input type="text"
																					name="<%="soluongTT_" + count%>" value=""
																					style="width: 100%; text-align: right;"
																					onkeypress="return keypress(event);"></TD>
																			</TR>
																			<%
																				}
																			%>

																		</TABLE>
																		<div align="right">
																			<label style="color: red"></label>
																			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
																				href="javascript:dropdowncontent.hidediv('subcontentMaNLTT<%=count%>')">Đóng
																				lại </a>
																		</div>
																	</DIV></td>

																<td><input type="text" name="hamam"
																	value="<%=vattu.getHamam()%>"
																	style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);"></td>

																<%
																	if (vattu.getIsTinhHA().equals("1")) {
																%>
																<Td align="center"><input id="tinhHA"
																	name="tinhHA_<%=i%>" type="checkbox" value="1" checked>
																</Td>
																<%
																	} else {
																%>
																<Td align="center"><input id="tinhHA"
																	name="tinhHA_<%=i%>" type="checkbox" value="1">
																</Td>
																<%
																	}
																%>

																<td><input type="text" name="hamluong"
																	value="<%=vattu.getHamluong()%>"
																	style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);"></td>


																<%
																	if (vattu.getIsTinhHL().equals("1")) {
																%>
																<Td align="center"><input id="tinhHL"
																	name="tinhHL_<%=i%>" type="checkbox" value="1" checked>
																</Td>
																<%
																	} else {
																%>
																<Td align="center"><input id="tinhHL"
																	name="tinhHL_<%=i%>" type="checkbox" value="1">
																</Td>
																<%
																	}
																%>


																<td><input type="text" name="pthaohutsp"
																	value="<%=vattu.getHaoHut()%>"
																	onkeypress="return keypress(event);"
																	style="width: 100%; text-align: right;"></td>
																<td align="left"><input type="text" name="dongdu"
																	value="<%=vattu.getDongdu()%>"
																	style="width: 100%; text-align: right;"></td>
																<td align="left"><input type="text" name="ghichuct"
																	value="<%=vattu.getGhichu()%>"
																	style="width: 100%; text-align: right;"></td>
																<%
																	if (vattu.getIsNLTieuHao().equals("1")) {
																%>
																<Td align="center"><input id="nltieuhao"
																	name="nltieuhao_<%=i%>" type="checkbox" value="1"
																	checked></Td>
																<%
																	} else {
																%>
																<Td align="center"><input id="nltieuhao"
																	name="nltieuhao_<%=i%>" type="checkbox" value="1"></Td>
																<%
																	}
																%>

															</tr>
															<%
																count++;
																		}
																	}
															%>


															<%
																int bien = count + 30;
																	while (count <= bien) {
															%>
															<tr>
																<td><input type="hidden" name="vtId"
																	style="width: 100%"></td>

																<td><input type="text" name="mavattu"
																	style="width: 100%"
																	onkeyup="ajax_showOptions(this,'abc',event)" onblur="kiemTraVT(<%=count %>)"
																	autocomplete='off'></td>

																<td><input type="text" name="tenvattu"
																	style="width: 100%" readonly="readonly"></td>
																<td><input type="text" name="donvitinh"
																	style="width: 100%" readonly="readonly"></td>
																<td><input type="text" name="soluong"
																	style="width: 100%; text-align: right;"></td>
																	
																<td><input type="text" name="dungsai" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);"></td>

																<td align="center"><A href=""
																	id="maNLTT<%=count%>"
																	rel="subcontentMaNLTT<%=count%>">&nbsp; <img
																		alt="Mã vật tư thay thế" src="../images/vitriluu.png">
																</A> &nbsp;
																	<DIV id="subcontentMaNLTT<%=count%>"
																		style="position: absolute; visibility: hidden; border: 5px solid #80CB9B; background-color: white; width: 450px; max-height: 250px; overflow-y: scroll; padding: 4px;">

																		<TABLE width="100%" border="0" cellspacing="2"
																			cellpadding="4">
																			<TR class="tbheader">
																				<TH width="70%">Mã nguyên liệu</TH>
																				<TH width="30%">Định mức</TH>
																			</TR>
																			<%
																				for (int m = 0; m < 5; m++) {
																			%>
																			<TR>
																				<TD><input type="text"
																					name="<%="mavattuTT_" + count%>" value=""
																					style="width: 100%"
																					onkeyup="ajax_showOptions(this,'abc',event);"
																					autocomplete='off'></TD>

																				<TD><input type="text"
																					name="<%="soluongTT_" + count%>" value=""
																					style="width: 100%; text-align: right;"
																					onkeypress="return keypress(event);"></TD>
																			</TR>
																			<%
																				}
																			%>

																		</TABLE>
																		<div align="right">
																			<label style="color: red"></label>
																			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
																				href="javascript:dropdowncontent.hidediv('subcontentMaNLTT<%=count%>')">Đóng
																				lại </a>
																		</div>
																	</DIV></td>
																<td><input type="text" name="hamam"
																	style="width: 100%; text-align: right;"></td>
																<Td align="center"><input id="tinhHA"
																	name="tinhHA_<%=count%>" type="checkbox" value="1"></Td>

																<td><input type="text" name="hamluong"
																	style="width: 100%; text-align: right;"></td>
																<Td align="center"><input id="tinhHL"
																	name="tinhHL_<%=count%>" type="checkbox" value="1"></Td>

																<td><input type="text" name="pthaohutsp" value=""
																	onkeypress="return keypress(event);"
																	style="width: 100%; text-align: right;"></td>
																<td align="left"><input type="text" name="dongdu"
																	value="" style="width: 100%; text-align: right;">
																</td>
																<td align="left"><input type="text" name="ghichuct"
																	value="" style="width: 100%; text-align: right;">
																</td>

																<Td align="center"><input id="nltieuhao"
																	name="nltieuhao_<%=count%>" type="checkbox" value="1"></Td>



															</tr>
															<%
																count++;
																	}
															%>
														</table>
													</div>
													<div id="tab_2" class="tabContents" style="display: none">
														<table width="100%" cellpadding="0" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="25%">Tên chi phí</th>
																<th align="center" width="10%">Đơn vị tính</th>
																<th align="center" width="10%">Định mức</th>
																<th align="center" width="10%">Đơn giá</th>
																<th align="center" width="10%">Thành tiền</th>
															</tr>
															<%
																for (int i = 0; i < obj.getDinhmucList().size(); i++) {
																		IErpDinhmuc dm = obj.getDinhmucList().get(i);
																		if (dm.getLoai().equals("0")) {
															%>
															<tr>
																<td><input type="hidden" name="dmid"
																	value="<%=dm.getId()%>" style="width: 100%">
																	<input type="hidden" name="dmloai"
																	value="<%=dm.getLoai()%>" style="width: 100%">
																	<input type="text" value="<%=dm.getTen()%>"
																	style="width: 100%" readonly="readonly" /></td>
																<td><input type="text" value="<%=dm.getDVT()%>"
																	style="width: 100%" readonly="readonly" /></td>
																<td><input type="text" name="dmsoluong"
																	value="<%=formatter2.format(dm.getSoluong())%>"
																	style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);" /></td>
																<td><input type="text" name="dmdongia"
																	value="<%=formatter2.format(dm.getDongia())%>"
																	style="width: 100%; text-align: right;"
																	readonly="readonly" /></td>
																<td><input type="text" name="dmthanhtien" value=""
																	style="width: 100%; text-align: right;"
																	readonly="readonly" /></td>
															</tr>
															<%
																} else {
															%>
															<tr>
																<td colspan="4"><input type="hidden" name="dmid"
																	value="<%=dm.getId()%>" style="width: 100%">
																	<input type="hidden" name="dmloai"
																	value="<%=dm.getLoai()%>" style="width: 100%">
																	<input type="text" value="<%=dm.getTen()%>"
																	style="width: 100%" readonly="readonly" /></td>
																<td><input type="text" name="dmthanhtien"
																	value="<%=formatter2.format(dm.getSoluong())%>"
																	style="width: 100%; text-align: right;" /></td>
															</tr>
															<%
																}
															%>
															<%
																}
															%>
														</table>
													</div>
													<div id="tab_3" class="tabContents" style="display: none">
														<table width="100%" cellpadding="0" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="25%">Tên Phân xưởng</th>

																<th align="center" width="10%">Chọn</th>

															</tr>
															<%
																ResultSet rsnhamay = obj.getrsNhamay();
																	try {
																		if (rsnhamay != null)
																			while (rsnhamay.next()) {
															%>
															<tr>
																<td align="left" width="90%"><%=rsnhamay.getString("ten")%></td>
																<td align="left" width="10%">
																	<%
																		if (obj.getIdNhamay().indexOf(rsnhamay.getString("PK_SEQ")) >= 0) {
																	%>
																	<input onChange="submitform();" type="checkbox"
																	value="<%=rsnhamay.getString("PK_SEQ")%>"
																	checked="checked" name="nhamayid"> <%
 	} else {
 %>
																	<input onChange="submitform();" type="checkbox"
																	value="<%=rsnhamay.getString("PK_SEQ")%>"
																	name="nhamayid"> <%
 	}
 %>
																</td>
															</tr>

															<%
																}
																		rsnhamay.close();
																	} catch (Exception er) {
																	}
															%>

														</table>
													</div>
													<div id="tab_4" class="tabContents" style="display: none">
														<table width="60%" cellpadding="0" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="25%">Kịch bản sản xuất</th>

																<th align="center" width="10%">Chọn</th>

															</tr>
															<%
																ResultSet rskb = obj.getrsKichBanSX();
																	try {
																		if (rskb != null)
																			while (rskb.next()) {
															%>
															<tr>
																<td align="left" width="90%"><%=rskb.getString("ten")%></td>
																<td align="left" width="10%">
																	<%
																		if (obj.getKichBanSXId().indexOf(rskb.getString("PK_SEQ")) >= 0) {
																	%>
																	<input type="checkbox"
																	value="<%=rskb.getString("PK_SEQ")%>"
																	checked="checked" name="kbsxid"> <%
 	} else {
 %> <input
																	type="checkbox" value="<%=rskb.getString("PK_SEQ")%>"
																	name="kbsxid"> <%
 	}
 %>
																</td>
															</tr>

															<%
																}
																		rskb.close();
																	} catch (Exception er) {
																	}
															%>

														</table>
													</div>
												</div>
											</TD>
										</TR>
									</TABLE>

								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
	<script type="text/javascript">

		replaces();
		 
	</script>

	<script type="text/javascript"> 
	  <%for (int k = 0; k < count; k++) {%>
	   
		dropdowncontent.init("maNLTT<%=k%>", "right-top", 100, "click");
	   
	  <%}%>

	  dropdowncontent.init("maTP","right-top",500,"click");
	</script>
</BODY>
</HTML>
<%
	if (spRs != null)
			spRs.close();
		if (rsVattu != null)
			rsVattu.close();
		if (loaihhList != null)
			loaihhList.close();
		if (rsdvkd != null)
			rsdvkd.close();
		if (rsdvdl != null) {
			rsdvdl.close();
		}
		if (vattuList != null) {
			vattuList.clear();
		}

		obj.DbClose();
		session.setAttribute("bomBean", null);
	}
%>
