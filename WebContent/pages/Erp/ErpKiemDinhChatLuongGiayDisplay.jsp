<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.giay.*"%>
<%@ page import="geso.traphaco.center.beans.thongtinsanpham.*"%>
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
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else
	{
%>
<%
	IErpKiemdinhchatluong obj = (IErpKiemdinhchatluong) session.getAttribute("dcsxBean");
		List<ITieuchikiemdinh> tckdList = (List<ITieuchikiemdinh>) obj.getTieuchikiemdinhList();
		ResultSet rsKiemDinh = (ResultSet) obj.getRsYeuCauKiemDinh();
		ResultSet rsdvdl=obj.getRsDvdl();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
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
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>
<SCRIPT language="JavaScript" type="text/javascript">
	
	function replaces()
	{
		var tieuchi = document.getElementsByName("tieuchi");
		var toantu = document.getElementsByName("toantu");
		var giatrichuan = document.getElementsByName("giatrichuan");
		var tumuc = document.getElementsByName("tumuc");
		var denmuc = document.getElementsByName("denmuc");
		var diemdat = document.getElementsByName("diemdat");
		var trangthai = document.getElementsByName("trangthai");
		var dat = document.getElementsByName("dat");
		//var tuychon = document.getElementsByName("tuychon");
		
		for(i = 0; i < tieuchi.length; i++)
		{
			if(diemdat.item(i).value != '')
			{
				var pheptoan = toantu.item(i).value;
				var tmuc = parseFloat(tumuc.item(i).value);
				var dmuc = parseFloat(denmuc.item(i).value);
				
				var chuan =  parseFloat(giatrichuan.item(i).value);
				var datduoc = parseFloat(diemdat.item(i).value);
				
				
				if( pheptoan == '<')
				{
					if( datduoc < tmuc )
					{
						trangthai.item(i).value = "Đạt";
						dat.item(i).value = "1";
						
					}
					else
					{
						trangthai.item(i).value = "Không đạt";
						dat.item(i).value = "0";
					}
				}
				
				if( pheptoan == '<=')
				{
					if( datduoc <= tmuc )
					{
						trangthai.item(i).value = "Đạt";
						dat.item(i).value = "1";
						
					}
					else
					{
						trangthai.item(i).value = "Không đạt";
						dat.item(i).value = "0";
					}
				}
				
				if( pheptoan == '>')
				{
					if( datduoc > dmuc )
					{
						trangthai.item(i).value = "Đạt";
						dat.item(i).value = "1";
						
					}
					else
					{
						trangthai.item(i).value = "Không đạt";
						dat.item(i).value = "0";
					}
				}
				
				if( pheptoan == '>=')
				{
					if( datduoc >= dmuc )
					{
						trangthai.item(i).value = "Đạt";
						dat.item(i).value = "1";
						
					}
					else
					{
						trangthai.item(i).value = "Không đạt";
						dat.item(i).value = "0";
					}
				}
				
				if( pheptoan == '=')
				{
					 
					if(giatrichuan.item(i).value.indexOf("-")>0){
						if( tmuc <= datduoc && datduoc <= dmuc )
						{
							trangthai.item(i).value = "Đạt";
							dat.item(i).value = "1";
						}
						else
						{
							trangthai.item(i).value = "Không đạt";
							dat.item(i).value = "0";
						}
					}else{
						if( tmuc  == datduoc )
						{
							trangthai.item(i).value = "Đạt";
							dat.item(i).value = "1";
						}
						else
						{
							trangthai.item(i).value = "Không đạt";
							dat.item(i).value = "0";
						}
					}
				}
				
			}
			else
			{
				trangthai.item(i).value = "";
				dat.item(i).value = "0";
				tuychon.innerHTML = "";
			}
		}
		
		
		//check tong dat
		var flag = true;
		for(i = 0; i < trangthai.length; i++)
		{
			if(dat.item(i).value != '1')
			{
				flag = false;
			}
		}
		
		
			
		if(flag == true)
		{
			document.getElementById("totalDat").style.display = "none";;
		}
		else
		{
			document.getElementById("totalDat").style.display = "";;
		}
		
		//setTimeout(replaces, 300);
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
	function convert(stt)
	{
		var trangthai = document.getElementsByName("trangthai");
		var dat = document.getElementsByName("dat");
		
		if(dat.item(stt).value == '0' )
		{
			trangthai.item(stt).value = 'Đạt';
			dat.item(stt).value = '1';
			document.getElementById("tuychon" + stt).innerHTML = " ";
		}
		else
		{
			trangthai.item(stt).value = 'Không đạt';
			dat.item(stt).value = '0';
			document.getElementById("tuychon" + stt).innerHTML = "<a href='javascript:convert(" + stt + ")' > Đạt </a>";
		}
		
		//check tong dat
		var flag = true;
		for(i = 0; i < trangthai.length; i++)
		{
			if(dat.item(i).value != '1')
			{
				flag = false;
			}
		}
		
		if(flag == true)
		{
			document.getElementById("totalDat").style.display = "none";;
		}
		else
		{
			document.getElementById("totalDat").style.display = "";;
		}
	}
	
	function DatChatLuong()
	{
		
		//var kq=
		var dat = document.getElementsByName("diemdat");
		//check tong dat
		var flag = true;
		for(i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
		
		if(flag == false)
		{
			alert('Có tiêu chí chưa nhập kết quả kiểm tra. Vui lòng kiểm tra lại.');
			return;
		}
	  document.forms["khtt"].action.value = "duyet";
	  document.forms["khtt"].submit(); 		
	}
	
	function duyetDat()
	{
		if(!confirm('Bạn có muốn duyệt  yêu cầu kiểm định  này?')) {return false ;}
		 document.forms["khtt"].datcl.value="1"; 
		 document.forms["khtt"].action.value = "duyet";
		 document.forms["khtt"].submit(); 
	}
	function duyetKDat()
	{
		if(!confirm('Bạn có muốn duyệt  yêu cầu kiểm định  này?')) {return false ;}
		 document.forms["khtt"].datcl.value="0"; 
		 document.forms["khtt"].action.value = "duyet";
		 document.forms["khtt"].submit(); 
	}
	
	function changeYeuCau()
	{
		 document.forms["khtt"].action.value = "change";
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
	
	function changeDat(pos)
	{
		var dat = document.getElementsByName("diemdat");
		for(var i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
		
	}
	
	
	function save()
	{
		var dat = document.getElementsByName("diemdat");
		
		//check tong dat
		var flag = true;
		for(i = 0; i < dat.length; i++)
		{
			if(dat.item(i).value == '')
			{
				flag = false;
			}
		}
 
	    document.forms["khtt"].action.value = "save";
	    document.forms["khtt"].submit(); 
    }
	
	function TinhSoLuong()
	{
		
 
		
		
		var soluongkiemdinh = document.getElementById("soluongkiemdinh");
		 
		
		var soluongkodat = document.getElementById("soluongkodat"); 
		var soluongmau=document.getElementById("soluongmau"); 
		
		var  soluongdat= document.getElementById("soluongdat");
		
		
		
		if(soluongkodat.value != '')
		{
			soluongdat.value = parseFloat(soluongkiemdinh.value) - parseFloat(soluongkodat.value) - parseFloat(soluongmau.value);
		}
		else
		{
			soluongkodat.value = soluongkiemdinh.value;
		}
		
		if( (parseFloat(soluongkiemdinh.value) - parseFloat(soluongkodat.value) - parseFloat(soluongmau.value) ) <0){
			soluongkodat.value="0";
			soluongmau.value="0";
			soluongdat.value = parseFloat(soluongkiemdinh.value);
			alert("Vui lòng kiểm tra lại số lượng không đạt và mẫu,vượt quá số lượng duyệt");
		} 
	}
	
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="replaces()">
	<form name="khtt" id="khtt" method="post" action=" ">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value="0">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Sản xuất > Kiểm định chất lượng > Hiển thị</TD>
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
										<TD width="30" align="left"><A
											href="../../ErpKiemdinhchatluongSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
									    <TD width="30" align="left" ><A href="../../ErpKiemdinhchatluongPdfSvl?userId=<%=userId%>&print=<%=obj.getYcId() %>" ><img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A></TD>						    
									   	<TD width="30" align="left" ><A href="../../ErpKiemdinhchatluongExcelSvl?userId=<%=userId%>&excel=<%=obj.getYcId() %>&muahang=0" ><img src="../images/excel30.gif" alt="Excel"  title="Excel"  border ="1px" longdesc="Excel" style="border-style:outset"></A></TD>
						    			<TD width="30" align="left"></TD>
							    		<TD align="left" >&nbsp;</TD>
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
									<textarea name="dataerror" style="width: 100%; color: #F00; font-weight: bold" style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin yêu cầu kiểm định </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Số chứng từ</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="id" value="<%=obj.getYcId()%>" readonly="readonly"></TD>
											<TD class="plainlabel" valign="middle" width="150px"></TD>
											<TD class="plainlabel" valign="middle"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Ngày kiểm</TD>
											<TD class="plainlabel" valign="middle"><input type="text" class="days" name="ngaykiem" value="<%=obj.getNgayKiem()%>"
												readonly="readonly"></TD>
											<TD class="plainlabel" valign="middle" width="150px">Ký hiệu mẫu kiểm định</TD>
											<TD class="plainlabel" valign="middle">
											<input type="text" name="kyhieukd" value="<%=obj.getKyhieukd()%>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Mã lệnh sản xuất</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="lenhsanxuatId" value="<%=obj.getLenhsanxuatId()%>"
												readonly="readonly"> <input type="hidden" name="congdoanId" value="<%=obj.getCongdoanId()%>" readonly="readonly"> 
												<input type="hidden" name="dinhluong" value="<%=obj.getDinhluong()%>" readonly="readonly">
												<input type="hidden" name="dinhtinh" value="<%=obj.getDinhtinh()%>" readonly="readonly">
												<input type="hidden" name="nhapkhoId" value="<%=obj.getNhapkhoId()%>" readonly="readonly">
												<input type="hidden" name="tinhtrang" value="<%=obj.getTrangThai()%>" readonly="readonly">
												</TD>
												<TD class="plainlabel" valign="middle" width="150px">Thông tin BOM</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="thongtinbom" value="<%=obj.getThongTinBom()%>" readonly="readonly"
												style="width: 100%"> </TD>
										
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="spTen" value="<%=obj.getSpTen()%>" readonly="readonly"
												style="width: 100%"> <input type="hidden" name="spId" value="<%=obj.getSpId()%>" readonly="readonly"></TD>
											<TD class="plainlabel" valign="middle" width="150px">Ngày sản xuất</TD>
											<TD class="plainlabel" valign="middle"><input type="text" class="days" name="ngaysanxuat" value="<%=obj.getNgaySanXuat()%>"></TD>
										</TR>
									 <TR>
											<TD class="plainlabel" valign="middle" width="150px">Ngày nhận hàng</TD>
											<TD class="plainlabel" valign="middle"><input type="text" class="days" name="ngaynhanhang" value="<%=obj.getNgayNhanHang()%>"></TD>
											<TD class="plainlabel" valign="middle"></TD>
											<TD class="plainlabel" valign="middle"></TD>
											<%-- <TD class="plainlabel" valign="middle" width="150px">Ngày sản xuất</TD>
											<TD class="plainlabel" valign="middle"><input type="text" class="days" name="ngaysanxuat" value="<%=obj.getNgaySanXuat()%>"></TD>
											 --%>
										</TR>
									 
										<TR>
											<TD class="plainlabel" valign="middle">Số lô</TD>
											<TD class="plainlabel" valign="middle"><select name="kiemdinhId" onchange="changeYeuCau()" id="kiemdinhId">
													<option></option>
													<%
														if (rsKiemDinh != null)
															{
																while (rsKiemDinh.next())
																{
																	if (obj.getYcId().equals(rsKiemDinh.getString("Pk_Seq")))
																	{
													%>
													<option selected="selected" value="<%=rsKiemDinh.getString("Pk_Seq")%>">
														<%=rsKiemDinh.getString("KiemDinh")%>
													</option>
													<%
														} else
																	{
													%>
													<option value="<%=rsKiemDinh.getString("Pk_Seq")%>">
														<%=rsKiemDinh.getString("KiemDinh")%>
													</option>
													<%
														}
																}
															}
													%>
											</select> <input type="hidden" name="solo" value="<%=obj.getSolo()%>" readonly="readonly" style="width: 400px"></TD>
											<TD class="plainlabel" valign="middle">Đơn vị tính </TD>
											<TD class="plainlabel" valign="middle">
											
											 <select id="Dvdlid" name="Dvdlid">
											<%
											if(rsdvdl!=null){
				           	 					while (rsdvdl.next()){ %>
				           	 						<% if( rsdvdl.getString("pk_Seq").equals(obj.getDvdlid()) ) { %>
				           	 							<option selected="selected" value="<%=rsdvdl.getString("pk_Seq")%>" > <%=rsdvdl.getString("diengiai")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=rsdvdl.getString("pk_Seq")%>" >  <%=rsdvdl.getString("diengiai")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 					rsdvdl.close();
				           	 					} 
											 %>
											 </select>
											 </TD>
											 </tr>
											 <tr>
											 
											<TD class="plainlabel" valign="middle">Số lượng kiểm định</TD>
											<TD class="plainlabel" valign="middle"> 
											<input type="text" name="soluongkiemdinh" id="soluongkiemdinh" value="<%=obj.getSoLuongKiemDinh()%>" readonly="readonly"></TD>
											
											 <TD class="plainlabel" valign="middle">Số lượng không đạt  </TD>
											<td class="plainlabel" >
												<input type="text" name="soluongkodat" id="soluongkodat" style="text-align: right;" onkeypress="return keypress(event)" onkeyup="TinhSoLuong();"  value="<%=obj.getsoluongkhongdat()%>">
											</TD>
											
											
											</TR>
											<tr>
											<TD class="plainlabel" valign="middle">Số lượng mẫu tiêu hao </TD>
											<TD class="plainlabel" valign="middle"> 
											<input type="text" name="soluongmau"  id="soluongmau"     onkeyup="TinhSoLuong();" value="<%=obj.getsoluongmau()%>" > 
											<TD class="plainlabel" valign="middle">Số lượng đạt</TD>
											<TD class="plainlabel" valign="middle"> 
											<input type="text" name="soluongdat" id="soluongdat" value="<%=obj.getsoluongDat()%>"> 
											
										</TR>
										<tr>
											<TD class="plainlabel" valign="middle">Chuyển đi cấp đông  
												<%if(obj.getIsCapDong().equals("1")){ %>
											 		<input type="checkbox" name="iscapdong"  id="iscapdong"  checked="checked"     value="1" >  
											 	<%}else{ %>
													 <input type="checkbox" name="iscapdong"  id="iscapdong"       value="1" >   
											 	<%} %>
											 	</TD>
											<TD class="plainlabel" valign="middle" colspan="3"> 
											
											 </TD>
											
											</TR>
																				
									<%String tooltips=
									"<div class=\"tooltip\">"+
										"<table style=\"margin:0\">"+
									  		"<tr>"+
									   			"<td class=\"label\">Người duyệt</td>"+
									    		"<td>'"+obj.getNguoiduyet()+"'</td>"+
									  		"</tr>"+
											"</table>"+
									"</div>";
									%>										
 
									
										<TR>
											<TD colspan="6">
												<ul class="tabs">
													<%
														if (obj.getDinhluong().equals("1"))
															{
													%>
													<li><a href="#" class="legendtitle">Định lượng</a></li>
													<%
														}
															if (obj.getDinhtinh().equals("1"))
															{
													%>
													<li><a href="#" class="legendtitle">Định tính</a></li>
													<%
														}
													%>
												</ul>
												<div class="panes">
												<%if(obj.getDinhluong().equals("1")){ %>
													<div>
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
															<TR style="font-size: 8pt">
															
																<TH width="30%"></TH>
																<TH width="10%"></TH>
																<TH width="15%"></TH>
																<TH width="15%"></TH>
																<TH width="15%"></TH>
																<TH width="15%">Chọn tất cả <input type="checkbox" id="allDinhLuong" value=1 onclick="checkedAll(document.khtt.quyetdinh,document.khtt.allDinhLuong);"/></TH>
															</TR>
															
															<TR class="tbheader">
															
																<TH width="30%">Tiêu chí</TH>
																<TH width="10%">So sánh</TH>
																<TH width="15%">Giá trị chuẩn</TH>
																<TH width="15%">Đo thực tế</TH>
																<TH width="15%">Kết quả</TH>
																<TH width="15%">Quyết định đạt</TH>
															</TR>
															<%
																for (int i = 0; i < tckdList.size(); i++)
																{
																	ITieuchikiemdinh tckd = tckdList.get(i);
																	
																	String[] gtc = tckd.getGiatrichuan().split("-");
																	String tumuc = "0";
																	String denmuc = "0";
																	if(gtc.length > 0) {
																		tumuc = gtc[0].trim();
																	}
																	if(gtc.length > 1) {
																		denmuc = gtc[1].trim();
																	}
															%>
															<TR>
																<Td><input type="text" name="tieuchi" value="<%=tckd.getTieuchi()%>" style="width: 100%" readonly="readonly"></Td>
																<Td><input type="text" name="toantu" value="<%=tckd.getToantu()%>" style="width: 100%; text-align: center;"
																	readonly="readonly"></Td>
																<Td><input type="text" name="giatrichuan" value="<%=tckd.getGiatrichuan()%>" style="width: 100%; text-align: right;" readonly="readonly">
																	<input type="hidden" name="tumuc" value="<%=tumuc %>" style="width: 100%; text-align: right;" readonly>
																	<input type="hidden" name="denmuc" value="<%=denmuc %>" style="width: 100%; text-align: right;" readonly>
																</Td>
																<Td><input type="text" name="diemdat" value="<%=tckd.getDiemdat()%>" style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);" onkeyup="replaces();"></Td>
																<Td><input type="text" name="trangthai" value="<%=tckd.getTrangthai()%>" style="width: 100%; text-align: right;"
																	readonly="readonly"> <input type="hidden" name="dat" value="<%=tckd.getDat()%>" style="width: 100%"
																	onkeypress="return keypress(event);"></Td>
																<Td align="center"  onMouseover="ddrivetip('<%= tckd.getNguoiSua() %>', 300)"; onMouseout="hideddrivetip()" >
																	<%
																		if (tckd.getQuyetdinh().equals("1"))
																				{
																	%> <input type="checkbox" id="quyetdinh" name="quyetdinh_<%=i%>" checked="checked" title="<%=tckd.getNguoiSua()%>"/> <%
 																} else{ %> <input type="checkbox" id="quyetdinh" name="quyetdinh_<%=i%>" title="<%=tckd.getNguoiSua()%>"/> <%}
 																%> <input type="hidden" name="nguoisua" value="<%=tckd.getNguoiSua()%>"  />
 																
 																
 																
																</Td>
															</TR>
															<%
																}
															%>
															<%
																if (tckdList.size() > 0)
																	{
															%>
															<Tr>
																<td colspan="7" class="tbheader">&nbsp;</td>
															</Tr>
															<%
																}
															%>
														</TABLE>
													</div>
													<%} %>
													<!-- End Div dinh luong -->
													<%if(obj.getDinhtinh().equals("1")) {%>
													<div>
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
														<TR style="font-size: 8pt">
															
																<TH width="40%"></TH>
																<TH width="45%"></TH>
																<TH width="15%">Chọn tất cả <input type="checkbox" id="allDinhTinh" value=1 onclick="checkedAll(document.khtt.ketqua_dinhtinh,document.khtt.allDinhTinh);"/></TH>
															</TR>
															<TR class="tbheader">
																<TH width="40%">Tiêu chí</TH>
																<TH width="45%">Ghi nhận thực tế</TH>
																<TH width="15%">Quyết định đạt</TH>
															</TR>
															<%
															
																for (int i = 0; obj.getTieuchi_dinhtinh()!=null&&i <obj.getTieuchi_dinhtinh().length; i++)
																	{
																		String tieuchi= obj.getTieuchi_dinhtinh()[i];
																		String ghinhan=obj.getGhinhan_dinhtinh()[i];
																		String ketqua=obj.getKetqua_dinhtinh()[i];
																		String nguoisua=obj.getNguoiSua_dinhtinh()[i];
															%>
															<TR>
																<Td><input type="text" name="tieuchi_dinhtinh" value="<%=tieuchi %>" style="width: 100%" readonly="readonly"></Td>
																<Td><input type="text" name="ghinhan_dinhtinh" value="<%=ghinhan %>" style="width: 100%" ></Td>
																<Td align="center"  onMouseover="ddrivetip('<%=nguoisua %>', 300)"; onMouseout="hideddrivetip()" >
																<%
																	if (ketqua.equals("1"))
																			{
																%> <input type="checkbox" id="ketqua_dinhtinh" name="ketqua_dinhtinh_<%=i%>" checked="checked" value="1"  title="<%=nguoisua%>"> <%
																			} else
																		{
																%> <input type="checkbox" id="ketqua_dinhtinh" name="ketqua_dinhtinh_<%=i%>" value="1" title="<%=nguoisua %>"  > <%}
 																%> <input type="hidden" name="nguoisua_dinhtinh" value="<%=nguoisua%>"> 
																</Td>
															</TR>
															<%
																}
															%>
														</TABLE>
													</div>
													<%} %>
													<!-- END DIV DINH TINH -->
												</div>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Kết luận</TD>
											<TD class="plainlabel" valign="middle" colspan="5">
												<input type="text" name="denghixuly" value="<%= obj.getDeNghiXuLy() %>" style="width: 100%">
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
	</BODY>
</HTML>
<%
	try
		{
			if (rsKiemDinh != null)
				rsKiemDinh.close();
			if (tckdList != null)
				tckdList.clear();
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			if (obj != null)
			{
				obj.DbClose();
				obj = null;
			}
			session.removeAttribute("dcsxBean");
		}
	}
%>