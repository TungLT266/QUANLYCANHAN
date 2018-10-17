<%@page import="java.text.DecimalFormat"%>
<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpHoso"%>
<%@page import="geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpHoso"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.*"%>
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
// them thong tin san pham
	//IThongtinsanphamgiay spBean = (IThongtinsanphamgiay) session.getAttribute("spBean");
	IErpKiemdinhchatluong_NhGiay obj = (IErpKiemdinhchatluong_NhGiay) session.getAttribute("kdcl");
	List<ITieuchikiemdinh> tckdList = (List<ITieuchikiemdinh>) obj.getTieuchikiemdinhList();
	List<IErpHoso> hosolist=obj.getListHoso();
	ResultSet rsKiemDinh = (ResultSet) obj.getRsYeuCauKiemDinh();
	// resultset san pham hoa chat
	ResultSet rssphc = (ResultSet) obj.getRshoachatsp();
	//
	// resultset may moc hoa chat
	ResultSet rssmm = (ResultSet) obj.getRsmaymocsp();
	List<IErpSanphamduyet>  listSpDuyet=obj.getListSanPhamDuyet();
	// resultSet cho thùng nào được lấy
	ResultSet rsSoThung = (ResultSet) obj.getRsSoThung();
	
	DecimalFormat formatter_3le = new DecimalFormat("###,###,###.###");
	DecimalFormat formatter_4le = new DecimalFormat("###,###,###.####");
	
	//IThongtinsanphamgiay spBean = (IThongtinsanphamgiay) session.getAttribute("spBean");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script type="text/javascript" src="../scripts/ajax.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
  
  
 
  <script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
	
     
<script>

$(function() {
 	$("ul.tabs").tabs("div.panes > div");
});
</script>


<style>
	.tablesolo th{
		font-size: 10pt;
	}
</style>
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
 							 
					  
							if( datduoc >= tmuc && datduoc <= dmuc) 
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
			else
			{
				trangthai.item(i).value = "";
				dat.item(i).value = "0";
				//tuychon.innerHTML = "";
			}
		}
		
		 
		
		
		//setTimeout(replaces, 300);
	}
	
	function checkedAll(chk,allquyen) {
		for(i=0; i<chk.length; i++){
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
		
		 
		 
	}
	
	function DatChatLuong()
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
		
		if(flag == false)
		{
			alert('Có tiêu chí chưa nhập kết quả kiểm tra. Vui lòng kiểm tra lại.');
			returnToPreviousPage();
			return false;
		}
		
	  	document.forms["khtt"].action.value = "duyet";
	  	document.forms["khtt"].submit(); 		
	}
	
	function duyetDat()
	{
		
		/*var listLayMauThung = document.getElementsByName("listLayMauThung");
		var tong = 0;
		for(var i =0; i< listLayMauThung.length; i++){
			tong +=  parseFloat(listLayMauThung.item(i).value);
		}
		
		if(tong == 0){
			alert("Vui lòng nhập số lượng lấy mẫu vào mới được duyệt phiếu kiểm định này");
			returnToPreviousPage();
			return false;	
		} */
		
		if(!confirm('Bạn có muốn duyệt yêu cầu kiểm định  này?'))
		{	returnToPreviousPage();
			return false ;
		}
		
	 	document.forms["khtt"].datcl.value = "1"; 
		document.forms["khtt"].action.value = "duyet";
		document.forms["khtt"].submit(); 
	}
	
	function AutoCreate(element){
		 var arr = element.id.split("_");
		 var index = arr[1];
		 var me = document.getElementsByName(""+ element.name);
		 for(var i=index; i< me.length; i++){
			 me.item(i).value = element.value;
		 }
	}
	
	function duyetKDat()
	{
		if(!confirm('Bạn có muốn duyệt  yêu cầu kiểm định  này?')) 
		{
			returnToPreviousPage();
			return false ;
		}
		
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
 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
			{
 
				return;
			}
			return false;	
		}
			
	}	
	function autoCheck()
	{
		TinhSoLuong();
	}
	
	function TinhSoLuong()
	{
		
		var tongsoluongnhap=document.getElementById("tongsoluongnhap");
		var soluongchuakiem=document.getElementById("soluongchuakiem");
		var soluongkiemdinh = document.getElementById("soluongkiemdinh");
		if( parseFloat(soluongchuakiem.value.replace(/,/g,"")) <parseFloat(soluongkiemdinh.value.replace(/,/g,"")) ){
			soluongkiemdinh.value="0";
			alert("Số lượng còn lại không đủ để kiểm định");
		}
			
		var soluongkodat = document.getElementById("soluongkodat"); 
		var soluongmau=document.getElementById("soluongmau"); 
		var  soluongdat= document.getElementById("soluongdat");
		tongsoluongnhap.value = parseFloat(tongsoluongnhap.value.replace(/,/g,""));
		soluongchuakiem.value = parseFloat(soluongchuakiem.value.replace(/,/g,""));
		soluongkiemdinh.value = parseFloat(soluongkiemdinh.value.replace(/,/g,""));
		soluongdat.value = parseFloat(soluongdat.value.replace(/,/g,""));
		soluongmau.value= parseFloat(soluongmau.value.replace(/,/g,""));
		soluongkodat.value = parseFloat(soluongkiemdinh.value.replace(/,/g,"")) - parseFloat(soluongdat.value.replace(/,/g,"")) - parseFloat(soluongmau.value.replace(/,/g,""));
		
		
		if( (parseFloat(soluongkiemdinh.value.replace(/,/g,"")) - parseFloat(soluongkodat.value.replace(/,/g,"")) - parseFloat(soluongmau.value.replace(/,/g,"")) ) <0){
			soluongkodat.value="0";
			soluongmau.value="0";
			alert("Vui lòng kiểm tra lại số lượng không đạt và mẫu,vượt quá số lượng duyệt");
			return;
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
		
		/* if(flag == false)
		{
			alert('Có tiêu chí chưa nhập kết quả kiểm tra. Vui lòng kiểm tra lại.');
			return;
		} */
		
	    document.forms["khtt"].action.value = "save";
	    document.forms["khtt"].submit(); 
    }
	
	
	function	reloadtieuchi(){
		  	document.forms["khtt"].action.value = "reloadtieuchi";
		    document.forms["khtt"].submit(); 
	}
	
    function onChangeTest(changeVal) {
      /* alert("Value is " + changeVal.value);
      alert("Value is " + changeVal.id);
      alert("Value is " + changeVal.name);
      alert("Value is " + changeVal.name.index); */
    }
 
	function ThayDoiSoLuongLayMau(){
		var listSoThung = document.getElementsByName("listSoThung");
		var listSoLuongThung = document.getElementsByName("listSoLuongThung");
		var listLayMauThung = document.getElementsByName("listLayMauThung");
		var listDatThung = document.getElementsByName("listDatThung");
		var listKhongDatThung = document.getElementsByName("listKhongDatThung");
		var tongDat =0;
		var tongKoDat = 0;
		var tongLayMau = 0; 
		var i;
		for( i = 0 ; i<listSoThung.length; i++){
			if(parseFloat(listSoLuongThung[i].value.replace(/,/g,"")) < parseFloat(listLayMauThung[i].value.replace(/,/g,""))){
				alert("Nhập lại số lượng lấy mẫu ");
			}
			else{
				listDatThung[i].value = parseFloat( listSoLuongThung[i].value.replace(/,/g,"")) -parseFloat( listLayMauThung[i].value.replace(/,/g,"")) - parseFloat( listKhongDatThung[i].value.replace(/,/g,"")) ;
			}
			tongDat  = parseFloat(tongDat) + parseFloat(listDatThung[i].value.replace(/,/g,""));
			tongKoDat = parseFloat(tongKoDat) + parseFloat(listKhongDatThung[i].value.replace(/,/g,""));
			tongLayMau = parseFloat(tongLayMau) + parseFloat(listLayMauThung[i].value.replace(/,/g,""));
		}
		document.getElementById("soluongdat").value = tongDat;
		document.getElementById("soluongkodat").value = tongKoDat;
		document.getElementById("soluongmau").value = tongLayMau;
	}
	function ThayDoiSoLuongDat(){
		var listSoThung = document.getElementsByName("listSoThung");
		var listSoLuongThung = document.getElementsByName("listSoLuongThung");
		var listLayMauThung = document.getElementsByName("listLayMauThung");
		var listDatThung = document.getElementsByName("listDatThung");
		var listKhongDatThung = document.getElementsByName("listKhongDatThung");
		var tongDat =0;
		var tongKoDat = 0;
		var tongLayMau = 0; 
		var i ;
		for(i = 0 ; i<listSoThung.length;i++){
			if(parseFloat(listDatThung[i].value.replace(/,/g,"")) > (parseFloat(listSoLuongThung[i].value.replace(/,/g,"")) - parseFloat(listLayMauThung[i].value.replace(/,/g,"")))){
				alert("Nhập lại số lượng đạt");
			}
			else{
				listKhongDatThung[i].value = parseFloat( listSoLuongThung[i].value.replace(/,/g,"")) - parseFloat(listLayMauThung[i].value.replace(/,/g,"")) -parseFloat( listDatThung[i].value.replace(/,/g,""));
			}
			tongDat  = parseFloat(tongDat) + parseFloat(listDatThung[i].value.replace(/,/g,""));
			tongKoDat = parseFloat(tongKoDat) + parseFloat(listKhongDatThung[i].value.replace(/,/g,""));
			tongLayMau = parseFloat(tongLayMau) + parseFloat(listLayMauThung[i].value.replace(/,/g,""));
		}
		
		document.getElementById("soluongdat").value = tongDat;
		document.getElementById("soluongkodat").value = tongKoDat;
		document.getElementById("soluongmau").value = tongLayMau;
	}
	
	
</SCRIPT>

<!-- <script type="text/javascript">
		
		$(document).ready(function() {
			
			// restrict surname input
			$('#maphieu').keypress(function(key) {
				if((key.charCode < 97 || key.charCode > 122) && 
						(key.charCode < 65 || key.charCode > 90) && 
						(key.charCode != 32) && (key.charCode != 8)
						(key.charCode < 48 || key.charCode > 57)
						) return false;
			});
			
		});
	
</script> -->
	
<script language="javascript" type="text/javascript">
 	function goBack(){
 		window.history.back();
 	}
 	</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="autoCheck()" >
	<form name="khtt" method="post" action="../../ErpKiemdinhchatluongUpdate_NhGiaySvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="loaiMH" value ="<%=obj.getloaimuahang() %>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;
										
										 <% if( obj.getloaimuahang().equals("0")) {%>
                            
				                            Quản lý mua hàng > Mua hàng nhập khẩu > Kiểm định chất lượng  > Cập nhật
				                            
				                            <% }else { %>
				                             Quản lý mua hàng > Mua hàng trong nước > Kiểm định chất lượng  > Cập nhật
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
										<TD width="30" align="left"><A
											href="javascript:goBack()"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left">
										</TD>
										<%
											if (!obj.getTrangThai().equals("2"))
												{
										%>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<%
											}
										%>
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
									<textarea name="dataerror" style="width: 100% ; color:#F00 ; background-color: white ; font-weight:bold;"
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
											<TD class="plainlabel" valign="middle" width="150px">Mã phiếu</TD>
											<TD class="plainlabel" valign="middle"  >
												<input type="text" name="maphieu"  id="maphieu" value="<%= obj.getMaPhieu() %>">
											</TD>

											<TD class="plainlabel" valign="middle" width="150px">Số hóa đơn:</TD>
											<TD class="plainlabel" valign="middle"  >
												<input type="text" name="maphieu" value="<%= obj.getSohoadon() %>" readonly="readonly">
											</TD>
											
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Số chứng từ</TD>
											<TD class="plainlabel" valign="middle" >
												<input type="text" name="id" value="<%= obj.getYcId() %>" readonly="readonly">
											</TD>
											<TD class="plainlabel" valign="middle">Số nhận hàng</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="nhanhangId" value="<%=obj.getNhanhangId() %>" readonly="readonly">
												<input type="hidden" name="dinhluong" value="<%=obj.getDinhluong()%>" readonly="readonly">
												<input type="hidden" name="dinhtinh" value="<%=obj.getDinhtinh()%>" readonly="readonly">
												<input type="hidden" name="tinhtrang" value="<%=obj.getTrangThai()%>" readonly="readonly">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" >Ngày kiểm</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" class="days" name="ngaykiem" value="<%= obj.getNgayKiem() %>" readonly="readonly">
											</TD>
											<TD class="plainlabel" valign="middle" >Ký hiệu mẫu kiểm định</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="kyhieumaukd" id="kyhieumaukd" value="<%= obj.getKyhieukd() %>" >
											</TD>
										</TR>
										<TR>
											
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><input type="text" name="spTen" value="<%=obj.getSpTen()%>" readonly="readonly"
												> <input type="hidden" name="spId" value="<%=obj.getSpId()%>" readonly="readonly"></TD>
											<TD class="plainlabel" valign="middle" >Nhà cung cấp /Khách hàng</TD>
											<TD class="plainlabel" valign="middle" >
												<input type="hidden" name="nccId" value="<%=obj.getNccId() %>"/>
												<input type="text" class="text" name="nhacungcap" value="<%= obj.getNhaCungCap() %>" readonly="readonly">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Số lô</TD>
											<TD class="plainlabel" valign="middle" >
											<select name="kiemdinhId" id="kiemdinhId">
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
														<%= rsKiemDinh.getString("solo")%>
													</option>
													<%
														} else	{
													%>
													<option value="<%=rsKiemDinh.getString("Pk_Seq")%>">
														<%=rsKiemDinh.getString("KiemDinh")%>
													</option>
													<% } } } %>
											</select> 
											<input type="hidden" name="solo" value="<%=obj.getSolo()%>" readonly="readonly" style="width: 400px"></TD>
											
											<TD class="plainlabel" valign="middle" >Ngày nhận hàng</TD>
											<TD class="plainlabel" valign="middle" >
												<input type="text" class="days" name="ngaynhanhang" value="<%= obj.getNgayNhanHang() %>" readonly="readonly">
											</TD>
										</TR>
										
										
											<tr>
											<TD class="plainlabel" valign="middle">Tổng số lượng nhập </TD>
											<TD class="plainlabel"   valign="middle">
												<input type="text" name="tongsoluongnhap" id="tongsoluongnhap" style="text-align: right;" readonly="readonly" value="<%= obj.getTongsoluongnhap() %>"> 
											</TD>
											<TD class="plainlabel" valign="middle"> Số lượng chưa kiểm  </TD>
											<td class="plainlabel" >
											<input type="text" name="soluongchuakiem" id="soluongchuakiem" style="text-align: right;" readonly="readonly" value="<%= obj.getsoluongchuakiem() %>">
											</TD>
											
										</tr>


										<tr>
											<TD class="plainlabel" valign="middle">Số lượng kiểm</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="soluongkiemdinh" id="soluongkiemdinh"
												style="text-align: right;" onkeyup="TinhSoLuong();"
												value="<%= obj.getSoLuongKiemDinh() %>"></TD>
											<TD class="plainlabel" valign="middle">Số lượng lấy mẫu
											</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="soluongmau" id="soluongmau"
												style="text-align: right;"
												onkeypress="return keypress(event)" onkeyup="TinhSoLuong();" readonly
												value="<%= obj.getSoLuongmau() %>">
											</TD>

										</tr>

										<tr>
											
											 <TD class="plainlabel" valign="middle">Số lượng không đạt  </TD>
											<td class="plainlabel" >
												<input type="text" name="soluongkodat" id="soluongkodat" style="text-align: right;" onkeypress="return keypress(event)" onkeyup="TinhSoLuong();"  value="0">
											</TD>
													<TD class="plainlabel" valign="middle">Số lượng đạt</TD>
											<td class="plainlabel" >
												<input type="text" name="soluongdat" id="soluongdat" style="text-align: right;" onkeypress="return keypress(event)" value="<%=obj.getsoluongDat()%>">
											 	</TD>
											
										</tr>
										
										<tr>
											<TD class="plainlabel" valign="middle">Ngày sản xuất  </TD>
											<td class="plainlabel" >
												<input type="text" class="days" id="ngaysanxuat" name="ngaysanxuat" value="<%= obj.getNgaySanXuat() %>" maxlength="10" readonly="readonly">
											</TD>
											<TD class="plainlabel" valign="middle">Ngày hết hạn</TD>
												<td class="plainlabel" >
												<input type="text" class="days" id="ngayhethan" name="ngayhethan" value="<%= obj.getNgayhethan() %>" maxlength="10" readonly="readonly">
											</TD>
										</tr>
							
											
										<tr>
											 <TD class="plainlabel" valign="middle"> Thiếu hồ sơ  </TD>
											<td class="plainlabel" >
												<%if(obj.getThieuhoso().equals("1")) {%>
												<input type="checkbox" name="thieuhoso" checked="checked" id="thieuhoso" style="text-align: right;"     value="1">
												<%}else{ %>
													<input type="checkbox" name="thieuhoso" id="thieuhoso" style="text-align: right;"     value="1">
												<%} %>
											</TD>
											<TD class="plainlabel" valign="middle">
											 
											<a href="" id="tonkhotp" rel="subcontent_tonkhotp">
          	 							    <img alt="BOM" src="../images/vitriluu.png"></a>
  	 										<DIV id="subcontent_tonkhotp" style="position:absolute;z-index:0;  visibility: hidden; border: 5px solid #80CB9B;
		                                     background-color: white; width:500px; padding: 2px;">
			                   				 <table border="1" style="padding:0" align="center" width="100%">
			                       				<tr align="center">		                                
		                               			 <th width="100%">Hồ sơ</th>
		                       			  	 </tr>
						                      	<%
						                       		if(hosolist != null){
						                       			 
						                       				for(int i=0;i<hosolist.size();i++){  
						                       				IErpHoso hoso=hosolist.get(i);
						                       				
						                       				%>
						                       				<tr> 
						    		                      		<td><input name="hoso"  type="text"   value = "<%=hoso.getHoso()%>" style="width: 100%"></td>
						    			                      
								                       		</tr> 
						                       				<%} 
						                       		 	 
						                       		}
							                     %>
				                      
							                     <%
						                       		 
					                       				for(int i=0;i<5 ;i++){  
					                       				%>
					                       				<tr> 
					    		                      		<td><input type="text" value = "" name="hoso" style="width: 100%"></td>
					    			                      
							                       		</tr> 
					                       				<%} 
			 
							                     %>
        	
					                   
			                   				 </table>		                    
					                     <div align="right"> 
					                      
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_tonkhotp')"  ">Hoàn tất</a>
					                     </div>
					                     </DIV>
													 </TD>
											<td class="plainlabel" >
											
											
											<a href="" id="listtpnhan" rel="subcontent_listtpnhan">
          	 							    <img alt="BOM" src="../images/vitriluu.png"> List sản phẩm đã duyệt </a>
  	 										<DIV id="subcontent_listtpnhan" style="position:absolute;z-index:0;  visibility: hidden; border: 5px solid #80CB9B;
		                                     background-color: white; width:700px; padding: 1px;">
			                   				 <table border="1" style="padding:0" align="center" width="100%">
		                       				   <tr align="center">		                                
	                               			   <th width="5%">Lần duyệt</th>
	                               			   <th width="20%">Ngày duyệt</th>
	                               			   <th width="20%">Người duyệt</th>
	                               			   <th width="10%">Số lượng duyệt</th>
	                               			   <th width="10%">Số lượng đạt</th>
	                               			   <th width="10%">Số lượng mẫu</th>
	                               			   <th width="10%">Số lượng hỏng</th>
		                       			  	 </tr>
						                      	<%
						                       		if(listSpDuyet != null){
						                       			 
						                       				for(int i=0;i<listSpDuyet.size();i++){  
						                       				IErpSanphamduyet sp=listSpDuyet.get(i);
						                       				
						                       				%>
						                       				<tr> 
						    		                      		
						    			                       <td width="20%"><input  type="text"   value = "<%=sp.getLanDuyet()%>" style="width: 100%"></td>
					                               			   <td width="20%"><input  type="text"   value = "<%=sp.getNgayDuyet()%>" style="width: 100%"></td>
					                               			   <td width="20%"><input   type="text"   value = "<%=sp.getNguoiDuyet()%>" style="width: 100%"></td>
					                               			   <td width="10%"><input type="text"   value = "<%=sp.getSoluongduyet()%>" style="width: 100%"></td>
					                               			   <td width="10%"><input   type="text"   value = "<%=sp.getSoluongdat()%>" style="width: 100%"></td>
					                               			   <td width="10%"><input  type="text"   value = "<%=sp.getSoluongmau()%>" style="width: 100%"></td>
					                               			   <td width="10%"><input   type="text"   value = "<%=sp.getSoluonghong()%>" style="width: 100%"></td>
		                               			   
								                       		</tr> 
						                       				<%} 
						                       		 	 
						                       		}
							                     %>
				                      
							                   
        	
					                   
			                   				 </table>		                    
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_listtpnhan')"  	>Hoàn tất</a>
					                     </div>
					                     </DIV>
									</TD>
											 		 	
										</tr>
										
										<TR>
											<td colspan="4" >
										<table  border="0" width="90%" cellpadding="0" cellspacing="1">
		                       				   <tr class="tbheader">		                                
		                               			   <th width="10%">Mã thùng</th>
		                               			   <th width="15%">Số lượng</th>
		                               			   <th width="15%">Lấy mẫu</th>
		                               			   <th width="15%">Số lượng đạt</th>
		                               			   <th width="15%">Số lượng hỏng</th>
		                               			   <th width="15%">Hàm ẩm</th>
		                               			   <th width="15%">Hàm lượng</th>
		                       			  		</tr>
						                      	<%
						                      		int j=0;
													if(rsSoThung!=null){
					                       				while(rsSoThung.next()){  
					                       					%>
												<tr>
													<td>
														<input type="text"  readonly value="<%=rsSoThung.getString("MATHUNG")%>" name="listSoThung"
														style="width: 100%" >
													</td>
													<td>
														<input type="text"  readonly value="<%=formatter_4le.format(rsSoThung.getDouble("SOLUONG"))%>" name="listSoLuongThung"
														style="width: 100%">
													</td>
													<td>
														<input type="text"   value="<%=formatter_4le.format(rsSoThung.getDouble("SOLUONGMAU"))%>" name="listLayMauThung"
														style="width: 100%" onchange= "ThayDoiSoLuongLayMau();">
													</td>
													<td>
														<input type="text"   value="<%=formatter_4le.format(rsSoThung.getDouble("SOLUONGDAT"))%>" name="listDatThung"
														style="width: 100%" onchange= "ThayDoiSoLuongDat();">
													</td>
													<td>
														<input type="text"  readonly value="<%=formatter_4le.format(rsSoThung.getDouble("SOLUONGKHONGDAT"))%>" name="listKhongDatThung"
														style="width: 100%" onchange= "onChangeTest(this);" >
													</td>
													<td>
														<input type="text"   value="<%=rsSoThung.getString("HAMAM")%>" name="listHamAm"
														style="width: 100%" id="<%="listHamAm_"+j %>" onchange ="AutoCreate(this);" >
													</td>
													<td>
														<input type="text"   value="<%=rsSoThung.getString("HAMLUONG")%>" name="listHamLuong"
														style="width: 100%" id="<%="listHamLuong_"+j %>" onchange ="AutoCreate(this);" >
													</td>
															

												</tr>
															<% j++;} rsSoThung.close();}%>
			                   				 </table>
										</td>
										</TR>
										<%
										System.out.println("trangthai : "+obj.getTrangThai());
										if (obj.getTrangThai().trim().equals("0"))
										{
										%>
										
										<TR id="totalDat">
											<TD class="plainlabel" colspan="2">
												<a class="button2" href="javascript:duyetDat();"> 
													<img style="top: -4px;" src="../images/button.png" alt="">Duyệt kiểm định
												</a>&nbsp;&nbsp;&nbsp;&nbsp; 
											</TD>
											<TD class="plainlabel" valign="middle">
												<input type="hidden" name="datcl" value="<%=obj.getDatCl()%>">
											</TD>
											<TD class="plainlabel" valign="middle"></TD>
										</TR>
										<%}%>
										
										<%-- <TR>
											<TD colspan="6">
												<ul class="tabs">
												
												<!-- them tab vao day -->
												
												
													<% if (obj.getDinhluong().equals("1")) { %>
														<li><a href="#" class="legendtitle">Định lượng</a></li>
														
													<% } if (obj.getDinhtinh().equals("1")) { %>
														<li><a href="#" class="legendtitle">Định tính</a></li> 
														<%} if(obj.getDinhluong().equals("1") || obj.getDinhtinh().equals("1")){%>
														<li><a href="#" class="legendtitle">Hóa chất kiểm định</a></li>
														<li><a href="#" class="legendtitle">Máy móc kiểm định</a></li>
													<% } %>
												</ul>
												<div class="panes">
												<%if(obj.getDinhluong().equals("1")){ %>
													<div>
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
															<TR class="tbheader">
																<TH width="30%">Tiêu chí</TH>
																<TH width="10%">So sánh</TH>
																<TH width="15%">Giá trị chuẩn</TH>
																<TH width="15%">Đo thực tế</TH>
																<TH width="15%">Kết quả</TH>
																<TH width="20%">Quyết định đạt <input type="checkbox" id="allDinhLuong" value=1 onclick="checkedAll(document.getElementsByClassName('dinhluong'),document.khtt.allDinhLuong);"/></TH>
															</TR>
															<%
																for (int i = 0; i < tckdList.size(); i++)
																{
																	ITieuchikiemdinh tckd = tckdList.get(i);
																	String tumuc = "0";
																	String denmuc = "0";
																	 
																	// dối vói những đấu :, hoặc dấu ÷
																	if(tckd.getGiatrichuan().trim().contains("÷") ){
																		String[] gtc = tckd.getGiatrichuan().split("÷");
																		
																		if(gtc.length > 0) {
																			tumuc = gtc[0].trim();
																		}
																		if(gtc.length > 1) {
																			denmuc = gtc[1].trim();
																		}else{
																			denmuc=tumuc = gtc[0].trim();
																		}
																	}else if (tckd.getGiatrichuan().trim().contains(":"))  {
																		
																		String[] gtc = tckd.getGiatrichuan().split(":");
																		if(gtc.length > 0) {
																			tumuc = gtc[0].trim();
																		}
																		if(gtc.length > 1) {
																			denmuc = gtc[1].trim();
																		}else{
																			denmuc=tumuc = gtc[0].trim();
																		}
																		
																	}else if (tckd.getGiatrichuan().trim().contains("-"))  {
																		int index=tckd.getGiatrichuan().trim().indexOf("-",1);
																		int leng=tckd.getGiatrichuan().trim().length();
																		if(index > 0){
																			System.out.println("index: "+index);
																			tumuc = tckd.getGiatrichuan().trim().substring(0,index);
																			System.out.println("tumuc: "+tumuc);
																			denmuc=tckd.getGiatrichuan().trim().substring(index+1,leng);
																			System.out.println("denmuc: "+denmuc);
																		}else{
																			tumuc=tckd.getGiatrichuan();
																			denmuc=tckd.getGiatrichuan();
																		}
																	}else{
																		tumuc=tckd.getGiatrichuan();
																		denmuc=tckd.getGiatrichuan();
																	}
																		
																	 
															%>
															<TR>
																<Td><input type="text" name="tieuchi" value="<%=tckd.getTieuchi()%>" style="width: 100%" readonly="readonly"></Td>
																<Td><input type="text" name="toantu" value="<%=tckd.getToantu()%>" style="width: 100%; text-align: center;" readonly="readonly"></Td>
																<Td>
																	<input type="text" name="giatrichuan" value="<%=tckd.getGiatrichuan()%>" style="width: 100%; text-align: right;" readonly="readonly">
																	<input type="hidden" name="tumuc" value="<%=tumuc %>" style="width: 100%; text-align: right;" readonly>
																	<input type="hidden" name="denmuc" value="<%=denmuc %>" style="width: 100%; text-align: right;" readonly>
																</Td>
																<Td><input type="text" name="diemdat" value="<%=tckd.getDiemdat()%>" style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);"  onkeyup="replaces();" ></Td>
																	
																<Td><input type="text" name="trangthai" value="<%=tckd.getTrangthai()%>" style="width: 100%; text-align: right;"
																	readonly="readonly"> <input type="hidden" name="dat" value="<%=tckd.getDat()%>" style="width: 100%"
																	onkeypress="return keypress(event);"></Td>
																<Td align="center">
																	<% if (tckd.getQuyetdinh().equals("1")) { %> 
																		<input type="checkbox" id="quyetdinh" name="quyetdinh_<%=i%>" value="1" checked="checked" class="dinhluong" /> 
																	<% } else{ %> 
																		<input type="checkbox" id="quyetdinh" name="quyetdinh_<%=i%>" value="1" class="dinhluong" /> 
																	<% } %> 
																	<input type="hidden" name="nguoisua" value="<%= tckd.getNguoiSua()%>" />
																</Td>
															</TR>
																<% } %>
															
														</TABLE>
													</div>
													<%} %>
													<!-- End Div dinh luong -->
													<%if(obj.getDinhtinh().equals("1")) {%>
													<div>
														<TABLE border="0" width="70%" cellpadding="0" cellspacing="1" style=" margin-left:120px;">
															<TR class="tbheader">
																<TH width="40%">Tiêu chí</TH>
																<TH width="45%">Ghi nhận thực tế</TH>
																<TH width="15%">Quyết định đạt <input type="checkbox" id="allDinhTinh" value=1 onclick="checkedAll(document.getElementsByClassName('dinhtinh'),document.khtt.allDinhTinh);"/></TH>
															</TR>
															<%
															
																for (int i = 0;  i < obj.getTieuchi_dinhtinh().length; i++)
																	{
																		String tieuchi= obj.getTieuchi_dinhtinh()[i];
																		String ghinhan=obj.getGhinhan_dinhtinh()[i];
																		String ketqua=obj.getKetqua_dinhtinh()[i];
																		String nguoisua=obj.getNguoiSua_dinhtinh()[i];
															%>
															<TR>
																<Td><input type="text" name="tieuchi_dinhtinh" value="<%=tieuchi %>" style="width: 100%" readonly="readonly"></Td>
																<Td><input type="text" name="ghinhan_dinhtinh" value="<%=ghinhan %>" style="width: 100%" ></Td>
																<Td align="center">
																<% if (ketqua.equals("1")) { %> 
																	<input type="checkbox" id="ketqua_dinhtinh" name="ketqua_dinhtinh_<%=i %>" checked="checked" value="1" class="dinhtinh" > 
																<% } else { %> 
																	<input type="checkbox" id="ketqua_dinhtinh" name="ketqua_dinhtinh_<%=i %>" value="1" class="dinhtinh" > 
																<%} %> 
																	<input type="hidden" name="nguoisua_dinhtinh" value="<%=nguoisua%>"> 
																</Td>
															</TR>
															<% } %>
														</TABLE>
													</div>
													<%} %>
													<!-- END DIV DINH TINH -->
													<!-- star DIV hoa chat kiem dinh-->
													<!-- HÓA CHẤT KIỂM ĐỊNH -->
																<% if(obj.getDinhluong().equals("1")) { %>
																<div id="tab_7" class="tabContents" style="display:none" >
																	<p>Hóa chất kiểm định</p>
																	<TABLE border="0" style="width: 80%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR class="tbheader" >
																			<TH width="10%">Số thứ tự</TH>
																			<TH width="20%">Mã hóa chất</TH>
																			<TH width="40%">Tên hóa chất</TH>
																			<TH width="10%">Số lượng chuẩn</TH>
																			<TH width="10%">Số lượng</TH>
																			<TH width="15%">Đơn vị</TH>
																		</TR>
																			<%
																				int countDT = 0;
																				while(rssphc.next())
																				{
																			%>
																			<TR>
																				<Td>
																					<input type="text"  value="<%= ++countDT %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																					<input type="hidden"  name="PK_SEQHC" style="width: 100%" value=""  > 
																				</Td>
																				<Td>
																					<input type="text"  name="maHoaChat" style="width: 100%" value="<%=rssphc.getString("HOACHAT") %>"  readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="text"  name="tenHoaChat" style="width: 100%" value="<%=rssphc.getString("tenhc") %>" readonly="readonly" > 
																				</Td>
																					<Td>
																					<input type="text"  name="soluongchuan" style="width: 100%" value="<%=rssphc.getString("soluongchuan") %>" readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="text"  name="soluong" style="width: 100%" value="<%=rssphc.getString("soluong") %>" readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="text"  name="donvi" style="width: 100%" value="<%=rssphc.getString("tendv") %>" readonly="readonly"> 
																				</Td>
																																													
																			</TR>
																			
																		<% } %>
																	<% } %>	
																	</TABLE>
																</div>
																<!-- HÓA CHẤT KIỂM ĐỊNH -->
																
																<!-- MÁY MÓC KIỂM ĐỊNH -->
																<% if(obj.getDinhluong().equals("1")) { %>
																<div id="tab_8" class="tabContents" style="display:none" >
																	<p>Máy móc kiểm định</p>
																	<TABLE border="0" style="width: 80%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR class="tbheader" >
																			<TH width="6%">Số thứ tự</TH>
																			<TH width="30%">Mã máy móc</TH>
																			<TH width="64%">Tên máy móc</TH>
																			
																		</TR>
																		 
																		
																		<% 
																		int countDT = 0;
																			while(rssmm.next())
																			{
																			%>
																	
																			<TR>
																				<Td>
																					<input type="text"  value="<%= ++countDT %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																				</Td>
																				<Td>
																					<input type="text"  name="maMayMoc" style="width: 100%" value="<%=rssmm.getString("ma") %>" readonly="readonly"  > 
																				</Td>
																				<Td>
																					<input type="text"  name="tenMayMoc" style="width: 100%" value="<%=rssmm.getString("ten") %>" readonly="readonly" > 
																				</Td>
																																																
																			</TR>
																			
																		<% } %>
																	<% } %>	
																	</TABLE>
																</div>
																<!--MÁY MÓC KIỂM ĐỊNH -->
													
												
												</div>
											</TD>
										</TR> --%>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Kết luận</TD>
											<TD class="plainlabel"  colspan="5">
												<textarea  name="denghixuly" style="width: 100%; text-align:left; background-color:white; "  rows="2" ><%=obj.getDeNghiXuLy()%></textarea>
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
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<script type="text/javascript">
		dropdowncontent.init('tonkhotp', "right", 400, "click");
		dropdowncontent.init('listtpnhan', "left", 700, "click");
		dropdowncontent.init('sololaymau', "left", 400, "click");
		ThayDoiSoLuongLayMau();
</script>
</BODY>
</HTML>
<%
		try
		{
			obj.DbClose();

			tckdList.clear();
			obj = null;
		} catch (Exception er)
		{
			er.printStackTrace();
		}
	}
%>
