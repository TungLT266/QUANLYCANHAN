<%@page	import="geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.nhacungcap.INhacungcap"%>
<%@ page import="geso.traphaco.center.beans.nhacungcap.INhacungcapList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>

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
	ICapnhatnhanvien obj = (ICapnhatnhanvien) session.getAttribute("obj");

	ResultSet quyen = obj.getquyen();
	ResultSet quyenchon = obj.getquyenchon();
	ResultSet kenh = obj.getkenh();
	ResultSet kenhRs = obj.getKenhRs();
	ResultSet kenhchon = obj.getkenhchon();
	ResultSet npp = obj.getnpp();
	ResultSet nppchon = obj.getnppchon();
	ResultSet sanpham = obj.getsanpham();
	ResultSet sanphamchon = obj.getsanphamchon();
	ResultSet kho = obj.getKhoRs();
	ResultSet khochon = obj.getKhochonrs();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet nhaphanphoi = obj.getnhaphanphoi();
	ResultSet phongban = obj.getphongbanRs();

	ResultSet dmquyen = obj.getDanhmucquyenRs();
	
	ResultSet ttRs = obj.getTtRs();
	ResultSet qhRs = obj.getQhRs();
	ResultSet diabanRs = obj.getDiabanRs();
	ResultSet tinhthanhRs = obj.getTinhthanhRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<head>
<TITLE> Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		//$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		$(".select2").select2();
		
	});
</script>

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

	function clearform() 
	{
		document.nccForm.tenviettat.value = "";
		document.nccForm.ncc.value = "";
		document.nccForm.tungay.value = "";
		document.nccForm.denngay.value = "";
		document.nccForm.trangthai.selectedIndex = 2;
		submitform();
	}

	function submitform() 
	{
		
		var active =$(".tabs li.current").index();
		document.forms["nccForm"].activeTab.value =active;
		
		document.forms['nccForm'].action.value = 'search';
		
		document.forms['nccForm'].submit();
	}

	function newform() {

		var active =$(".tabs li.current").index();
		document.forms["nccForm"].activeTab.value =active;
		
		var loai = $("#loai").val();
		if (loai === "1" || loai === "2" || loai === "3") {
			var title = loai === "1" ? "RSM" : loai === "2" ? "ASM"
					: loai === "3" ? "GSBH" : "";
			if ($("#loaiId").val().trim().length <= 0) {
				alert("Bạn chưa chọn tên " + title);
				return;
			}
		}

		document.forms['nccForm'].action.value = 'save';
		document.forms['nccForm'].submit();
	}
	function setThutu(h) {
		document.forms['nccForm'].thutu.value = h;
		document.forms['nccForm'].submit();

	}
	function checkedAll(chk, allquyen) 
	{
		for (var i = 0; i < chk.length; i++) 
		{
			if (allquyen.checked == true) 
			{
				chk[i].checked = true;
			} else 
			{
				chk[i].checked = false;
			}
		}
	}
	
	function check_all(checkid, doituong)
	{   
			 var spIds = document.getElementsByName(doituong);
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = checkid;
			 }
	}

	
	
	function setNppIdSelected() 
	{
		var nppId = document.getElementsByName('npp');
		var nppIds = "";
		var nppSelected = document.getElementById("nppSelected");
		for ( var ii = 0; ii < nppId.length; ii++) 
		{
			if (nppId.item(ii).checked == true) 
			{
				nppIds = nppIds + nppId.item(ii).value + ",";
			}
		}
		if (nppIds.length > 0) 
		{
			nppIds = nppIds.substring(0,nppIds.length-1);
		}
	nppSelected.value=nppIds;
}
	
	function AjaxNpp()
	{
		 	var kenhId = document.getElementById("kenhId");
		 	var kenhIds="";
				for(var i = 0; i < kenhId.options.length ; i++)
				{
					if(kenhId.options[i].selected)
						kenhIds += kenhId.options[i].value + ',';
				}
				if(kenhIds.length>0)
				{
					kenhIds=kenhIds.substring(0,kenhIds.length-1);
				}
				
				var vungId = document.getElementById("vungId");
				var vungIds="";
				for(var i = 0; i < vungId.options.length ; i++)
				{
					if(vungId.options[i].selected)
						vungIds += vungId.options[i].value + ',';
				}
				if(vungIds.length>0)
				{
					vungIds=vungIds.substring(0,vungIds.length-1);
				}
				
				
				var khuvucId = document.getElementById("khuvucId");
				var khuvucIds="";
				for(var i = 0; i < khuvucId.options.length ; i++)
				{
					if(khuvucId.options[i].selected)
						khuvucIds += khuvucId.options[i].value + ',';
				}
				if(khuvucIds.length>0)
				{
					khuvucIds=khuvucIds.substring(0,khuvucIds.length-1);
				}
				
				
				
				//Lay tat ca nppIdSelecd nhung nhapp da tick chon 
				var all_NppId_Checked="";
				var nppId= document.getElementsByName('npp');
				for(var ii=0;ii<nppId.length;ii++)
				{
					if(nppId.item(ii).checked==true)
					{
						all_NppId_Checked=all_NppId_Checked+nppId.item(ii).value+",";
					}		
				}
				if(all_NppId_Checked.length>0)
				{
					all_NppId_Checked=all_NppId_Checked.substring(0,all_NppId_Checked.length-1);
				}
				
				 $.get("../../CapNhatNhanVienAjax?action=ajaxNpp&kenhId="+kenhIds+"&vungId="+vungIds+"&kvId="+khuvucIds+"&nppSelected="+all_NppId_Checked+"", function(list,status) {
						var table = $('#nppTable');
							table.html(
								'<TABLE id="nppTable">'+
				                    '<TR class="tbheader">'+
			                        '<TH align="center" width="10%">Mã</TH>'+
			                        '<TH align="left" width="40%"> Tên </TH>'+
			                        '<TH align="center" width="20%"> Chọn tất cả <input type ="checkbox" name="checknpp_all"  onchange="check_all(this.checked ,\'npp\');"   "></TH>'+
			                    '</TR>'
		 					);
							$.each(list, function(index, data) {
								var checked='';
								if(all_NppId_Checked.indexOf(data.nppId)>=0)
									 checked='checked="checked"';
								var vclass= document.createElement("tr");
									vclass.setAttribute("class", "tblightrow");
								if(index % 2 !=0)
									vclass.setAttribute("class", "tblightrow");
								$(vclass).appendTo(table)
									.append($('<td><input type=text   value='+data.nppMa +' style="width: 100%;"  readonly="readonly" ></td>' ))
									.append($("<td><input type='text'  value= '"+data.nppTen+"' style='width: 100%;'  readonly='readonly'> </td>" ))
									.append($('<td><input type=checkbox value='+data.nppId+' name="npp" '+checked+'  ></td>' ));
								
							});
						
					});
			 
	}
	
	function ShowPopup() {
	    document.getElementById('light').style.display = 'block';
	    document.getElementById('fade').style.display = 'block'
	}
	function HidePopup() {
	    document.getElementById('light').style.display = 'none';
	    document.getElementById('fade').style.display = 'none';
	}
	
	function ajaxOption(id)
	{
		var str = '';
		var str2 = '';
		var str3='';
		var str4='';
		var tinhIds =document.getElementById("ttId");
		for(var j = 0; j < tinhIds.options.length ; j++)
		{
			if(tinhIds.options[j].selected)
				str4 += tinhIds.options[j].value + ',';
		}
		
		var str5='';
		var qhIds =  document.getElementById("qhId");
		for(var j = 0; j < qhIds.options.length ; j++)
		{
			if(qhIds.options[j].selected)
				str5 += qhIds.options[j].value + ',';
		}
		var xmlhttp;
		
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
		      document.getElementById(id).innerHTML = xmlhttp.responseText;
		   }
		}
		xmlhttp.open("POST","../../AjaxNhomNpp?type=" + id + "&kenhId=" + str + "&vungId=" + str2 + "&kvId=" + str3 + "&tpId=" + str4 + "&qhId=" + str5 + "&nhomId=<%=obj.getId()%>",true);
		xmlhttp.send();
	}
	
	
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

	<form name="nccForm" method="post" action="../../CapnhatnhanviennewSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="Id" value='<%=obj.getId()%>'> 
		<input type="hidden" name="manv" value=''> 
		<input type="hidden" name="chonquyen" value=''> 
		<input type="hidden" id="nppSelected" name="nppSelected" value='<%=obj.getNppIds()%>'> 
		<input type="hidden" id="activeTab" name="activeTab" value='<%=obj.getActiveTab()%>'>

		<div id="main" style="width: 100%">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống > Cập nhật nhân viên > Cập nhật</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn &nbsp;<%=userTen%>
				</div>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Cập nhật nhân viên </legend>
					<div style="width: 100%; float: none" align="left">
						<TABLE border="0" width="100%">
							<TR>
							<TR>
								<TD>
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class="tbdarkrow">
											<TD width="30" align="left">
												<A href="../../CapnhatnhanvienSvl?userId=<%=userId%>">
													<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
												</A>
											</TD>
											<TD width="2" align="left"></TD>
											<TD width="30" align="left">
												<A href="javascript: newform()">
													<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"> </A>
											</TD>
											<TD>&nbsp;</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
										<LEGEND class="legendtitle">Thông báo </LEGEND>
										<textarea name="dataerror" style="width: 100%; font-weight: bold" cols="100%" rows="1"><%=obj.getmsg()%> </textarea>
									</FIELDSET>
								</TD>
							</tr>
						</TABLE>

						<%  
						String style = "";
						String current = "";
					 	/* if(obj.getphanloai().equals("1")){
					 		style="style=\"display:none\"";
					 	} */
					 	
					 	try
					 	{
					 		boolean hienDLN = false;
					 		if( obj.getphanloai().equals("2") || ( obj.getphanloai().equals("1") && ( obj.getLoai().equals("0") || obj.getLoai().equals("5") || obj.getLoai().equals("7") || obj.getLoai().equals("6") ) ) )
					 			hienDLN = true;
					 		
					 		boolean hienDIABAN = false;
					 		if( obj.getphanloai().equals("1") && ( obj.getLoai().equals("7") || obj.getLoai().equals("6") ) )
					 			hienDIABAN = true;
					 		
					 		boolean hienKENH = false;
					 		if( obj.getphanloai().equals("2") || ( obj.getphanloai().equals("1") && ( obj.getLoai().equals("0") || obj.getLoai().equals("1") || obj.getLoai().equals("5") || obj.getLoai().equals("7") || obj.getLoai().equals("6") ) ) )
					 			hienKENH = true;
					 		
					 		boolean hienNPP = false;
					 		if( obj.getphanloai().equals("2") || ( obj.getphanloai().equals("1") && !( obj.getLoai().equals("3") || obj.getLoai().equals("4") ) ) )
					 			hienNPP = true;
					 		
					 	%>
						<table width="100%">
							<tr>
								<td>

									<ul class="tabs">
										<li
											<%=obj.getActiveTab().equals("0") ? "class='current'" : "" %> >
											<a href="#tabNv">Cập nhật nhân viên</a>
										</li>
										
										<li
											<%=obj.getActiveTab().equals("1") ? "class='current'" : "" %> >
											<a href="#tabMenu">Menu</a></li>
										
										<% if( hienKENH ) { %>
										<li
											<%=obj.getActiveTab().equals("2") ? "class='current'" : "" %> >
											<a href="#tabKenh">Kênh</a></li>
										<% } %>
										
										<% if( hienNPP ) { %>
										<li
											<%=obj.getActiveTab().equals("3") ? "class='current'" : "" %> >
											<a href="#tabNpp">MTV / DLPP</a></li>
										<% } %>
										
										<li
											<%=obj.getActiveTab().equals("4") ? "class='current'" : "" %> >
											<a href="#tabSp">Sản phẩm</a></li>
										<li
											<%=obj.getActiveTab().equals("5") ? "class='current'" : "" %> >
											<a href="#tabKho">Kho</a></li>
										<%-- <%} %> --%>
									</ul>

									<div class="panes">
									
										<div id="tabNv" class="tab_content">

											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="plainlabel" valign="bottom">

													<TD width="100%" align="left">
														<FIELDSET>
															<LEGEND class="legendtitle">Cập nhật nhân viên </LEGEND>

															<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
																
																<TR>
																	<TD class="plainlabel" >Phân loại</TD>
																	<TD class="plainlabel">
																		<select name="phanloai" onchange="submitform();" class="select2" style="width: 400px;" >
																			<option value=""></option>
																			<% if (obj.getphanloai().equals("2")) { %>
																				<option value="2" selected>Tổng công ty</option>
																			<% } else { %>
																				<option value="2">Tổng công ty</option>
																			<% } %>
																			<% if (obj.getphanloai().equals("1")) { %>
																				<option value="1" selected>Công ty MTV / DLPP</option>
																			<% } else { %>
																				<option value="1">Công ty MTV / DLPP</option>
																			<% } %>
																		</select>
																	</TD>
																	
																	<TD class="plainlabel">Phòng ban</TD>
																	<TD class="plainlabel" valign="middle">
											                            <select name="phongbanId" id="phongbanId" style = "width:200px">
											                            	<option value=""></option>
											                            	<%
												                        		if(phongban != null)
												                        		{
												                        			while(phongban.next())
												                        			{  
												                        			if( phongban.getString("pk_seq").equals(obj.getphongbanId())){ %>
												                        				<option value="<%= phongban.getString("pk_seq") %>" selected="selected" ><%= phongban.getString("TEN") %></option>
												                        			<%}else { %>
												                        				<option value="<%= phongban.getString("pk_seq") %>" ><%= phongban.getString("TEN") %></option>
												                        		 <% } } phongban.close();
												                        		}
												                        	%>
											                            </select>
											                        </TD>  
                        
																	<%-- <TD class="plainlabel" >																		
																		<%= util.getSelectBox(phongban, "width: 400px;", "phongbanId", "", obj.getphongbanId(), "PK_SEQ", "TEN", "0", false, true) %>																		
																	</TD> 				
																	 --%>
																</TR>
																
																<% if( obj.getphanloai().equals("1") ) { %>
																<TR>
																	<TD class="plainlabel">Công ty MTV / DLPP</TD>
																	<TD class="plainlabel" colspan="3" >
																		
																		<%= util.getSelectBox(nhaphanphoi, "width: 400px;", "nppId", "submitform();", obj.getnppId(), "convsitecode", "TEN", "0", false, true) %>
																		
																	</TD>
																</TR>
																<% } %>
																
																<% if ( obj.getphanloai().equals("1") ) { %>
																<TR>
																	<TD class="plainlabel">Loại nhân viên</TD>
																	<TD class="plainlabel" colspan="3" >
																		<select name="loai" id="loai" onchange="submitform();" class="select2" style="width: 400px;" >
																			<option value="0"
																				<%=obj.getLoai().equals("0") ? " selected " : ""%>></option>
																			<option value="5"
																				<%=obj.getLoai().equals("5") ? " selected " : ""%>>Trung tâm</option>
																			<option value="7"
																				<%=obj.getLoai().equals("7") ? " selected " : ""%>>CS / Kế toán / CSC</option>
																			<option value="4"
																				<%=obj.getLoai().equals("4") ? " selected " : ""%>>Trình dược viên</option>
																			<option value="3"
																				<%=obj.getLoai().equals("3") ? " selected " : ""%>>Phụ Trách Tỉnh/ GĐCN Cấp 2</option>
																			<option value="1"
																				<%=obj.getLoai().equals("1") ? " selected " : ""%>>Giám Đốc Miền</option>
																			<option value="2"
																				<%=obj.getLoai().equals("2") ? " selected " : ""%>>Phụ trách Vùng</option>
																			<option value="6"
																				<%=obj.getLoai().equals("6") ? " selected " : ""%>>loaiId</option>																			
																		</select>
																	</TD>
																</TR>
																<%
																	if (obj.getLoai().equals("1") || obj.getLoai().equals("2")|| obj.getLoai().equals("3") || obj.getLoai().equals("4") || obj.getLoai().equals("6") && obj.getLoaiRs() != null) {
																		ResultSet loaiRs = obj.getLoaiRs();
																		String title = obj.getLoai().equals("1") ? "Giám đốc miền" : obj.getLoai().equals("2") ? "Phụ trách vùng" : obj.getLoai().equals("3") ? "Phụ trách tỉnh" : obj.getLoai().equals("4") ? "Trình dược viên" : obj.getLoai().equals("6") ? "NV Giao nhận" : "NV";
																%>
																<TR>
																	<TD class="plainlabel"><%=title%></TD>
																	<TD class="plainlabel" colspan="3" >

																		<%= util.getSelectBox(loaiRs, "width: 400px;", "loaiId", "", obj.getLoaiId(), "PK_SEQ", "TEN", "0", false, true) %>
																			
																	</TD>
																</TR>
																															
																<% } } %>
																
																<% if( hienDIABAN ) { %>
																<TR>
																	<TD class="plainlabel">Địa bàn</TD>
																	<TD class="plainlabel" >
																		
																		<%= util.getSelectBox(diabanRs, "width: 400px;", "diabanIds", "", obj.getDiabanId(), "pk_seq", "TEN", "0", true, true) %>
																		
																	</TD>
																	<TD class="plainlabel">Tỉnh thành</TD>
																	<TD class="plainlabel" >
																		
																		<%= util.getSelectBox(tinhthanhRs, "width: 400px;", "tinhthanhIds", "", obj.getTinhthanhId(), "pk_seq", "TEN", "0", true, true) %>
																		
																	</TD>
																</TR>
																<% } %>
																
																<% if( hienDLN ) { %>
																<TR>
																	<TD class="plainlabel">Họ tên</TD>
																	<TD class="plainlabel">
																		<INPUT name="ten" type="text" size="40" value="<%=obj.getTen()%>">
																	</TD>
																	<TD class="plainlabel">Ngày sinh</TD>
																	<TD class="plainlabel">
																		<INPUT name="ngaysinh" type="text" size="20" value="<%=obj.getngaysinh()%>">
																	</TD>
																</TR>
																<TR>
																	<TD class="plainlabel">Email</TD>
																	<TD class="plainlabel">
																		<INPUT name="email" type="text" size="40" value="<%=obj.getemail()%>">
																	</TD>
																	<TD class="plainlabel">Điện thoại</TD>
																	<TD class="plainlabel">
																		<INPUT name="dienthoai" type="text" size="20" value="<%=obj.getdienthoai()%>">
																	</TD>
																</TR>
																<% } %>
																
																<TR>
																	<TD class="plainlabel" style="width: 100px;" >Tên đăng nhập</TD>
																	<TD class="plainlabel" style="width: 250px;" >
																		<INPUT name="tendangnhap" type="text" size="20" value="<%=obj.gettendangnhap()%>">
																	</TD>
																	<TD class="plainlabel" style="width: 100px;" >Mật khẩu </TD>
																	<TD class="plainlabel">
																		<INPUT name="matkhau" type="password" size="20" value="">
																	</TD>
																</TR>

																<tr>
																	<TD class="plainlabel">Trạng thái</TD>
																	<TD class="plainlabel">
																		<% if (obj.gettrangthai().trim().equals("1")) { %> 
																			<input name="trangthai" type="checkbox" value="1" checked> 
																		<% } else { %> 
																			<input name="trangthai" type="checkbox" value="1"> <% } %> <i>Hoạt động</i>
																	</TD>
																	<TD class="plainlabel"> Xuất hóa đơn</TD>
																	<TD class="plainlabel">
																		<% if (obj.getXuathoadon().trim().equals("1")) { %> 
																			<input name="xuathoadon" type="checkbox" value="1" checked> 
																		<% } else { %> 
																			<input name="xuathoadon" type="checkbox" value="1">
																		<% } %>
																	</TD>
																</TR>
																

																<% if (obj.getphanloai().equals("1") ) { %>
																
																<TR style="display: none;" >

																	<TD class="plainlabel">Vùng</TD>
																	<TD class="plainlabel">
																		
																		<%= util.getSelectBox(vung, "", "vungId", "", obj.getvungId(), "pk_seq", "TEN", "0", false, true) %>
																		
																	</TD>
																
																	<TD class="plainlabel">Khu Vực</TD>
																	<TD class="plainlabel">
																	
																		<%= util.getSelectBox(khuvuc, "", "khuvucId", "", obj.getkhuvucId(), "pk_seq", "TEN", "0", false, true) %>
																	
																	</TD>
																</TR>

																<TR style="display:none">
																	<TD class="plainlabel" style="display:none">Số hóa đơn :&nbsp;&nbsp;&nbsp;Từ </TD>
																	<TD class="plainlabel" style="display:none">
																		<input name= "sohoadontu" type="text" value="<%= obj.getSohoadontu()%>" size="20">
																	</TD>																	
																</TR>
																
																<TR style="display:none">																	
																	<TD class="plainlabel">&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến  </TD>
																	<TD class="plainlabel">
																		<input name= "sohoadonden" type="text" value="<%= obj.getSohoadonden()%>" size="20">
																	</TD>
																</TR>
																
																<% } %>

																
															</TABLE>
														</FIELDSET>
													</TD>
												</TR>
											</TABLE>
										</div>
										
										<div id="tabMenu" class="tab_content">
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%">Tên quyền</TH>
													<TH width="10%">Chọn tất cả
														<input type="checkbox" name="allquyen" value='1' onclick="checkedAll(document.nccForm.quyen,document.nccForm.allquyen);">
													</TH>
												</TR>
												<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(quyenchon !=null)
									 while(quyenchon.next())
									 { if (m % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=quyenchon.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="quyen"
														value="<%=quyenchon.getString("pk_seq")%>" checked></TH>
												</TR>
												<% m++; 
			                                       }
			                                     if(quyen !=null)
												 while(quyen.next())
												 {
													 if (m % 2 != 0) {%>
															<TR class=<%=lightrow%> align="left">
																<%} else { %>
															
															<TR class=<%= darkrow%> align="left">
																<%} %>
																<TH width="20%"><%=quyen.getString("Ten")%></TH>
																<TH width="10%"><input type="checkbox" name="quyen"
																	value="<%=quyen.getString("pk_seq")%>"></TH>
															</TR>
															<% m++; 
			                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
									<% if( hienKENH ) { %>	
										<div id="tabKenh" class="tab_content" <%=style%>>

											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">

												<TR class="tbheader">
													<TH width="20%">Tên kênh</TH>
													<TH width="10%">Chọn tất cả<input type="checkbox"
														name="allkenh" value='1'
														onclick="checkedAll(document.nccForm.kenh,document.nccForm.allkenh);">
													</TH>
												</TR>
												<% 
									int n = 0;
									//String lightrow = "tblightrow";
								//	String darkrow = "tbdarkrow";
									if(kenhchon !=null)
									 while(kenhchon.next())
									 { if (n % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=kenhchon.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="kenh"
														value="<%=kenhchon.getString("pk_seq")%>" checked></TH>
												</TR>
												<% n++; 
                                       }
                                     if(kenh !=null)
									 while(kenh.next())
									 {
										 if (n % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=kenh.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="kenh"
														value="<%=kenh.getString("pk_seq")%>"></TH>
												</TR>
												<% n++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
									<% } %>
									
									<% if( hienNPP ) { %>									
										<div id="tabNpp" class="tab_content" <%=style%>>

											<TABLE id="nppTable" width="100%" border="0"
												cellspacing="1px" cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%">Mã</TH>
													<TH width="20%">Tên</TH>
													<TH width="10%">Chọn tất cả<input type="checkbox" name="allnpp" value='1' onclick="check_all(this.checked ,'npp');">
													</TH>
												</TR>


												<% 
									 int h = 0;
                                     if(npp != null)
									 while(npp.next())
									 {
										 if (h % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=npp.getString("nppMa")%></TH>
													<TH width="20%"><%=npp.getString("nppTen")%></TH>
													<TH width="10%">
														<%if( util.checkId(npp.getString("NppId"), obj.getNppIds(), ",") ) {  %>
														<input type="checkbox" name="npp"
														value="<%=npp.getString("NppId")%>" checked="checked">
														<%}else { %> <input type="checkbox" name="npp"
														value="<%=npp.getString("NppId")%>"> <%} %>
													</TH>
												</TR>
												<% h++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
									<% } %>
									
										<div id="tabSp" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">


												<TR class="tbheader">
													<TH width="20%">Tên Sản phẩm</TH>
													<TH width="10%">Chọn tất cả<input type="checkbox"
														name="allsanpham" value='1'
														onclick="checkedAll(document.nccForm.sanpham,document.nccForm.allsanpham);">
													</TH>
												</TR>
												<tr>
													<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
														style="font-weight: bold; font-size: 0.9em">Nhãn
															hàng </span>&nbsp;&nbsp; <select name="nhanhangId"
														onchange="submitform();">
															<option value="" selected></option>
															<% if(nhanhang!=null)
											while(nhanhang.next())
										   	{ if(nhanhang.getString("pk_seq").equals(obj.getnhanhangId()))
										   	{%>
															<option value="<%=nhanhang.getString("pk_seq")%>"
																selected><%=nhanhang.getString("ten") %></option>
															<%} else { %>
															<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten") %></option>
															<%} }%>
													</select> <span style="font-weight: bold; font-size: 0.9em">Chủng
															loại </span>&nbsp;&nbsp;<select name="chungloaiId"
														onchange="submitform();">
															<option value="" selected></option>
															<% if(chungloai!=null)
											while(chungloai.next())
										   	{ if(chungloai.getString("pk_seq").equals(obj.getchungloaiId()))
												{%>
															<option value="<%=chungloai.getString("pk_seq")%>"
																selected><%=chungloai.getString("ten") %></option>
															<%} else { %>
															<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten") %></option>
															<%}} %>
													</select></td>
													<% 
									int k = 0;
									//String lightrow = "tblightrow";
								//	String darkrow = "tbdarkrow";
									if(sanphamchon !=null)
									 while(sanphamchon.next())
									 { if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=sanphamchon.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="sanpham"
														value="<%=sanphamchon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
                                       }
                                     if(sanpham !=null)
									 while(sanpham.next())
									 {
										 if (k % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=sanpham.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="sanpham"
														value="<%=sanpham.getString("pk_seq")%>"></TH>
												</TR>
												<% k++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
										<div id="tabKho" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<!-- <TH width="20%">Mã kho</TH> -->
													<TH width="10%">Mã kho</TH>
													<TH width="20%">Tên kho</TH>
													<TH width="20%">Diễn giải</TH>
													<TH width="10%">Chọn tất cả<input type="checkbox" name="allkho" value='1' onclick="checkedAll(document.nccForm.kho,document.nccForm.allkho);">
													</TH>							
												</TR>
											<%		 
											 k = 0;
											 if(khochon !=null)
											 while(khochon.next())
											 { if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="10%"><%=khochon.getString("MA")%></TH>
													<TH width="20%"><%=khochon.getString("Ten")%></TH>
													<TH width="20%"><%=khochon.getString("DIACHI")%></TH>
													<TH width="10%"><input type="checkbox" name="kho"
														value="<%=khochon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
			                                       }
			                                     if(kho !=null)
												 while(kho.next())
												 {
													 if (k % 2 != 0) {%>
															<TR class=<%=lightrow%> align="left">
																<%} else { %>
															
															<TR class=<%= darkrow%> align="left">
																<%} %>
																<TH width="10%"><%=kho.getString("MA")%></TH>
																<TH width="20%"><%=kho.getString("Ten")%></TH>
																<TH width="20%"><%=kho.getString("DIACHI")%></TH>
																<TH width="10%"><input type="checkbox" name="kho"
																	value="<%=kho.getString("pk_seq")%>"></TH>
															</TR>
															<% k++; 
			                                       }%>
			
															<TR>
																<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
															</TR>
												
											</TABLE>
										</div>

										<% }
										 	catch (Exception ex)
										 	{
										 		ex.printStackTrace();
										 		System.out.println("lỗi !!!");
										 	}
										%>

									</div>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
	</form>

</BODY>
</html>

<%
if(qhRs!=null)qhRs.close();
if(ttRs!=null) ttRs.close();
if(kho!=null) kho.close();
if(khochon!=null) khochon.close();
obj=null;
	}
%>