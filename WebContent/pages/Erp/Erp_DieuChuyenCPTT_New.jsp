<%@page import="geso.traphaco.erp.beans.taisancodinh.IPhanBo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.dieuchuyencptt.IErp_DieuChuyenCPTT"%>
<%@ page import="geso.traphaco.erp.beans.dieuchuyencptt.imp.Erp_DieuChuyenCPTT"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.util.List" %>


<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	Utility util = (Utility) session.getAttribute("util"); 
	String sum = (String) session.getAttribute("sum");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

		IErp_DieuChuyenCPTT dcBean = (Erp_DieuChuyenCPTT) session.getAttribute("dcBean");
	List<IPhanBo> phanBoList  = new ArrayList<IPhanBo>();
	if (dcBean == null)
		System.out.println("Null--------");

	ResultSet ntsList = dcBean.getRsNts();
	ResultSet cdList = dcBean.getRscd();	
	ResultSet pbRs = dcBean.getPbRs();
	ResultSet ttcpRs = dcBean.getTtcpRs();
	ResultSet tsRs=dcBean.getTscdRs();
	
	System.out.print("");
	
	
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
		error.value= "Vui lòng nhập mã chi phí trả trước.";
		return;              
	}
	if(tents.value  == "")
	{
		tents.focus();
		error.value = "Vui lòng nhập tên chi phí trả trước.";
		return;
	}

	if(nts.value  == "")
	{
		nts.focus();
		error.value = "Vui lòng chọn loại chi phí trả trước.";
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
	
	document.forms['tsForm'].khauhao.value='khauhao';
	document.forms["tsForm"].submit();
	
}

function submitform()
{
	document.forms['tsForm'].action.value='submit';
	document.forms["tsForm"].submit();
}

function saveform(){

	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
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
function TinhChenhLech()
{
	var sothangkh =document.getElementById("sothangkhauhao");
	var sothangkhmoi =document.getElementById("sothangkhauhaomoi");
	var dieuchinhsothangkhauhao =document.getElementById("dieuchinhsothangkh");
	
	var nguyengia=document.getElementById("nguyengia");
	var nguyengiamoi=document.getElementById("nguyengiamoi");
	var dieuchinhnguyengia=document.getElementById("dieuchinhnguyengia");

	if ((sothangkh.value!="")&&(sothangkhmoi!="")&&(sothangkh.value!=null)&&(sothangkhmoi!=null))
	{
		var dcthangkh= parseFloat(sothangkhmoi.value)-parseFloat(sothangkh.value);
		dieuchinhsothangkhauhao.value=DinhDangDonGia(dcthangkh);
	}
	if ((nguyengia.value!="")&&(nguyengiamoi.value!=""))
	{
		var ng=nguyengia.value.replace(/,/g,"");
		var ngm=nguyengiamoi.value.replace(/,/g,"");
		var dc=parseFloat(ngm)-parseFloat(ng);
		dieuchinhnguyengia.value=DinhDangDonGia(dc);
		nguyengiamoi.value=DinhDangDonGia(ngm);
	}
	document.forms['tsForm'].action.value='change';
	document.forms["tsForm"].submit();
			
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
function changeTscd()
{
	document.forms['tsForm'].action.value='change';
	document.forms["tsForm"].submit();
}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tsForm" method="post" action="../../Erp_DieuChuyenCPTTUpdateSvl">
<input type="hidden" name="Id" value='<%= dcBean.getId() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="save">
<INPUT name="khauhao" type="hidden" value="1">

<INPUT name="id" type="hidden" value="<%=dcBean.getId()%>">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">Quản lý chi phí trả trước > Điều chuyển chi phí trả trước > Tạo mới</TD>
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
										<TD  width="30" align="left" ><A href="/TraphacoHYERP/Erp_DieuChuyenCPTTSvl"> <img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
										</A></TD>
										
										<TD width="2" align="left"></TD>
										<TD  width="30" align="left" >
										<div id="btnSave">
										<A href="javascript: saveform();">
										
										<IMG src="../images/Save30.png"
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
									<textarea name="dataerror" id="error" style="width: 100%" readonly="readonly" rows="1"><%=dcBean.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin điều chuyển </LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										
											<TR class="plainlabel">
											  <TD class="plainlabel">Ngày điều chuyển </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngaychungtu" name="ngaychungtu" value="<%= dcBean.getNgaychungtu() %>" maxlength="10"  /> 
											  </TD>
											   <TD class="plainlabel">Số chứng từ </TD>
											  <TD class="plainlabel" colspan="3">                               
                                                 <input type="text" size="10"  id="sochungtu" name="sochungtu" value="<%= dcBean.getSochungtu() %>" maxlength="10"  /> 
                            				  </TD>
                                          	</TR>
                                          	
                                          	<TR class="plainlabel">
											  <TD class="plainlabel">Chi phí trả trước</TD>
											 	<TD class="plainlabel" >
											          <select name="taisancodinh" id= "taisancodinh"  onchange="javascript: changeTscd();" class="select2" style="width: 200px">
														 <option value=""></option>
														  <% if(tsRs != null){
															  try{ while(tsRs.next()){ 				
												    			if(tsRs.getString("pk_seq").trim().equals(dcBean.getTscdId())){ %>
												      				<option value='<%=tsRs.getString("pk_seq")%>' selected><%=tsRs.getString("ten") %></option>
												      			<%}else{ %>
												     				<option value='<%=tsRs.getString("pk_seq")%>'><%=tsRs.getString("ten") %></option>
												     			<%}
												    			}tsRs.close();}catch(java.sql.SQLException e){}} %>	
														</select> 
										          </td>
										           <TD class="plainlabel">Diễn giải chứng từ </TD>
											  <TD class="plainlabel" >                               
                                                 <input type="text"  id="dienGiaiCT" name="dienGiaiCT" value="<%= dcBean.getDienGiaiCT() %>"  /> 
                            				  </TD>
										           
                                          	</TR>  	
                                           <TR>
                                          	  <TD class="plainlabel">Phòng ban cũ </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text"  id="ttcpCuTen" name="ttcpCuTen" value="<%= dcBean.getTtcpCu() %>"  /> 
                                                 <input type="hidden" id="ttcpCu" name="ttcpCu" value="<%= dcBean.getTtcpCuId() %>"  /> 
                            				  </TD>
                                          	<TD class="plainlabel">Phòng ban mới</TD>
										          <td class="plainlabel">
											          <select name="ttcpId" id= "ttcpId"  class="select2" style="width: 200px" onchange="javascript: changeTscd();">
														 <option value=""></option>
														  <% if(ttcpRs != null){
															  try{ while(ttcpRs.next()){ 				
												    			if(ttcpRs.getString("pk_seq").trim().equals(dcBean.getTtcpId())){ %>
												      				<option value='<%=ttcpRs.getString("pk_seq")%>' selected><%=ttcpRs.getString("ten") %></option>
												      			<%}else{ %>
												     				<option value='<%=ttcpRs.getString("pk_seq")%>'><%=ttcpRs.getString("ten") %></option>
												     			<%}
												    			}ttcpRs.close();}catch(java.sql.SQLException e){}} %>	
														</select> 
										          </td>
                                          	</TR>
																				
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						<TR>
							<td>
															
								<ul class="tabs">
																							
									<li><a href="#" class= "legendtitle"> Khoản mục chi phí </a></li>																
<!-- 									<li><a href="#" class= "legendtitle">Phân bổ khấu hao</a></li> -->
																
								</ul>
									
								<div class="panes">
										<div align = "left">
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Chọn khoản mục chi phí</TD></TR>
											</TABLE>
										
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
<!-- 													<TH width="10%">Chọn</TH> -->
													<TH width="80%">Khoản mục chi phí</TH>
													<TH width="20%">Phần trăm (%)</TH>
												</TR>

										<% 
									int i = 0;
		                            String lightrow = "tblightrow";
		                            String darkrow = "tbdarkrow";
										
		                            ResultSet cdtsRs =  dcBean.getRsCdts();
		                            phanBoList=dcBean.getPhanBoList();
		                        	 for ( i=0; i<phanBoList.size();i++ ) 
                                  	 {
                                  	    IPhanBo phanBo= phanBoList.get(i);
                                       %>
                  						<TR>
                                          <TD  align="left" width="100%">
                        		
                        					<select style="width: 600px" name="khoanMucId" onchange="submitform();" >
											<option value  = ""></option>
                        		<%			
                        					if(cdtsRs!=null){
                        						cdtsRs.beforeFirst();
	                        					
				                        		while (cdtsRs.next())
				                        		{  
				                        			if(phanBo.getKhoanMucId().equals(cdtsRs.getString("NCPID") )){
				                        		%>
													<option  selected="selected" value="<%= cdtsRs.getString("NCPID")%>"> <%= cdtsRs.getString("DIENGIAI") %> </option>
												<%}else{ %>
													<option value="<%= cdtsRs.getString("NCPID")%>"> <%= cdtsRs.getString("DIENGIAI") %> </option>
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
                        					if(cdtsRs!=null){
                        						cdtsRs.beforeFirst();
	                        					while (cdtsRs.next())
				                        		{ 
	                        					%>
				                         
												<option value="<%= cdtsRs.getString("NCPID")%>"> <%= cdtsRs.getString("DIENGIAI") %>  </option>
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
								    ResultSet kmRs = dcBean.getRskhoanmuc();
									if(kmRs != null){
										while(kmRs.next()){ 
										  	if (i % 2 != 0) {		%>
												                       
                                              <TR class= <%=lightrow%> >
	                                              
                                   <%	  	} else {%>
	                                              
	                                          <TR class= <%= darkrow%> >
	                                               
	                               <%	  	}%>
												<TD align = "center">
												<% if(dcBean.getKmId().indexOf(kmRs.getString("PK_SEQ"))>=0) { %>
													<INPUT TYPE = "checkbox" name="kmcpIds" value="<%= kmRs.getString("PK_SEQ") %>" checked="checked">
												<% } else { %>
													<INPUT TYPE = "checkbox" name="kmcpIds" value="<%= kmRs.getString("PK_SEQ") %>">
												<% } %>
													<INPUT TYPE = "HIDDEN" name="Ids" value="<%= kmRs.getString("PK_SEQ") %>" >
												</TD>

	                               				<TD><%= kmRs.getString("TEN") %></TD>	
												<TD><%= kmRs.getString("DIENGIAI") %></TD>																									

												<TD align = "center">
													<INPUT TYPE = "text" name="<%= "pt2" + kmRs.getString("PK_SEQ") %>" value="<%= kmRs.getString("PHANTRAM") %>"  style="width:100"  onkeypress="return isNumberKey(event)">
												</TD>
																
																						
											 </TR>
																									
									<%		i++;
										} 
									}
										%> 
										
										
											</TABLE>	
									</div>
												 --%>			
								
							
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
	if(ntsList != null) ntsList.close();
	if(cdList != null) cdList.close();

	//if(kmRs!=null)kmRs.close();
	if(pbRs != null) pbRs.close();
	if(phanBoList!=null)phanBoList.clear();
	dcBean.DBClose();
	session.setAttribute("dcBean", null); 
}catch(Exception ex)
{
	ex.printStackTrace();
}
} %>