<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.danhmucquyen.IDanhmucquyen"%>
<%@ page import="geso.traphaco.center.beans.kho.IKho"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");

	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else
	{
		IDanhmucquyen obj = (IDanhmucquyen) session.getAttribute("obj");
		ResultSet ungdungRs = (ResultSet) obj.getUngdungRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>  
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<style type="text/css">
#sku tr:HOVER {
	cursor: pointer;
	background: #E5E0E0;
}
</style>
<style type="text/css">
#sku1 tr:HOVER {
	cursor: pointer;
	background: #E5E0E0;
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
    document.forms["khoForm"].submit();
}
function changeView()
{
	document.forms["khoForm"].action.value="loaiMenu";
	submitform();
}

function check_all(checkid, doituong)
{   
	 var spIds = document.getElementsByName(doituong);
	 for(var i = 0; i < spIds.length; i++)
	 {
		 spIds.item(i).checked = checkid;
	 }
}

function CheckAllDanhMuc(danhmucId)
{
	var ungdungIds =document.getElementsByName("ungdungId");
	  for(var i = 0; i < ungdungIds.length; i++) 
	  {
		var ungdungId=danhmucId+'_'+ungdungIds.item(i).value;
		try
		{
			var hienthi =document.getElementById("ch_" + ungdungId);
			var them =document.getElementById("chthem_" + ungdungId);
			var chot = document.getElementById("chchot_" + ungdungId);
			var xem = document.getElementById("chxem_" + ungdungId);
			var xoa = document.getElementById("chxoa_" + ungdungId);
			var sua = document.getElementById("chsua_" + ungdungId);
			var huychot = document.getElementById("chhuychot_" + ungdungId);
			var chuyen = document.getElementById("chchuyen_" + ungdungId);
			var fax = document.getElementById("chfax_" + ungdungId);
			var sms = document.getElementById("chsms_" + ungdungId);
			var hienthiall = document.getElementById("chhienthiall_" + ungdungId);
			var xuatexcel = document.getElementById("chxuatexcel_" + ungdungId);
			var hoantat = document.getElementById("chhoantat_" + ungdungId);
			
			if(hienthi.checked ==false||them.checked==false) 
			{
				hienthi.checked=true;
				them.checked=true;
			    chot.checked=true;
			    xem.checked=true;
			    xoa.checked=true;
			    sua.checked=true;
			    huychot.checked=true;
			    chuyen.checked=true;
			    fax.checked=true;
			    sms.checked=true;
			    hienthiall.checked=true;
			    xuatexcel.checked=true;
			    hoantat.checked=true;
			} 
			else 
			{
				hienthi.checked=false;
				them.checked=false;
			    chot.checked=false;
			    xem.checked=false;
			    xoa.checked=false;
			    sua.checked=false;
			    huychot.checked=false;
			    chuyen.checked=false;
			    fax.checked=false;
			    sms.checked=false;
			    hienthiall.checked=false;
			    xuatexcel.checked=false;
			    hoantat.checked=false;
			}
		}
		catch(err)
		{
			
		}
	  }
}
function CheckAllUngDung(ungdungId)
{
	var hienthi =document.getElementById("ch_" + ungdungId);
	var them =document.getElementById("chthem_" + ungdungId);
	var chot = document.getElementById("chchot_" + ungdungId);
	var xem = document.getElementById("chxem_" + ungdungId);
	var xoa = document.getElementById("chxoa_" + ungdungId);
	var sua = document.getElementById("chsua_" + ungdungId);
	var huychot = document.getElementById("chhuychot_" + ungdungId);
	var chuyen = document.getElementById("chchuyen_" + ungdungId);
	var fax = document.getElementById("chfax_" + ungdungId);
	var sms = document.getElementById("chsms_" + ungdungId);
	var hienthiall = document.getElementById("chhienthiall_" + ungdungId);
	var xuatexcel = document.getElementById("chxuatexcel_" + ungdungId);
	var hoantat = document.getElementById("chhoantat_" + ungdungId);
	
	if(hienthi.checked==false)
	{
		hienthi.checked=true;
		them.checked=true;
	    chot.checked=true;
	    xem.checked=true;
	    xoa.checked=true;
	    sua.checked=true;
	    huychot.checked=true;
	    chuyen.checked=true;
	    fax.checked=true;
	    sms.checked=true;
	    hienthiall.checked=true;
	    xuatexcel.checked=true;
	    hoantat.checked=true;
	}
	else
	{
		hienthi.checked=false;
		them.checked=false;
	    chot.checked=false;
	    xem.checked=false;
	    xoa.checked=false;
	    sua.checked=false;
	    huychot.checked=false;
	    chuyen.checked=false;
	    fax.checked=false;
	    sms.checked=false;
	    hienthiall.checked=false;
	    xuatexcel.checked=false;
	    hoantat.checked=false;
	}
}
function CheckAllNdx(ungdungId)
{
	var hienthi =document.getElementById("cbHienThi" + ungdungId);
	var them =document.getElementById("cbThem" + ungdungId);
	var chot = document.getElementById("cbChot" + ungdungId);
	var xem = document.getElementById("cbXem" + ungdungId);
	var xoa = document.getElementById("cbXoa" + ungdungId);
	var sua = document.getElementById("cbSua" + ungdungId);
	var huychot = document.getElementById("cbHuyChot" + ungdungId);
	var chuyen = document.getElementById("cbChuyen" + ungdungId);
	var fax = document.getElementById("cbFAX" + ungdungId);
	var sms = document.getElementById("cbSMS" + ungdungId);
	var hienthiall = document.getElementById("cbHienthiALL" + ungdungId);
	var xuatexcel = document.getElementById("cbXuatExcel" + ungdungId);
	var hoantat = document.getElementById("cbHoanTat" + ungdungId);
	
	if(hienthi.checked==false)
	{
		hienthi.checked=true;
		them.checked=true;
	    chot.checked=true;
	    xem.checked=true;
	    xoa.checked=true;
	    sua.checked=true;
	    huychot.checked=true;
	    chuyen.checked=true;
	    fax.checked=true;
	    sms.checked=true;
	    hienthiall.checked=true;
	    xuatexcel.checked=true;
	    hoantat.checked=true;
	}
	else
	{
		hienthi.checked=false;
		them.checked=false;
	    chot.checked=false;
	    xem.checked=false;
	    xoa.checked=false;
	    sua.checked=false;
	    huychot.checked=false;
	    chuyen.checked=false;
	    fax.checked=false;
	    sms.checked=false;
	    hienthiall.checked=false;
	    xuatexcel.checked=false;
	    hoantat.checked=false;
	}
}
</SCRIPT>
 <script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();

	}); 

	window.onscroll = scroll;
	
	var offY = 0;
	var tableY = 0;
	
	function run() {
		scroll ();
		
		setTimeout(run, 500);
	}
	
	$(document).ready(function() {	
	    run();
		
	    offY = $("#trTieuDE").offset().top;			
	    //tableY = document.getElementById("sku").offsetTop + 4;

	});

	function saveScroll() {
		$("#scrollX").val(parseFloat(window.pageXOffset));
		$("#scrollY").val(parseFloat(window.pageYOffset));
	}
	
	function scroll (offsetX, offsetY) 
	{
		saveScroll();
		
	 	if( parseFloat(window.pageYOffset) >= ( tableY + 10 ) )
	    {
	 		$('#trTieuDE').css("transform", "translateY(" + ( parseFloat(window.pageYOffset) - tableY - 295 ) + "px)");
	    }
	    else
	    {
	    	$('#trTieuDE').css("transform", "");
	    }
	}
	
	$( function() {
		//Created By: Brij Mohan
		//Website: http://techbrij.com
		function groupTable2($rows, startIndex, total)
		{
			if (total === 0)
			{
				return;
			}
			var i , currentIndex = startIndex, count=1, lst=[];
			var tds = $rows.find('td:eq('+ currentIndex +')');
			var ctrl = $(tds[0]);
			lst.push($rows[0]);
			for (i=1;i<=tds.length;i++){
			if (ctrl.text() ==  $(tds[i]).text()){
			count++;
			$(tds[i]).addClass('deleted');
			lst.push($rows[i]);
			}
			else{
				if (count>1){
				ctrl.attr('rowspan',count);
				groupTable2($(lst),startIndex+1,total-1)
				}
				count=1;
				lst = [];
				ctrl=$(tds[i]);
				lst.push($rows[i]);
			}
			}
		}
		var rowCount = $('#sku1 tr').length;

		groupTable2($('#sku1 tr:has(td)'),0,rowCount);
		$('#sku1 .deleted').remove();

		}); 

</script>

<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<style type="text/css">
		
	.fixed {position:fixed; top:0; left:10; z-index:999999; width:99%;}
	.fixed1 {position:fixed; top:0; left:5; z-index:999999; width:99%;}
	
</style>
 	
<script type="text/javascript">
	
	$(window).scroll(function(){
	     if ($(this).scrollTop() > 135) {
	         $('#task_flyout').addClass('fixed');
	         $('#task_flyout1').addClass('fixed1');
	     } else {
	         $('#task_flyout').removeClass('fixed');
	         $('#task_flyout1').removeClass('fixed1');
	     }
	});

</script>
  	
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khoForm" method="post" action="../../DanhmucquyennewSvl" >
		<INPUT name="userId" type="hidden" value='<%=userId%>' size="30"> 
		<INPUT name="id" type="hidden" value='<%=obj.getId()%>' size="30">
		<input name="action" type="hidden" value='0'> 
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">

								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản trị hệ thống > Cập nhật quyền > <%= obj.getId().trim().length() > 0 ? "Cập nhật" : "Tạo mới" %> </TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>

								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="../../DanhmucquyenSvl"><img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A href="javascript: submitform()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>

					<TABLE width=100% cellpadding="4" cellspacing="0" border="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>

									<textarea name="dataerror" style="width: 100%; color: #F00; font-weight: bold" style="width:100%" readonly="readonly" rows="1"> <%=obj.getMsg()%> </textarea>

								</FIELDSET>
							</TD>
						</tr>
						
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin quyền </LEGEND>
									<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
										<TR>
											<TD width="15%" class="plainlabel">Tên quyền <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><INPUT name="ten" size="20" value="<%=obj.getTen()%> " type="text"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel"><INPUT name="diengiai" size="80" value="<%=obj.getDiengiai()%>" type="text"></TD>
										</TR>
									<TR>
										<TD class="plainlabel">Xem theo</TD>
										<TD class="plainlabel">
											<% if(obj.getLoaiMenu().equals("0")){%>
												<input type="radio" name="loaiMenu"	value="0"  checked="checked" onchange="changeView()"/>Tổng công ty  &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="loaiMenu" value="1" onchange="changeView()"/>Công ty MTV
											<%}else{ %>
											<input type="radio" name="loaiMenu"	value="0"   onchange="changeView()"/>Tổng công ty  &nbsp;&nbsp;&nbsp; 
											<input type="radio" name="loaiMenu" value="1" checked="checked" onchange="changeView()"/>Công ty MTV
											<%} %>
									
																					
										</TD>	
									</TR>
									<TR>
										<TD class="plainlabel"><label> <input name="trangthai" type="checkbox" value="1" checked> Hoạt động
										</label></TD>
										<TD class="plainlabel">&nbsp;</TD>
									</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
							 
								 <ul class="tabs"  id="tabnew" >
										<li class="current" >
											<a    href="#tabphanquyenmenu">Quyền menu</a>	
										</li>
										
										 <li >
											<a    href="#tabphanquyenndx">Nội dung nhập xuất</a>
										</li>
									</ul>
							 	<div class="panes">
									
							 <div id="tabphanquyenmenu" class="tab_content">
								
								 
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin quyền </LEGEND>
									<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="98%">
												<TABLE ID="sku" width="100%" border="1" cellspacing="1" cellpadding="4">
													<TR class="tbheader" id="task_flyout" >
														<TH width="12%">Menu</TH>
														<TH width="13%">Ứng dụng</TH>
														
														<TH width="6%">Hiển thị  <input type="checkbox" name="cbAll_HienThi_" value="1" onclick="check_all(this.checked,'cbHienThi')"></TH>
														<TH width="6%">Tạo mới <input type="checkbox" name="cbAll_Them" value="1" onclick="check_all(this.checked,'cbThem')"></TH>
														<TH width="6%">Xóa <input type="checkbox" name="cbAll_Xoa" value="1" onclick="check_all(this.checked,'cbXoa')"></TH>
														<TH width="6%">Sửa <input type="checkbox" name="cbAll_Sua" value="1" onclick="check_all(this.checked,'cbSua')"></TH>
														<TH width="6%">Xem <input type="checkbox" name="cbAll_Xem" value="1" onclick="check_all(this.checked,'cbXem')"></TH>
														<TH width="6%">Chốt <input type="checkbox" name="cbAll_Chot" value="1" onclick="check_all(this.checked,'cbChot')"></TH>
														<TH width="6%">Hủy chốt <input type="checkbox" name="cbAll_HuyChot" value="1" onclick="check_all(this.checked,'cbHuyChot')"></TH>
														<TH width="6%">Chuyển <input type="checkbox" name="cbAll_Chuyen" value="1" onclick="check_all(this.checked,'cbChuyen')"></TH>
														
														<TH width="6%">Xác nhận bởi SMS<input type="checkbox" name="cbAll_SMS" value="1" onclick="check_all(this.checked,'cbSMS')"></TH>
														<TH width="6%">Xác nhận bởi FAX<input type="checkbox" name="cbAll_FAX" value="1" onclick="check_all(this.checked,'cbFAX')"></TH>
														<TH width="6%">Hiển thị tất cả<input type="checkbox" name="cbAll_HienThiALL" value="1" onclick="check_all(this.checked,'cbHienthiALL')"></TH>
														<TH width="6%">Xuất Excel<input type="checkbox" name="cbAll_XuatExcel" value="1" onclick="check_all(this.checked,'cbXuatExcel')"></TH>
													</TR>
													<%
														String danhmuc = "", chucnang = "";
														int num1 = 0, num2 = 0;
															while (ungdungRs.next())
															{
																String str=ungdungRs.getString("DanhMuc_FK")+"_"+ungdungRs.getString("pk_seq");
																if(!danhmuc.equals(ungdungRs.getString("TENDANHMUC")))
																{
																	danhmuc = ungdungRs.getString("TENDANHMUC");
																	num1++;
																	chucnang = "";
																	num2 = 0;
																}
																if(!chucnang.equals(ungdungRs.getString("Ten")))
																{
																	chucnang = ungdungRs.getString("Ten");
																	num2++;
																}
													%>
															<TR id="dong_<%=ungdungRs.getString("DanhMuc_FK")%>">
																<TD width="12%" onclick="CheckAllDanhMuc(<%=ungdungRs.getString("DanhMuc_FK")%>)"><%=num1+". "+ungdungRs.getString("TENDANHMUC")%></TD>
																<TD width="13%" align="left" onclick="CheckAllUngDung('<%=str%>');">
																	<input name="ungdungId" type="hidden"  value ="<%=ungdungRs.getString("pk_seq")%>"><%=num1+"."+num2+". "+ungdungRs.getString("Ten")%>
																</TD>
																
														<TD width="6%" align="center">
														<%if(obj.getCbHienThi().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>
																<input type="checkbox"   id="ch_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"   name="cbHienThi" value="<%=ungdungRs.getString("pk_seq")%>" checked="checked"  >
														<%}else { %>
																<input type="checkbox"   id="ch_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"   name="cbHienThi" value="<%=ungdungRs.getString("pk_seq")%>"   >
														<%} %>
														</TD>
														
											 			<TD width="6%" align="center">
											 				<%if(obj.getCbThem().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>
											 					<input type="checkbox"   id="chthem_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>" checked="checked"   name="cbThem" value="<%=ungdungRs.getString("pk_seq")%>">
											 				<%}else { %>
											 					<input type="checkbox"   id="chthem_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"   name="cbThem" value="<%=ungdungRs.getString("pk_seq")%>">
											 				<%} %>
											 			</TD>
											 			
														<TD width="6%" align="center">
															<%if(obj.getCbXoa().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>
																<input type="checkbox"   id="chxoa_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>" checked="checked"  name="cbXoa" value="<%=ungdungRs.getString("pk_seq")%>">
															<%}else { %>
																<input type="checkbox"   id="chxoa_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbXoa" value="<%=ungdungRs.getString("pk_seq")%>">
															<%} %>
														</TD>
														<TD width="6%" align="center">
															<%if(obj.getCbSua().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>
																<input type="checkbox"   id="chsua_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbSua" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>">
																<%}else { %>
																<input type="checkbox"   id="chsua_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbSua" value="<%=ungdungRs.getString("pk_seq")%>">
																<%} %>
														</TD>
														<TD width="6%" align="center">
															<%if(obj.getCbXem().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>
															<input type="checkbox"   id="chxem_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>" name="cbXem" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%}else { %>
																<input type="checkbox"   id="chxem_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>" name="cbXem" value="<%=ungdungRs.getString("pk_seq")%>">
															<%} %>
														</TD>
														
														<TD width="6%" align="center">
															<%if(obj.getCbChot().indexOf(ungdungRs.getString("pk_seq") )>=0  ){ %>	
																<input type="checkbox"   id="chchot_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbChot" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>">
															<%}else { %>
																	<input type="checkbox"   id="chchot_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbChot" value="<%=ungdungRs.getString("pk_seq")%>">
															<%} %>
															</TD>
														<TD width="6%" align="center">
															<%
															if(obj.getCbHuyChot().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chhuychot_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbHuyChot" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chhuychot_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbHuyChot" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>
														
														<TD width="6%" align="center">
															<%
															if(obj.getCbChuyen().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chchuyen_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbChuyen" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chchuyen_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbChuyen" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>	
														
														<TD width="6%" align="center">
															<%
															if(obj.getCbSMS().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chsms_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbSMS" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chsms_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbSMS" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>
															
														<TD width="6%" align="center">
															<%
															if(obj.getCbFAX().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chfax_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbFAX" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chfax_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbFAX" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>
															
														<TD width="6%" align="center">
															<%
															if(obj.getCbHienThiALL().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chhienthiall_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbHienthiALL" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chhienthiall_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbHienthiALL" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>
															
														<TD width="6%" align="center">
															<%
															if(obj.getCbXuatExcel().indexOf(ungdungRs.getString("pk_seq") )  >=0  ){ %>
																<input type="checkbox"   id="chxuatexcel_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbXuatExcel" checked="checked" value="<%=ungdungRs.getString("pk_seq")%>"  >
															<%}else { %>
																<input type="checkbox"   id="chxuatexcel_<%=ungdungRs.getString("DanhMuc_FK")%>_<%=ungdungRs.getString("pk_seq")%>"  name="cbXuatExcel" value="<%=ungdungRs.getString("pk_seq")%>" >
															<%} %>
															</TD>	
														
														<%
															}
														%>
													</TR>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
								</FIELDSET>
								</div>
								
								<div id="tabphanquyenndx" class="tab_content">
												<TABLE ID="sku" width="100%" border="1" cellspacing="1" cellpadding="4">
													<TR class="tbheader" id="task_flyout1" >
														 
														<TH width="36%">Nội dung nhập xuất</TH>
														
														<TH width="8%">Hiển thị <input type="checkbox" name="cbAll_HienThi_" value="1" onclick="check_all(this.checked,'cbHienThi')"></TH>
														<TH width="8%">Tạo mới <input type="checkbox" name="cbAll_Them" value="1" onclick="check_all(this.checked,'cbThem')"></TH>
														<TH width="8%">Xóa <input type="checkbox" name="cbAll_Xoa" value="1" onclick="check_all(this.checked,'cbXoa')"></TH>
														<TH width="8%">Sửa <input type="checkbox" name="cbAll_Sua" value="1" onclick="check_all(this.checked,'cbSua')"></TH>
														<TH width="8%">Xem <input type="checkbox" name="cbAll_Xem" value="1" onclick="check_all(this.checked,'cbXem')"></TH>
														<TH width="8%">Chốt <input type="checkbox" name="cbAll_Chot" value="1" onclick="check_all(this.checked,'cbChot')"></TH>
														<TH width="8%">Hủy chốt <input type="checkbox" name="cbAll_HuyChot" value="1" onclick="check_all(this.checked,'cbHuyChot')"></TH>
														<TH width="8%">Chuyển <input type="checkbox" name="cbAll_Chuyen" value="1" onclick="check_all(this.checked,'cbChuyen')"></TH>																		
													</TR>
													<%
													
														ResultSet noidungrs=obj.getnoidungRs();
													
															while (noidungrs.next())
															{
																String str=noidungrs.getString("DanhMuc_FK");
													%>
															<TR>
																<TD width="36%" align="left" onclick="CheckAllNdx('<%=str%>');">
																	<input name="noidungxuatid" type="hidden"  value ="<%=noidungrs.getString("MA")%>"> 
																	<%=noidungrs.getString("TENDANHMUC")%> 
																</TD>
																
																<TD  width="8%" align="center">
																	<%if(obj.getCbHienThi().indexOf(noidungrs.getString("MA") )>=0  ){ %>
																			<input type="checkbox" id="cbHienThi<%=noidungrs.getString("DanhMuc_FK")%>" name="cbHienThi" value="<%=noidungrs.getString("MA")%>" checked="checked"  >
																	<%}else { %>
																			<input type="checkbox" id="cbHienThi<%=noidungrs.getString("DanhMuc_FK")%>" name="cbHienThi" value="<%=noidungrs.getString("MA")%>"   >
																	<%} %>
																</TD>
														
											 			<TD width="8%" align="center">
											 				<%if(obj.getCbThem().indexOf(noidungrs.getString("MA") )>=0  ){ %>
											 					<input type="checkbox" id="cbThem<%=noidungrs.getString("DanhMuc_FK")%>" name="cbThem" checked="checked" value="<%=noidungrs.getString("MA")%>">
											 				<%}else { %>
											 					<input type="checkbox" id="cbThem<%=noidungrs.getString("DanhMuc_FK")%>" name="cbThem" value="<%=noidungrs.getString("MA")%>">
											 				<%} %>
											 			</TD>
											 			 <TD width="8%" align="center">
															<%if(obj.getCbXoa().indexOf(noidungrs.getString("MA") )>=0  ){ %>
																<input type="checkbox" id="cbXoa<%=noidungrs.getString("DanhMuc_FK")%>" name="cbXoa" checked="checked" value="<%=noidungrs.getString("MA")%>">
															<%}else { %>
																<input type="checkbox" id="cbXoa<%=noidungrs.getString("DanhMuc_FK")%>" name="cbXoa" value="<%=noidungrs.getString("MA")%>">
															<%} %>
														</TD>
														<TD width="8%" align="center">
															<%if(obj.getCbSua().indexOf(noidungrs.getString("MA") )>=0  ){ %>
																<input type="checkbox" id="cbSua<%=noidungrs.getString("DanhMuc_FK")%>" name="cbSua" checked="checked" value="<%=noidungrs.getString("MA")%>">
																<%}else { %>
																<input type="checkbox" id="cbSua<%=noidungrs.getString("DanhMuc_FK")%>" name="cbSua" value="<%=noidungrs.getString("MA")%>">
																<%} %>
														</TD>
														<TD width="8%" align="center">
															<%if(obj.getCbXem().indexOf(noidungrs.getString("MA") )>=0  ){ %>
															<input type="checkbox" id="cbXem<%=noidungrs.getString("DanhMuc_FK")%>" name="cbXem" checked="checked" value="<%=noidungrs.getString("MA")%>" >
															<%}else { %>
																<input type="checkbox" id="cbXem<%=noidungrs.getString("DanhMuc_FK")%>" name="cbXem" value="<%=noidungrs.getString("MA")%>">
															<%} %>
														</TD>
														
														<TD width="8%" align="center">
															<%if(obj.getCbChot().indexOf(noidungrs.getString("MA") )>=0  ){ %>	
																<input type="checkbox" id="cbChot<%=noidungrs.getString("DanhMuc_FK")%>" name="cbChot" checked="checked" value="<%=noidungrs.getString("MA")%>">
															<%}else { %>
																	<input type="checkbox" id="cbChot<%=noidungrs.getString("DanhMuc_FK")%>" name="cbChot" value="<%=noidungrs.getString("MA")%>">
															<%} %>
															</TD>
														<TD width="8%" align="center">
															<%
															if(obj.getCbHuyChot().indexOf(noidungrs.getString("MA") )  >=0  ){ %>
																<input type="checkbox" id="cbHuyChot<%=noidungrs.getString("DanhMuc_FK")%>" name="cbHuyChot" checked="checked" value="<%=noidungrs.getString("MA")%>"  >
															<%}else { %>
																<input type="checkbox" id="cbHuyChot<%=noidungrs.getString("DanhMuc_FK")%>" name="cbHuyChot" value="<%=noidungrs.getString("MA")%>" >
															<%} %>
															</TD>
														
														<TD width="8%" align="center">
															<%
															if(obj.getCbChuyen().indexOf(noidungrs.getString("MA") )  >=0  ){ %>
																<input type="checkbox" id="cbChuyen<%=noidungrs.getString("DanhMuc_FK")%>" name="cbChuyen" checked="checked" value="<%=noidungrs.getString("MA")%>"  >
															<%}else { %>
																<input type="checkbox" id="cbChuyen<%=noidungrs.getString("DanhMuc_FK")%>" name="cbChuyen" value="<%=noidungrs.getString("MA")%>" >
															<%} %>
															</TD>	
														<%
															}
														%>
													</TR>
												</TABLE>
								</div>
								
								</div>
					</TABLE>
				</TD>
			</TR>

		</TABLE>
	</form>
</BODY>
</HTML>
<%
	if(ungdungRs != null) ungdungRs.close();
	if(noidungrs != null) noidungrs.close();
	}
%>

<% session.setAttribute("obj", null)	;	 %>

