<%@page import="geso.traphaco.erp.beans.taisancodinh.IPhanBo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IErp_TaiSanCoDinh"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.imp.Erp_TaiSanCoDinh"%>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.util.List" %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	Utility util = (Utility) session.getAttribute("util"); 
	String sum = (String) session.getAttribute("sum");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

		  IErp_TaiSanCoDinh obj = (Erp_TaiSanCoDinh) session.getAttribute("obj");
		  /* ResultSet ntsList = obj.getRsNts(); */
		  /* ResultSet cdList = obj.getRscd(); */
		  ResultSet khRs = obj.Laykhauhao();  
		  ResultSet lnsRs = obj.getRsLapNganSach();
		  /* ResultSet TtcpRs = obj.getRsTtcp(); */
		  ResultSet dcNguyenGiaRs  = obj.getDieuChinhNguyenGiaRs();
		  ResultSet ltsRs = obj.getRsLoaitaisan();
		  ResultSet dvthRs= obj.getDvthList();
		  
		  List<IKhauHaoDuKien> khauhaodukienList = new ArrayList<IKhauHaoDuKien>();
		  List<IPhanBo> phanBoList  = new ArrayList<IPhanBo>();
		  ResultSet kmcpRs =  obj.getKMCPList();		  
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

<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
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
	var mats =document.getElementById("ma");
	var error=document.getElementById("error");
	var sl=document.getElementById("soluong");
	var dg=document.getElementById("dongia");
	
	var tents = document.getElementById("ten");
	var nts = document.getElementById("nts");
	var ct =  document.getElementById("ct");
	var cd =  document.getElementById("cd");
	var ttcp =  document.getElementById("ttcp");
	var dvt = document.getElementById("dvt");
	var stkh =document.getElementById("stkh");
	var tbdkh = document.getElementById("tbdkh");
	var ppkh =  document.getElementById("ppkh");
	
	if(mats.value == "")                        
	{
		mats.focus();
		error.value= "Vui lòng nhập mã tài sản.";
		return;              
	}
	if(tents.value  == "")
	{
		tents.focus();
		error.value = "Vui lòng nhập tên tài sản.";
		return;
	}

	if(nts.value  == "")
	{
		nts.focus();
		error.value = "Vui lòng chọn loại tài sản.";
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
	
	if(ppkh.value  == "")
	{
		ppkh.focus();
		error.value  = "Vui lòng nhập phương pháp khấu hao.";
		return;
	}
	
	
	document.forms['tsForm'].khauhao.value='khauhao';
	document.forms["tsForm"].submit();
	
}

function submitform()
{
	document.forms['tsForm'].action.value='submit';
	document.forms["tsForm"].submit();
}

function saveform(){
	var mats =document.getElementById("ma");

	var diengiai = document.getElementById("diengiai");
	var stkh =document.getElementById("stkh");
	var tbdkh = document.getElementById("tbdkh");
	
	if(mats.value == "")                        
	{
		mats.focus();
		error.value= "Vui lòng nhập Mã tài sản.";
		return;              
	}
	
	if(diengiai.value  == "")
	{
		diengiai.focus();
		error.value = "Vui lòng nhập Diễn giải tài sản.";
		return;
	}

	if(ntsId.value  == "")
	{
		ntsId.focus();
		error.value = "Vui lòng chọn nhóm tài sản.";
		return;
	}
		

	document.forms['tsForm'].action.value='save';
	document.forms["tsForm"].submit();
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
	document.forms['tsForm'].action.value='Excel';
	document.forms["tsForm"].submit();
}
function goBack() {
    window.history.back();
}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tsForm" method="post" action="../../Erp_TaiSanCoDinhUpdateSvl">
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
										<TD align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Tài sản > Hiển thị</TD>
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
										<TD  width="30" align="left" ><A href="javascript:goBack();"> <img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
										</A></TD>
										
										<TD width="2" align="left"></TD>
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
									<LEGEND class="legendtitle">Thông tin tài sản </LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td class="plainlabel">
												Ngân sách
											</td>
											<TD class="plainlabel" >
												<SELECT name="lnsId" id="lnsId"   class="select2" style="width: 200px" disabled>
													<option value=""></option>
													<%
														try {
															while (lnsRs.next()) {
																if (obj.getLapngansachId().equals(lnsRs.getString("pk_seq"))) {
													%>
													<option value='<%=lnsRs.getString("pk_seq")%>' selected><%=lnsRs.getString("DIENGIAI")%></option>
													<%
														} else {
													%>
													<option value='<%=lnsRs.getString("pk_seq")%>'><%=lnsRs.getString("DIENGIAI")%></option>
													<%
														}
															}
															lnsRs.close();
														} catch (java.sql.SQLException e) {
														}
													%>
												</SELECT>
											</TD>
											<%-- <TD class="plainlabel">Loại tài sản</TD>
											<TD  class="plainlabel">
												<INPUT type="text" name="ltsId" id="ltsId" 	value="<%=obj.getLoaitaisan() %>"  readonly="readonly">											
											</TD> --%>
                                            <TD class="plainlabel">Loại tài sản<FONT class="erroralert"> *</FONT></TD>
                                                <TD class="plainlabel">
                                                
                                                                 
                                                                 <SELECT name="ltsId" id="ltsId" class="select2" style="width: 200px"  disabled >
                                                    <option value=""></option>
                                                    <%
                                                      try {
                                                        while (ltsRs.next()) {
                                                          if (obj.getLtsId().equals(ltsRs.getString("pk_seq"))) {
                                                    %>
                                                    <option value='<%=ltsRs.getString("pk_seq")%>' selected><%=ltsRs.getString("diengiai")%></option>
                                                    <%
                                                      } else {
                                                    %>
                                                    <option value='<%=ltsRs.getString("pk_seq")%>'><%=ltsRs.getString("diengiai")%></option>
                                                    <%
                                                      }
                                                        }
                                                        ltsRs.close();
                                                      } catch (java.sql.SQLException e) {
                                                      }
                                                    %>
                                                  </SELECT>
                                                </TD>
										</tr>
										
										<TR>
                                            <TD width="15%" class="plainlabel">Mã <FONT class="erroralert">*</FONT></TD>
                                             <TD class="plainlabel" colspan="3"><INPUT type="text" name="ma" id="ma" size="40" type="text" value="<%=obj.getMa()%>" readonly="readonly"></TD>
                        
                                    </TR>
                                    
                                    <TR>
											<%-- <TD class="plainlabel">Nhóm tài sản<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel">
												<INPUT TYPE = "HIDDEN"  ID = "ntsId" NAME = "ntsId"  class="select2" style="width: 200px" VALUE = "<%= obj.getNtsId() %>" >
												<SELECT name="nId" id="nId" readonly=readonly>
													<option value=""></option>
													<%
														try {
															while (ntsList.next()) {
																if (obj.getNtsId().equals(ntsList.getString("pk_seq"))) {
													%>
													<option value='<%=ntsList.getString("pk_seq")%>' selected><%=ntsList.getString("diengiai")%></option>
													<%
														} else {
													%>
													<option value='<%=ntsList.getString("pk_seq")%>'><%=ntsList.getString("diengiai")%></option>
													<%
														}
															}
															ntsList.close();
														} catch (java.sql.SQLException e) {
														}
													%>
												</SELECT>
											</TD> --%>
                                            
                      
											<TD width="15%" class="plainlabel">Diễn giải <font class="erroralert">*</font></TD>
											
											<TD class="plainlabel"><input type="text" name="diengiai" id="diengiai" value="<%=obj.getDiengiai() %>" readonly></TD>
            											<TD class="plainlabel">Đơn vị tính</TD>
                                  <TD  class="plainlabel">
                                    <INPUT type="text" name="dvt" id="dvt"  value="<%=obj.getDvt() %>"   readonly="readonly">
                                  </TD> 
                                  
                                  
                                    <tr>
                       			  <td class="plainlabel">Đơn vị thực hiện <font class="erroralert">*</font> </td>
                                          <TD class="plainlabel" colspan="3"><select name="dvthId" id="dvthId" style="width: 200px" >
                                              <option value=""></option>
                                              <%
                                                if (dvthRs != null){
                                                  while (dvthRs.next()) {
                                                    if (obj.getDvthId().equals(dvthRs.getString("PK_SEQ"))) 
                                                    {
                                              %>
                                                <option value="<%=dvthRs.getString("PK_SEQ")%>" selected="selected"><%=dvthRs.getString("Ten")%></option>
                                              <%
                                                    } else {
                                              %>
                                                <option value="<%=dvthRs.getString("PK_SEQ")%>"><%=dvthRs.getString("Ten")%></option>
                                              <%
                                                    }
                                                  }
                                                  dvthRs.close(); 
                                                }
                                              %>
                                          </select></td>
                      </tr> 
            											

										</TR> 
                                        
									
										
																				
										<TR>
											<TD width="15%" class="plainlabel">Số lượng </TD>
											<TD class="plainlabel">
												<INPUT type="text" name="soluong" id="soluong" 	value="<%=obj.getSoLuong()%>" onkeypress="return keypress(event);"  readonly="readonly">
											</TD>
												
											<TD width="15%" class="plainlabel">Thành tiền </TD>
											<TD  class="plainlabel">
												<INPUT type="text" name="thanhtien" id="thanhtien" value="<%= formatter.format(Double.parseDouble(obj.getThanhTien()))%>" readonly="readonly"></TD>
											
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Đơn giá </TD>
											<TD  class="plainlabel"><input type="text" name="dongia" id="dongia"
												value="<%=formatter.format(Double.parseDouble(obj.getDonGia()))%>" onkeypress="return keypress(event);"  readonly="readonly"></TD>

											<TD width="15%" class="plainlabel">Ngày bắt đầu khấu hao</TD>
											
											<TD class="plainlabel">
											<input class="days" type="text" name="tbdkh" id="tbdkh" value="<%=obj.getThangbatdauKH()%>" maxlength="10" readonly></TD>
											
										</TR>

										<TR>
											<TD width="15%" class="plainlabel">Số tháng khấu hao</TD>
									
											<TD class="plainlabel"><input type="text" name="stkh" id="stkh" value="<%=obj.getSothangKH()%>" onkeypress="return keypress(event);" maxlength="4" readonly></TD>
								
											<TD width="15%" class="plainlabel">Ngày ghi tăng</TD>
											
											<TD class="plainlabel">
											<input class="days" type="text" name="ngayghitang" id="ngayghitang" value="<%=obj.getNgayghitang()%>" maxlength="10" readonly></TD>
											
										</TR>
										
										<tr>
										
											<%-- <td class="plainlabel">Trung tâm chi phí <font class="erroralert">*</font> </td>
												<TD class="plainlabel"><select name="ttcpId" id="ttcpId" style="width: 200px" >
														<option value=""></option>
														<%
															if (TtcpRs != null)
																while (TtcpRs.next()) {
																	if (obj.getTtcp().equals(
																			TtcpRs.getString("PK_SEQ"))) 
																	{
														%>
															<option value="<%=TtcpRs.getString("PK_SEQ")%>" selected="selected"><%=TtcpRs.getString("Ten")%></option>
														<%
															} else {
														%>
															<option value="<%=TtcpRs.getString("PK_SEQ")%>"><%=TtcpRs.getString("Ten")%></option>
														<%
															}
														}
														%>
												</select></td> --%>
												
									<TD width="15%" class="plainlabel">Điều chỉnh nguyên giá</TD>
									<td class="plainlabel" colspan = "3">
										 <A href="" id="dcng" rel="subcontentNG">&nbsp; 
										     <img alt="Điều chỉnh nguyên giá" src="../images/vitriluu.png">
									 </A> &nbsp;
										 <DIV id="subcontentNG" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 450px; max-height:150px; overflow-y:scroll; padding: 4px;">
                    						<table width="90%" align="center">
						                        <tr>
						                            <th width="100px">Chứng từ</th>
						                            <th width="150px">Số chứng từ</th>
						                            <th width="150px">Ngày chứng từ</th>
						                            <th width="150px">Giá trị điều chỉnh</th>	
						                            <th width="150px">Số tháng điều chỉnh</th>											                       
						                        </tr>
                        						
					                          <% if(dcNguyenGiaRs!=null){%>
					                          <% while  (dcNguyenGiaRs.next()) {%>
								                        			<tr>
								                        				<td>
								                        					<input type="text" style="width: 100%" readonly="readonly" name="loaichungtu_dieuchinh" value="<%=dcNguyenGiaRs.getString("LOAICHUNGTU")  %>" />
								                        				</td>
											                            <td>											                            	
											                            	<input type="text" style="width: 100%" readonly="readonly" name="sochungtu_dieuchinh" value="<%= dcNguyenGiaRs.getString("SOCHUNGTU") %>" />
											                            </td>
											                            <td>
											                            	<input type="text" style="width: 100%" readonly="readonly" name="ngaydieuchinh_nguyengia" value="<%=  dcNguyenGiaRs.getString("NGAYDIEUCHINH") %>" /></td>
											                            <td>
											                            	<input type="text"  style="width: 100%" name="giatri" value="<%= formatter.format(dcNguyenGiaRs.getDouble("GIATRI")) %>" />
											                            </td>
											                             <td>
											                            	<input type="text"  style="width: 100%" name="sothang" value="<%= dcNguyenGiaRs.getString("SOTHANG") %>" />
											                            </td>
											                        </tr>
								                      
							        			<% } }%>
							                       
		
                    							</table>
							                     <div align="right">
							                     	<label style="color: red" ></label>
							                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                     	<a href="javascript:dropdowncontent.hidediv('subcontentNG')">Hoàn tất</a>
							                     </div>
                							</DIV>
                							</td>
															
										</tr>

										
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						 <TR>
              <td>
                              
                <ul class="tabs">
                  <li><a href="#" class= "legendtitle"> Khoản mục chi phí</a></li>                              
               <!--    <li><a href="#" class= "legendtitle"> Công dụng</a></li>                                
                  <li><a href="#" class= "legendtitle">Phân bổ khấu hao</a></li>-->
                  <li><a href="#" class= "legendtitle">Khấu hao dự kiến</a></li> 
                                
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
                
                

                    <%-- <div>
                      <TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
                        <TR><TD class= "legendtitle" >Chọn trung tâm chi phí để phân bổ khấu hao</TD></TR>
                      </TABLE>
                    
                      <TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
                        <TR class="tbheader">
                          <TH width="10%">Chọn</TH>
                          <TH width="25%">Mã</TH>
                          <TH width="45%">Diễn giải</TH>                          
                          <TH width="20%">Phần trăm (%)</TH>
                        </TR>

                    <% 
                    i = 0;
                    ResultSet ttcpRs = obj.getChonTTCP();
                    while(ttcpRs.next()){ 
                        if (i % 2 != 0) {   %>
                                               
                                              <TR class= <%=lightrow%> >
                                                
                                   <%     } else {%>
                                                
                                            <TR class= <%= darkrow%> >
                                                 
                                 <%     }%>
                        <TD align = "center">
                          <INPUT TYPE = "checkbox" name="Ids" value="<%= ttcpRs.getString("TTCPID") %>"  checked="checked" disabled=disabled>
                          <INPUT TYPE = "HIDDEN" name="ttcpIds" value="<%= ttcpRs.getString("TTCPID") %>" >
                        </TD>

                                        <TD><%= ttcpRs.getString("MA") %></TD>  
                        <TD><%= ttcpRs.getString("DIENGIAI") %></TD>                                                  

                        <TD align = "center">
                          <INPUT TYPE = "text" name="<%= "pt2" + ttcpRs.getString("TTCPID") %>" value="<%= ttcpRs.getString("PHANTRAM") %>"  style="width:100" onkeypress="return isNumberKey(event)">
                        </TD>
                                
                                            
                       </TR>
                                                  
                  <%    i++;
                    } 
                    ttcpRs.close();
                    %> 
                    
                    
                      </TABLE>  
                  </div> --%>
                              
                   			<div align = "left">


			                  <TABLE  id="Master" class="tabledetail" width="100%" border="0" cellpadding="4" cellspacing="1">                  
				                  <TR class="tbheader" >
					                    <TH class="tbheader">Tháng thứ</TH>
					                    <TH class="tbheader">Khấu hao dự kiến</TH>
					                    <TH class="tbheader">Khấu hao lũy kế dự kiến</TH>
					                    <TH class="tbheader">Giá trị còn lại dự kiến</TH>
					                    <TH class="tbheader">Khấu hao thực tế</TH>
					                    <TH class="tbheader">Khấu hao lũy kế thực tế</TH>
					                    <TH class="tbheader">Giá trị còn lại thực tế</TH>
				                  </TR>
				<%                   khauhaodukienList= obj.getKhauhaodukienList();
				                    if(khauhaodukienList != null){
				                    try{
				                      for(int i=0; i<khauhaodukienList.size(); i++){ 
				                      IKhauHaoDuKien khauhao = khauhaodukienList.get(i);
				                      if (i % 2 != 0) {%>           
			                      <TR class= <%=lightrow%> >
			                    <%} else {%>
			                      <TR class= <%= darkrow%> >
			                    <%} %>
			                        <TD align="center"><%= khauhao.getThang() %> </TD>  
			                        <TD align="center"><%= khauhao.getKhauhaodukien() %> </TD>
			                        <TD align="center"><%= khauhao.getKhauhaoluykedukien() %> </TD>
			                        <TD align="center"><%= khauhao.getGiatriconlaidukien() %> </TD>
			                        <TD align="center"><%= khauhao.getKhauhaothucte() %>    </TD>
			                        <TD align="center"><%= khauhao.getKhauhaoluykethucte() %>   </TD>
			                        <TD align="center"><%= khauhao.getGiatriconlaithucte() %>   </TD>
			                      </TR> 
			                        
			<%                      }
			                    }catch(Exception e){}
			                  }
			%>
			                </TABLE>              
            		  </div> 
              
              </td>
            </TR>
					</TABLE>	
					
				</TD>
			</TR>
		</TABLE>
	</form>
<script type="text/javascript"> 
	   
		dropdowncontent.init("dcng", "right-top", 250, "click");
</script>
<script type="text/javascript"> 
	   
		dropdowncontent.init("dieuChuyen", "left-top", 250, "click");
</script>
</BODY>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</HTML>


<% 
	try{
		/* if(ntsList != null) ntsList.close();
		if(cdList != null) cdList.close(); */
		if(khRs != null) khRs.close();	
		if(lnsRs != null) lnsRs.close();
		/* if(TtcpRs != null) TtcpRs.close(); */
		if(dcNguyenGiaRs!=null)dcNguyenGiaRs.close();
		if(khauhaodukienList!= null)khauhaodukienList.clear();
		obj.DBClose();
		session.setAttribute("obj", null); 
	}catch(Exception ex)
	{
		ex.printStackTrace();
	}
} %>