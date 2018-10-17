<%@page import="java.util.ArrayList"%>
<%@page import="geso.traphaco.erp.beans.taisancodinh.IPhanBo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IErp_CongCuDungCu"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.imp.Erp_CongCuDungCu"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

	IErp_CongCuDungCu obj = (Erp_CongCuDungCu) session.getAttribute("obj");
	if (obj == null)
		System.out.println("Null--------");

	ResultSet nccdcList = obj.getRsNccdc();
	ResultSet LccdcList = obj.getRsLoaiCCDC();
	ResultSet lccdcList = obj.getRsLoaiCCDC();
	ResultSet cdList = obj.getRscd();
	ResultSet DvthList = obj.getDvthList();
	ResultSet khRs = obj.Laykhauhao();	
	ResultSet TtcpRs = obj.getRsTtcp();
	
	
	List<IPhanBo> phanBoList  = new ArrayList<IPhanBo>();
	ResultSet kmcpRs =  obj.getChonkhoanmucchiphi();
	
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>    

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script>
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
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
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />



<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<script type="text/javascript">

function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed==45)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
      return false;

   return true;
}

function UpdateTT()
{
	var soluong=document.getElementById("soluong");
	var dongia=document.getElementById("dongia");
	var thanhtien=document.getElementById("thanhtien");
	var sl=soluong.value.replace(/,/g,"");
	var dg=dongia.value.replace(/,/g,"");
	var tt=parseFloat(sl)*parseFloat(dg);
	thanhtien.value=DinhDangDonGia(tt);
	soluong.value=DinhDangDonGia(soluong.value);
	dongia.value=DinhDangDonGia(dongia.value);
	
}

function TinhKH(){
	var maccdc =document.getElementById("ma");
	var error=document.getElementById("error");
	var sl=document.getElementById("soluong");
	var dg=document.getElementById("dongia");
	
	var tenccdc = document.getElementById("ten");
	var nccdc = document.getElementById("nccdc");
	var ct =  document.getElementById("ct");
	var cd =  document.getElementById("cd");
	var ttcp =  document.getElementById("ttcp");
	var dvt = document.getElementById("dvt");
	var stkh =document.getElementById("stkh");
	var tbdkh = document.getElementById("tbdkh");
	var ppkh =  document.getElementById("ppkh");
	
	if(maccdc.value == "")                        
	{
		maccdc.focus();
		error.value= "Vui lòng nhập mã Chi phí trả trước.";
		return;              
	}
	if(tenccdc.value  == "")
	{
		tenccdc.focus();
		error.value = "Vui lòng nhập tên Chi phí trả trước.";
		return;
	}

	if(nccdc.value  == "")
	{
		nccdc.focus();
		error.value = "Vui lòng chọn loại Chi phí trả trước.";
		return;
	}
	
	
	if(cd.value == "")
	{
		cd.focus();
		error.value= "Vui lòng chọn công dụng.";
		return;
	}
	if(ttcp.value  == "")
	{
		ttcp.focus();
		error.value = "Vui lòng chọn trung tâm chi phí.";
		return;
	}
	
	
	document.forms['ccdcForm'].khauhao.value='khauhao';
	document.forms["ccdcForm"].submit();
	
}

function searchForm()
{
	document.forms['ccdcForm'].action.value='search';
	document.forms["ccdcForm"].submit();
}

function submitform(){
/*	var maccdc =document.getElementById("ma");
	var diengiai = document.getElementById("diengiai");
	
	var nccdcId = document.getElementById("nccdcId");
	//var nId = document.getElementById("nId");
	//var stkh =document.getElementById("stkh");
	//var tbdkh = document.getElementById("tbdkh");
	var ttcp = document.getElementById("ttcpId");
	
	if(maccdc.value == "")                        
	{
		maccdc.focus();
		error.value= "Vui lòng nhập Mã Chi phí trả trước.";
		return;              
	}
	
	if(diengiai.value  == "")
	{
		diengiai.focus();
		error.value = "Vui lòng nhập Diễn giải Chi phí trả trước.";
		return;
	}

	if(nccdcId.value  == "" )
	{
		nccdcId.focus();
		error.value = "Vui lòng chọn nhóm Chi phí trả trước.";
		return;
	}

	if(ttcp.value  == "")
	{
		ttcp.focus();
		error.value = "Vui lòng chọn trung tâm chi phí.";
		return;
	}*/
	
	document.forms["ccdcForm"].action.value='load';
	document.forms["ccdcForm"].submit();
}

function Save()
{
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['ccdcForm'].action.value='save';
	document.forms["ccdcForm"].submit();
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
	num = "";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	num = Math.floor(num/100).toString();
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));

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
function monthDiff(d1, d2) {
	 var months;
	    months = 11 - d1.getMonth();  
	      months += d2.getMonth() + 1;  
	      months += (d2.getFullYear() - d2.getFullYear() - 1) * 12;  
	    return months;
}

function removeChildrenFromNode(node)
{
   if(node == undefined && node == null)
   {
      return;
   }

   var len = node.childNodes.length;

   for(var i = 2; i < len; i++)
   {
      node.removeChild(node.childNodes[i]);
   }
}

function TinhKhauHao()
{
	var thanhtien = document.getElementById("thanhtien");
	var soluong = document.getElementById("soluong");
	var dongia = document.getElementById("dongia");
	var stkh = document.getElementById("stkh");
	var tt = thanhtien.value.replace(/,/g,"");
	 
	var tbdkh = document.getElementById("tbdkh");
	var s = tbdkh.value.split('-');
	var date=new Date();
	date.setFullYear(s[0],s[1]-1,s[2]);
	var current=new Date();
	//So thang khac nhau giua 2 khoang thoi gian
	var numb = monthDiff(date,current);
	
	
	var khdkVal = new Array();
	var lkdkVal = new Array();
	var gtdkVal=new Array();
	
	var khttVal=new Array();
	var lkttVal=new Array();
	var gtttVal=new Array();
	var n = stkh.value;
	lkdkVal[0]=0;
	
	var Master = document.getElementById("Master");
	removeChildrenFromNode(Master);
	
	for(var i=1;i <= n;i++)
	{
		var day = date.getDate();
		var month = date.getMonth()+1;
		var year = date.getFullYear();
		date.setMonth(date.getMonth()+1);
		
		var displayDate = '';
		if(month < 10)
			displayDate = year + '-' + '0' + month + '-' + '0' + day;
		else if(day<10)
			displayDate = year + '-' + month + '-'+ '0'+ day;
			else displayDate = year + '-' + month + '-'+day;
		
		 if(i == n)
		 {
			khdkVal[i] = tt-lkdkVal[i-1];
		 	lkdkVal[i] = khdkVal[i]+lkdkVal[i-1];
		 	gtdkVal[i] = tt-lkdkVal[i];
		 }
		 else
		 {
		 	khdkVal[i] = Math.round(tt/(stkh.value));
		 	lkdkVal[i] = khdkVal[i]+lkdkVal[i-1];
		 	gtdkVal[i] = tt-lkdkVal[i];
		 }
		 
		for(var j=1 ; j <= numb + 1 ; j++)
		{
			khttVal[j] = khdkVal[i];
			lkttVal[j] = lkdkVal[i];
			gtttVal[j] = gtdkVal[i];
		}
		
		if(khttVal[i]==undefined) khttVal[i]='';
		if(lkttVal[i]==undefined) lkttVal[i]='';
		
		if(gtttVal[i]==undefined) gtttVal[i]='';
		
		var	Details = document.createElement("TR");
		Details.setAttribute("id", "Details");
		Details.name='Details';

		var sttAdd = document.createElement("TD");
		var thangAdd = document.createElement("TD");
		var khdkAdd = document.createElement("TD");
		var gtdkAdd = document.createElement("TD");
		var lkdkAdd = document.createElement("TD");
		var khttAdd = document.createElement("TD");
		var lkttAdd = document.createElement("TD");
		var gtttAdd = document.createElement("TD");
	
		var stt = document.createElement("input");
		stt.setAttribute("type", "text");
		stt.setAttribute("readonly", "readonly");
		stt.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
		stt.setAttribute("value",i);
		stt.name = 'stt';
		sttAdd.appendChild(stt);
	
		var thang = document.createElement("input");
		thang.setAttribute("type", "text");
		thang.setAttribute("readonly", "readonly");
		thang.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		thang.setAttribute("value",displayDate);
		thang.name = 'thang';
		thangAdd.appendChild(thang);
	
		var khdk = document.createElement("input");
		khdk.setAttribute("type", "text");
		khdk.setAttribute("readonly", "readonly");
		khdk.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		khdk.setAttribute("value",DinhDangDonGia(khdkVal[i]));
		khdk.name = 'khdk';
		khdkAdd.appendChild(khdk);
	
		var lkdk = document.createElement("input");
		lkdk.setAttribute("type", "text");
		lkdk.setAttribute("readonly", "readonly");
		lkdk.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		lkdk.setAttribute("value",DinhDangDonGia(lkdkVal[i]));
		lkdk.name = 'lkdk';
		lkdkAdd.appendChild(lkdk);
		
		var gtdk = document.createElement("input");
		gtdk.setAttribute("type", "text");
		gtdk.setAttribute("readonly", "readonly");
		gtdk.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		gtdk.setAttribute("value",DinhDangDonGia(gtdkVal[i]));
		gtdk.name = 'gtdk';
		gtdkAdd.appendChild(gtdk);
	
		var khtt = document.createElement("input");
		khtt.setAttribute("type", "text");
		khtt.setAttribute("readonly", "readonly");
		khtt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		khtt.setAttribute("value",khttVal[i]);
		khtt.name = 'khtt';
		khttAdd.appendChild(khtt);
	
		var lktt = document.createElement("input");
		lktt.setAttribute("type", "text");
		lktt.setAttribute("readonly", "readonly");
		lktt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		lktt.setAttribute("value",lkttVal[i]);
		lktt.name = 'lktt';
		lkttAdd.appendChild(lktt);
	
		var gttt = document.createElement("input");
		gttt.setAttribute("type", "text");
		gttt.setAttribute("readonly", "readonly");
		gttt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		gttt.setAttribute("value",DinhDangDonGia(gtttVal[i]));
		gttt.name = 'gttt';
		gtttAdd.appendChild(gttt);

		Details.appendChild(sttAdd);
		Details.appendChild(thangAdd);
		Details.appendChild(khdkAdd);
		Details.appendChild(lkdkAdd);
		Details.appendChild(gtdkAdd);
		Details.appendChild(khttAdd);
		Details.appendChild(lkttAdd);
		Details.appendChild(gtttAdd);
	
		Master.appendChild(Details);
		Master.removeAttribute("Style");
	}
	
	
}

function GenerateHtml()
{
	TinhKhauHao();
}
function xuatExcel()
{
	document.forms['ccdcForm'].action.value='Excel';
	document.forms["ccdcForm"].submit();
}

</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ccdcForm" method="post" action="../../Erp_CongCuDungCuUpdateSvl">
<input type="hidden" name="Id" value='<%= obj.getId() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="save">
<INPUT name="khauhao" type="hidden" value="1">

<INPUT name="id" type="hidden" value="<%=obj.getId()%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">Quản lý Chi phí trả trước &gt; Chi phí trả trước &gt; Tạo mới</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD  width="30" align="left" ><A href="/TraphacoHYERP/Erp_CongCuDungCuSvl"> <img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
										</A></TD>
										
										<TD width="2" align="left"></TD>
										<TD  width="30" align="left" >
										<div id="btnSave">
										<A href="javascript: Save()"><IMG src="../images/Save30.png"
												title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
												</div></TD>
												
                                		<TD>&nbsp; </TD>						
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" id="error" style="width: 100%" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin Chi phí trả trước </LEGEND>
									<TABLE width="100%"  cellspacing="0" cellpadding="6">
									
										<TR  class="plainlabel">
											<TD style="display: none;">Nhóm Chi phí trả trước<FONT class="erroralert" > *</FONT></TD>
											<TD style="display: none;">
												<SELECT style="display: none;" name="nccdcId" id="nccdcId" onChange = "submitform();">
													<option value=""></option>
													<%
														try {
															while (nccdcList.next()) {
																if (obj.getNccdcId().equals(nccdcList.getString("pk_seq"))) {
													%>
													<option value='<%=nccdcList.getString("pk_seq")%>' selected><%=nccdcList.getString("diengiai")%></option>
													<%
														} else {
													%>
													<option value='<%=nccdcList.getString("pk_seq")%>'><%=nccdcList.getString("diengiai")%></option>
													<%
														}
															}
															nccdcList.close();
														} catch (java.sql.SQLException e) {
														}
													%>
												</SELECT>
											</TD>
										</TR>
											<TR  class="plainlabel" >
											<TD>Loại chi phí trả trước<FONT class="erroralert "> *</FONT></TD>
											<TD  colspan = "3"    >
												<SELECT style="width: 200px " name="LccdcId" id="LccdcId"  onChange = "submitform();">
													<option value=""></option>
													<%
														try {
															while (LccdcList.next()) {
																if (obj.getLccdcId().equals(LccdcList.getString("pk_seq"))) {
													%>
													<option value='<%=LccdcList.getString("pk_seq")%>' selected><%=LccdcList.getString("TEN")%></option>
													<%
														} else {
													%>
													<option value='<%=LccdcList.getString("pk_seq")%>'><%=LccdcList.getString("TEN")%></option>
													<%
														}
															}
															LccdcList.close();
															
														} catch (java.sql.SQLException e) {
														}
													%>
												</SELECT>
										</TR>
										
										
										<TR>
											<TD width="15%" class="plainlabel">Mã <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><INPUT type="text" name="ma" id="ma" size="40" type="text" value="<%=obj.getMa()%>" onChange = "submitform();"></TD>
												
											<TD width="15%" class="plainlabel">Diễn giải <font class="erroralert">*</font></TD>
											
											<TD class="plainlabel"><input type="text" name="diengiai" id="diengiai" value="<%=obj.getDiengiai() %>" onChange = "submitform();"></TD>											
												
										</TR>
										
										
										<TR>
											<TD width="15%" class="plainlabel">Số lượng </TD>
											<TD class="plainlabel">
												<INPUT type="text" name="soluong" id="soluong" 	value="<%=obj.getSoLuong()%>" onkeypress="return keypress(event);"  readonly="readonly">
											</TD>

											<TD class="plainlabel">Đơn vị tính</TD>
											<TD  class="plainlabel">
												<INPUT type="text" name="dvt" id="dvt" 	value="<%=obj.getDvt() %>"   readonly="readonly">
											</TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Đơn giá </TD>
											<TD  class="plainlabel"><input type="text" name="dongia" id="dongia"
												value="<%=formatter.format(Double.parseDouble(obj.getDonGia()))%>" onkeypress="return keypress(event);"  readonly="readonly"></TD>

											<TD width="15%" class="plainlabel">Thành tiền </TD>
											<TD  class="plainlabel">
												<INPUT type="text" name="thanhtien" id="thanhtien" value="<%= formatter.format(Double.parseDouble(obj.getThanhTien()))%>" readonly="readonly"></TD>
											
										</TR>

										<TR>
											<TD width="15%" class="plainlabel">Số tháng khấu hao <font class="erroralert">*</font></TD>
											
											<TD class="plainlabel"><input type="text" name="stkh" id="stkh" value="<%=obj.getSothangKH()%>" onkeypress="return keypress(event);" maxlength="4" onChange = "submitform();"></TD>

											<TD width="15%" class="plainlabel">Ngày bắt đầu khấu hao <font class="erroralert">*</font>	</TD>
											
											<TD class="plainlabel">
												<input class="days" type="text" name="tbdkh" id="tbdkh" value="<%=obj.getThangbatdauKH()%>"  maxlength="10" onChange = "submitform();"></TD> 				
												
												
										</TR>
										<TR>
											<TD class="plainlabel">Phòng ban<FONT class="erroralert"> *</FONT></TD>
											
											<TD class="plainlabel" colspan = 3><select name="dvthId" id="dvthId" style="width: 200px" >
														<option value=""></option>
														<%
															if (DvthList != null)
																while (DvthList.next()) {
																	if (obj.getDvthId().equals(
																			DvthList.getString("PK_SEQ"))) 
																	{
														%>
															<option value="<%=DvthList.getString("PK_SEQ")%>" selected="selected"><%=DvthList.getString("Ten")%></option>
														<%
															} else {
														%>
															<option value="<%=DvthList.getString("PK_SEQ")%>"><%=DvthList.getString("Ten")%></option>
														<%
															}
														}
														%>
												</select>
											</TD>											

											
										</TR>
										
										
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						<TR>
							<td>
															
								<ul class="tabs">
									 <li><a href="#" class= "legendtitle"> Khoản mục chi phí</a></li>        														
																
								</ul>
									
								<div class="panes">
                                        <div align = "left">
                                        <TABLE style="width: 30%" cellpadding="2px" cellspacing="1px" align="center">
                                          <TR><TD class= "legendtitle" >Chọn khoản mục chi phí</TD></TR>
                                        </TABLE>
                                      
                                        <TABLE style="width: 30%" cellpadding="2px" cellspacing="1px" align="center">
                                          <TR class="tbheader">
                                            <TH width="20%" align="left">Khoản mục phí</TH>
                                            <TH width="20%" align="left">Phần trăm (%)</TH>
                                          </TR>
                  
                                      <% 
                                     String lightrow = "tblightrow";
                                     String darkrow = "tbdarkrow";
                                     
                                     phanBoList=obj.getPhanBoList();
                                  	 for (int i=0; i<phanBoList.size();i++ ) 
                                  	 {
                                  	    IPhanBo phanBo= phanBoList.get(i);
                                       %>
                  						<TR>
                                          <TD  align="left" width="100%">
                        		
                        					<select style="width: 600px" name="khoanMucId" onchange="submitform();" >
											<option value  = ""></option>
                        		<%			
                        					if(kmcpRs!=null){
	                        					kmcpRs.beforeFirst();
	                        					
				                        		while (kmcpRs.next())
				                        		{  
				                        			if(phanBo.getKhoanMucId().equals(kmcpRs.getString("NCPID") )){
				                        		%>
													<option  selected="selected" value="<%= kmcpRs.getString("NCPID")%>"> <%= kmcpRs.getString("DIENGIAI") %> </option>
												<%}else{ %>
													<option value="<%= kmcpRs.getString("NCPID")%>"> <%= kmcpRs.getString("DIENGIAI") %> </option>
												<%} %>
				                        		<%  } %> 
				                        	<% } %>
			
											 </select>
								 
											</TD>
                                            
                                          <TD  align="left"  width="100%">
                                            
                                            <INPUT TYPE = "text" name="phanTramKhauHao" value="<%= phanBo.getPhanTram() %>"  onkeypress="return isNumberKey(event)">
                                          </TD>
                                        </TR>
                  	                 <%} %>
                  	                 <TR>
                                          <TD  align="left" width="100%">
                        		
                        					<select  style="width: 600px" name="khoanMucId" onchange="submitform();" >
											<option value  = ""></option>
                        					<%			
                        					if(kmcpRs!=null){
	                        					kmcpRs.beforeFirst();
	                        					while (kmcpRs.next())
				                        		{ 
	                        					%>
				                         
												<option value="<%= kmcpRs.getString("NCPID")%>"> <%= kmcpRs.getString("DIENGIAI") %>  </option>
				                        		<%  } %> 
				                        	<% } %>
			
											 </select>
								 
											</TD>
                                            
                                          <TD  align="left"  width="100%">
                                            
                                            <INPUT TYPE = "text" name="phanTramKhauHao" value="" onkeypress="return isNumberKey(event)">
                                          </TD>
                                        </TR>
                                                              
                                        
                                        </TABLE>  
                                      </div>
                
						
							</td>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>
<% 
try{
	if(nccdcList != null) nccdcList.close();
	if(lccdcList != null) lccdcList.close();
	if(cdList != null) cdList.close();
	if(khRs != null) khRs.close();	
	if(TtcpRs != null) TtcpRs.close();
	if (kmcpRs != null)
		kmcpRs.close(); 

}catch(java.sql.SQLException e){
	e.printStackTrace();
}finally{
	obj.DBClose();
	session.setAttribute("obj", null) ; 
}
}%>