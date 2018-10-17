<%@page import="geso.traphaco.distributor.beans.butrucongno.IButrucongno"%>
<%@page import="geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhang"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp")   ;
	}else{ %>

<% IButrucongno nvgnBean = (IButrucongno)session.getAttribute("nvgnBean");
   ResultSet hoadonList = nvgnBean.getHoadonList() ;
   
   String[] hdId = nvgnBean.getHdId();
   String[] hdNgayhd = nvgnBean.getHdNgayhd();
   String[] hdKhid = nvgnBean.getHdKhid();
   String[] hdMakh = nvgnBean.getHdMakh();
   String[] hdMadt = nvgnBean.getHdMadt();
   String[] hdKyhieu = nvgnBean.getHdKyhieu();
   String[] hdSohd = nvgnBean.getHdSohd();
   String[] hdSotien = nvgnBean.getHdSotien();
   String[] hdChon = nvgnBean.getHdChon();
   String[] HdDuno = nvgnBean.getHdDuno();
   String[] HdDuco = nvgnBean.getHdDuco();
   String[] HdGhino = nvgnBean.getHdGhino();
   String[] HdGhico = nvgnBean.getHdGhico();
   
   NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
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
</script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	}
	function submitform()
	{
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}
	function xuatexcel()
	{
		document.forms['nvgnForm'].action.value='xuatexcel';
	   	document.forms['nvgnForm'].submit();
	}
	
	function saveform()
	{	  				 				 
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms['nvgnForm'].action.value='save';
	     document.forms['nvgnForm'].submit();
	}
	
	function Change_NO_CO(stt)//khi check từng cái
	{		
		var hdduno = document.getElementsByName("hdduno");
		var hdduco = document.getElementsByName("hdduco");
		
		var hdghico1 = document.getElementsByName("hdghico1").item(stt);
		var hdghino1 = document.getElementsByName("hdghino1").item(stt);
		
		var hdId = document.getElementsByName("hdId").item(stt);
		
		var khId = document.getElementsByName("khId").item(stt);
		var ckKh = document.getElementById("ckKh");
		
		var chon = "";
		
		if(ckKh.checked)
			chon = document.getElementById(khId.value);
		else
			chon = document.getElementById(hdId.value);
		
		var tc_gn = document.getElementById("tc_gn").value.replace(/,/g, "");
		if(tc_gn == '')
			tc_gn=0;
		var tc_gc = document.getElementById("tc_gc").value.replace(/,/g, "");
		if(tc_gc == '')
			tc_gc=0;
				
		if(chon.checked)
		{
			hdghico1.value = hdduno.item(stt).value;
			hdghino1.value = hdduco.item(stt).value;
			hdghino1.readOnly=false;
			hdghico1.readOnly=false;
			if(hdduco.item(stt).value!='')
				tc_gn = parseFloat(tc_gn)+ parseFloat(hdduco.item(stt).value.replace(/,/g, ""));
			if(hdduno.item(stt).value!='')
				tc_gc = parseFloat(tc_gc) + parseFloat(hdduno.item(stt).value.replace(/,/g, ""));

		}
		else
		{	
			hdghico1.value ="";
			hdghino1.value="";
			hdghino1.readOnly=true;
			hdghico1.readOnly=true;
			if(hdduco.item(stt).value!='')
				tc_gn = parseFloat(tc_gn)- parseFloat(hdduco.item(stt).value.replace(/,/g, ""));
			if(hdduno.item(stt).value!='')
				tc_gc = parseFloat(tc_gc) - parseFloat(hdduno.item(stt).value.replace(/,/g, ""));
		}
		
		
/* 		document.getElementById("tc_gn").value=DinhDangTien(tc_gn);
		document.getElementById("tc_gc").value=DinhDangTien(tc_gc); */
		ThanhToan(stt);
		
		UnCheckedAll();
	}
	
	function CheckAll()//hàm check hết 
	{
		var selectAll = document.getElementById("selectAll");		
		var hdId = document.getElementsByName("hdId");		
		var hdduno = document.getElementsByName("hdduno");
		var hdghico1 = document.getElementsByName("hdghico1"); 
		
		var hdduco = document.getElementsByName("hdduco");
		var hdghino1 = document.getElementsByName("hdghino1");
		
		var ckKh = document.getElementById("ckKh");
		var khId = document.getElementsByName("khId");
		
		var tc_gn = 0;
		var tc_gc = 0;
		
		if(selectAll.checked)//khi check hết
		{
			if(!ckKh.checked)
			{
				for(i = 0; i < hdId.length; i++)
				{	
					
					document.getElementById(hdId.item(i).value).checked= true;								
					hdghico1.item(i).value = hdduno.item(i).value;
					hdghino1.item(i).value=hdduco.item(i).value;
					hdghino1.item(i).readOnly=false;
					hdghico1.item(i).readOnly=false;
					if(hdduco.item(i).value!='')
						tc_gn += parseFloat(hdduco.item(i).value.replace(/,/g, "") );
					if(hdduno.item(i).value!='')
						tc_gc +=parseFloat(hdduno.item(i).value.replace(/,/g, ""));
				}
			}
			
			if(ckKh.checked)
			{
				for(i = 0; i < khId.length; i++)
				{	
					
					document.getElementById(khId.item(i).value).checked= true;								
					hdghico1.item(i).value = hdduno.item(i).value;
					hdghino1.item(i).value=hdduco.item(i).value;
					hdghino1.item(i).readOnly=false;
					hdghico1.item(i).readOnly=false;
					if(hdduco.item(i).value!='')
						tc_gn += parseFloat(hdduco.item(i).value.replace(/,/g, "") );
					if(hdduno.item(i).value!='')
						tc_gc +=parseFloat(hdduno.item(i).value.replace(/,/g, ""));
				}
			}
			
			document.getElementById("tc_gn").value=DinhDangTien(tc_gn);
			document.getElementById("tc_gc").value=DinhDangTien(tc_gc);
		}
		else //khi bỏ check
		{
			if(ckKh.checked)
			{
				for(i = 0; i < khId.length; i++)
				{	
					document.getElementById(khId.item(i).value).checked = false;				
					hdghico1.item(i).value ="";
					hdghino1.item(i).value="";
					hdghino1.item(i).readOnly=true;
					hdghico1.item(i).readOnly=true;
				}
			}
			else
			{
				for(i = 0; i < hdId.length; i++)
				{	
					document.getElementById(hdId.item(i).value).checked = false;				
					hdghico1.item(i).value ="";
					hdghino1.item(i).value="";
					hdghino1.item(i).readOnly=true;
					hdghico1.item(i).readOnly=true;
				}
			}
			document.getElementById("tc_gn").value=DinhDangTien(tc_gn);
			document.getElementById("tc_gc").value=DinhDangTien(tc_gc);
		}
	}
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("selectAll");
		selectAll.checked = false;
	}
	
	
	function DinhDang()
	{
		var sotien = document.getElementById("sotientu").value.replace(/,/g,"");
		document.getElementById("sotientu").value= DinhDangTien(sotien);
	}
	
	function DinhDang1()
	{
		var sotien = document.getElementById("sotienden").value.replace(/,/g,"");
		document.getElementById("sotienden").value= DinhDangTien(sotien);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
		{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nvgnForm" method="post" action="../../ButrucongnoUpdateSvl" >
<input type="hidden" name="nppId" id= "nppId" value='<%= nvgnBean.getNppId() %>'>
<input type="hidden" name="id" id= "id" value='<%= nvgnBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý công nợ > Bù trừ công nợ > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= nvgnBean.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<%-- <div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ButrucongnoSvl?userId=<%=userId %>">		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
    </div> --%>
    
	<div align="left" style="width:100%; float:none; clear:left" class="tbdarkrow">
		<div style="float:none; width:100%" align="left">
			<table width="100%" cellspacing="0" cellpadding="6px">
				<tr>
					<tr>
						<A href= "../../ButrucongnoSvl?userId=<%=userId %>">		 		
						<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
					</tr>
					<tr>
						<a href="javascript:xuatexcel()" >
						<img style="top: -4px;" src="../images/excel.gif" alt=""></a>
					</tr>
				</tr>
			</table>
		</div>
	</div>														
	                               
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1"><%= nvgnBean.getMessage() %></textarea>
		         <% nvgnBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Bù trừ công nợ </legend>       	
        <div style="float:none; width:100%" align="left">
            <table width="100%" cellspacing="0" cellpadding="6px">
                <TR>
                    <TD width="15%" class="plainlabel" > Ngày chứng từ</TD>
                    <TD width="15%" class="plainlabel">
                        <INPUT type="text" name="ngay" class = "days" size="40" value="<%= nvgnBean.getNgay()%>">
                    </TD>
                     <TD width="15%" class="plainlabel" > Ghi chú</TD>
                    <TD width="20%" class="plainlabel">
                        <INPUT type="text" name="ghichu"  size="50" value="<%= nvgnBean.getGhichu()%>">
                    </TD>                      
                                                                                   	                               
                </TR>
                
                <TR>
                	<TD class="plainlabel" colspan="6"></TD>
                </TR>
                
                </table>
             
             
             <div id="xoanokh" align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
             		<input type="hidden" name="ckKh"  value="<%=nvgnBean.getckKh() %>">
	             <table width="100%" border="0" cellspacing="1" cellpadding="1">
	                    <tr class="tbheader">
	                    	<th style="width:2% " align="center">STT </th>
	                        <th style="width:30% " align="center">Tên khách hàng OTC/ETC </th>
	                        <% 
	                        System.out.println("JSP: nvgnBean.getckKh: '" + nvgnBean.getckKh()+"' " );
	                        if(!nvgnBean.getckKh().equals("1")) {%>
	                        <th style="width:10% " align="center">Số chứng từ</th>
	                        <th style="width:8% " align="center">Ngày hóa đơn</th>
	                        <th style="width:10% " align="center">Số hóa đơn</th>
	                         <%} %>
	                        <th style="width:10% " align="center">Dư nợ </th>
	                        <th style="width:10% " align="center">Dư có </th>
	                        <th style="width:10% " align="center">Ghi nợ </th>
	                        <th style="width:10% " align="center">Ghi có </th>	               
	                        <th style="width:5% " align="center" >Chọn <input type="checkbox" id="selectAll" onChange="CheckAll()"/></th>
	                    </tr>
	                   
	                  <Tr>
	                   		<% if(!nvgnBean.getckKh().equals("1")) {%>
	                   		<Td align="center" colspan="5">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%" value="" > <!-- STT_TongCong -->
	                   		</Td>
	                   		<%} %>
	                   		
	                   		<% if(nvgnBean.getckKh().equals("1")) {%>
	                   		<Td align="center" colspan="2">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%" value="" > <!-- STT_TongCong -->
	                   		</Td>
	                   		<%} %>
	                   		
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%; text-align:right" value="<%= nvgnBean.getTc_duno()%>" > <!-- Dư nợ-->
	                   		</Td>
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%; text-align:right" value="<%= nvgnBean.getTc_duco()%>" > <!-- Dư có-->
	                   		</Td>
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="tc_gn" id="tc_gn" style="width: 100%; text-align:right" value="<%=nvgnBean.getTc_ghino() %>" > <!--Ghi nợ-->
	                   		</Td>
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="tc_gc" id="tc_gc" style="width: 100%; text-align:right" value="<%=nvgnBean.getTc_ghico() %>" > <!--Ghi nợ-->
	                   		</Td>
	                   </Tr>
	                    <%
	                    if(!nvgnBean.getckKh().equals("1"))
	                    {
	                    int n = 0;
	                   	int stt=1;
	                    if(hdId != null)
	                    {	
	                    	int k =  hdId.length;
	                    	String[] _NO=new String[k];
							String[] _CO=new String[k];
							String[] _GHINO = new String[k];
							String[] _GHICO = new String[k];
							for(int i = 0; i < hdId.length ; i++){ 
														
									double duno = Double.parseDouble(HdDuno[i]);
									double duco = Double.parseDouble(HdDuco[i]);
									double ghino = Double.parseDouble(HdGhino[i]);
									double ghico = Double.parseDouble(HdGhico[i]);
									
									System.out.println("du no: "+ duno);
									if(duno > 0 )
									{
										_NO[i] = formatter.format(duno);
										_CO[i] = "";
									}
									else
									{
										duno=duno*(-1);
										_NO[i] = "";										
										_CO[i] = formatter.format(duno);
									}
									
									if(duco != 0)
									{
										_NO[i] = "";
										_CO[i] = formatter.format(duco);
									}								
																		
									if(ghino >0)
										_GHINO[i]=formatter.format(ghino);	
									else									
										_GHINO[i] ="";
									
									if(ghico >0)
										_GHICO[i]=formatter.format(ghico);
									else
										_GHICO[i]="";
							
								%>
						<TR>
							
							<TD align="center">
								<input type="text" readonly="readonly" name="STT" style="width: 100%" value="<%= stt%>" > <!-- Tên khách hàng -->							
							</TD>
							<TD align="center">
							    <input type="hidden" name="khId"  value="<%= hdKhid[i]%>" > 
								<input type="text" readonly="readonly" name="khMa" style="width: 100%" value="<%= hdMakh[i]%>" > <!-- Tên khách hàng -->							
							</TD>
							<TD align="left">
								<input type="text" readonly="readonly" name="hdId" style="width: 100%" value="<%= hdId[i]%>" > <!-- Số chứng từ -->
							</TD> 
							<TD align="center">
								<input type="text" readonly="readonly" name="hdNgay" style="width: 100%" value="<%= hdNgayhd[i]%>" > <!-- Ngày chứng từ -->
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsohoadon" style="width: 100%" value="<%= hdSohd[i] %>" > <!-- Số hóa đơn -->
							</TD>
							
							<TD align="right">								
								<input type="text"  id="hdduno" name="hdduno"  readonly="readonly" style="width: 100%; text-align:right" value="<%= _NO[i] %>" > <!-- Dư nợ -->
							</TD>
							<TD align="right">								
								<input type="text"  id="hdduco" name="hdduco" readonly="readonly"  style="width: 100%; text-align:right" value="<%= _CO[i] %>" > <!-- Dư có -->
							</TD>
							
							<TD align="right">								
								<input type="text" name="hdghino1"  style="width: 100%; text-align:right" readonly="readonly" value="<%= _GHINO[i] %>" onkeypress="return keypress(event);"  onChange="ThanhToan( <%= i  %> );" > <!-- Ghi nợ -->
							</TD>
							<TD align="right">								
								<input type="text"  name="hdghico1"  style="width: 100%; text-align:right" readonly="readonly" value="<%= _GHICO[i] %>" onkeypress="return keypress(event);"  onChange="ThanhToan( <%= i  %> );" > <!-- Ghi có -->
							</TD>
																												
							<TD align="center">
							<% if (hdChon[i].equals(hdId[i] )){ %>
								<input name="<%= hdId[i] %>" id="<%= hdId[i] %>"  value="<%= hdId[i] %>" type="checkbox"  checked  onChange="Change_NO_CO( <%= i  %> );" >
							<%}else{ %>											
								<input name="<%= hdId[i] %>" id="<%= hdId[i] %>" type="checkbox" value="<%= hdId[i]%>"  onChange="Change_NO_CO( <%= i  %> );" >
							<%} %>
						</TD> 
	                    </TR> <%
	                    stt++; 	}} %> 
					     	
					   <%  }else if (nvgnBean.getckKh().equals("1")){%>
					   
					   <%
	                    int n = 0;
	                   	int stt=1;
	                    if(hdKhid != null)
	                    {	
	                    	int k =  hdKhid.length;
	                    	String[] _NO=new String[k];
							String[] _CO=new String[k];
							String[] _GHINO = new String[k];
							String[] _GHICO = new String[k];
							for(int i = 0; i < hdKhid.length ; i++){ 														
									double duno = Double.parseDouble(HdDuno[i]);
									double duco = Double.parseDouble(HdDuco[i]);
									double ghino = Double.parseDouble(HdGhino[i]);
									double ghico = Double.parseDouble(HdGhico[i]);
									if(duno > 0 )
									{
										_NO[i] = formatter.format(duno);
										_CO[i] = "";
									}
									else
									{
										duno=duno*(-1);
										_NO[i] = "";										
										_CO[i] = formatter.format(duno);
									}
									
									if(duco != 0)
									{
										_NO[i] = "";
										_CO[i] = formatter.format(duco);
									}								
																		
									if(ghino >0)
										_GHINO[i]=formatter.format(ghino);	
									else									
										_GHINO[i] ="";
									
									if(ghico >0)
										_GHICO[i]=formatter.format(ghico);
									else
										_GHICO[i]="";
							
						%>
						<TR>
							
							<TD align="center">
								<input type="text" readonly="readonly" name="STT" style="width: 100%" value="<%= stt%>" > <!-- Tên khách hàng -->							
							</TD>
							<TD align="center">
							    <input type="hidden" name="khId"  value="<%= hdKhid[i]%>" > 
								<input type="text" readonly="readonly" name="khMa" style="width: 100%" value="<%= hdMakh[i]%>" > <!-- Tên khách hàng -->							
							</TD>							
							<TD align="right">								
								<input type="text"  id="hdduno" name="hdduno"  readonly="readonly" style="width: 100%; text-align:right" value="<%= _NO[i] %>" > <!-- Dư nợ -->
							</TD>
							<TD align="right">								
								<input type="text"  id="hdduco" name="hdduco" readonly="readonly"  style="width: 100%; text-align:right" value="<%= _CO[i] %>" > <!-- Dư có -->
							</TD>
							
							<TD align="right">								
								<input type="text" name="hdghino1"  style="width: 100%; text-align:right" readonly="readonly" value="<%= _GHINO[i] %>" onkeypress="return keypress(event);"  onChange="ThanhToan( <%= i  %> );" > <!-- Ghi nợ -->
							</TD>
							<TD align="right">								
								<input type="text"  name="hdghico1"  style="width: 100%; text-align:right" readonly="readonly" value="<%= _GHICO[i] %>" onkeypress="return keypress(event);"  onChange="ThanhToan( <%= i  %> );" > <!-- Ghi có -->
							</TD>
																												
							<TD align="center">
							<% if (hdChon[i].equals(hdKhid[i] )){ %>
								<input name="<%= hdKhid[i] %>" id="<%= hdKhid[i] %>"  value="<%= hdKhid[i] %>" type="checkbox"  checked  onChange="Change_NO_CO( <%= i  %> );" >
							<%}else{ %>											
								<input name="<%= hdKhid[i] %>" id="<%= hdKhid[i] %>" type="checkbox" value="<%= hdKhid[i]%>"  onChange="Change_NO_CO( <%= i  %> );" >
							<%} %>
						</TD> 
	                    </TR> <%
	                    stt++; 	}} %> 
					     	
					   <%  			   
					    } else {%>
					   
					   	<tr><td class="plainlabel" colspan="10">&nbsp;</td></tr>
					   <%} %>		      
	             </table> 
             </div>   
         </div>      
    </fieldset>	
    </div>
</div>
</form>
</body>
</html>
<% 	


	try{

		if(nvgnBean!=null){
		nvgnBean.DBclose();
		nvgnBean=null;
		}

	
	}
	catch (Exception e) {}
%>
<%}%>