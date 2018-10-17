<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.cttrungbay.*" %>
<%@ page  import = "geso.traphaco.center.beans.cttrungbay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% ICttrungbay cttbBean = (ICttrungbay)session.getAttribute("cttbBean"); %>
<% List<INhomsptrungbay> nsptbList = cttbBean.getNsptbList(); %>
<% 
	ResultSet tratbRs = cttbBean.getTratbRs(); 

	String[] diengiaiMuc = (String[])cttbBean.getDiengiaiMuc();
	String[] tumuc = (String[])cttbBean.getTumuc();
	String[] denmuc = (String[])cttbBean.getDenmuc();
	String[] thuongSR = (String[])cttbBean.getThuongSR();
	String[] thuongTDSR = (String[])cttbBean.getThuongTDSR();
	String[] thuongSS = (String[])cttbBean.getThuongSS();
	String[] thuongTDSS = (String[])cttbBean.getThuongTDSS();
	String[] thuongASM = (String[])cttbBean.getThuongASM();
	String[] thuongTDASM = (String[])cttbBean.getThuongTDASM();
	
%>

<% ResultSet kbhRs = cttbBean.getKbhRs(); %>
<% ResultSet vungRs = cttbBean.getVungRs(); %>
<% ResultSet kvRs = cttbBean.getKhuvucRs(); %>
<% ResultSet hangkhRS = (ResultSet)cttbBean.getHangkhRs(); %>
<% ResultSet vitrikhRS = (ResultSet)cttbBean.getVitrikhRs(); %>
<% 
	ResultSet nppRs = cttbBean.getNppRs();
	ResultSet nhomTbRs = cttbBean.getNhomTbRs();

%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
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
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/cttrungbayList.js"></script>
    <script type="text/javascript">
    

    function clickTinhDoanhSo() {
    	
    	/* var ckType = document.getElementById("ckType");
    	if(ckType.checked) {
    		$("#ngay1").val($("#ngayKtTds").val());
    		$("#ngay2").val($("#ngayKtTb").val());
    	} else {
    		$("#ngay1").val("");
    		$("#ngay2").val("");
    	} */
    	
    	
//    	$("#ngayKtTds")
    }
    
    $(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		})});
    
    
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
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
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: <%= cttbBean.getActive() %> });
	  });
  </script>
  
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
	<!-- <script src="../scripts/colorBox/jquery.min.js"></script> -->
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".button1").colorbox({width:"35%", inline:true, href:"#nhomsptrungbay"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Best - Project.");
                return false;
            });
            
            $(".button2").colorbox({width:"35%", inline:true, href:"#tratrungbay"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Best - Project.");
                return false;
            });
        });
    </script>
    <script language="javascript" type="text/javascript">
		function replaces()
		{
			var ngayKtTds = document.getElementsByName("ngayKtTds");
			var ngayKtTb = document.getElementsByName("ngayKtTb");
			var ngayTb = document.getElementsByName("ngayTb");
			var ngay1 =	document.getElementsByName("ngay1");
			var ngay2 =	document.getElementsByName("ngay2");
		    var type = document.getElementById("ckType");
			
		    //phai chay bang vong lap vay moi duoc
		    if(type.checked)
	    	{
			    for(var h = 0; h < ngay1.length; h++)
			    {
			    	ngay1.item(h).value = ngayKtTds.item(h).value;
			    	if(type.checked == true)
					 	ngay2.item(0).value = ngayKtTb.item(0).value;
					else
						ngay2.item(0).value  = ngayTb.item(0).value;
			    }
	    	}
		    
			var nsptbId = document.getElementsByName("nsptbId");
			var nsptbDiengiai = document.getElementsByName("nsptbDiengiai");
			var nsptbTongluong = document.getElementsByName("nsptbTongluong");
			var nsptbTongtien = document.getElementsByName("nsptbTongtien");
	
			var i;
			for(i=0; i < nsptbId.length; i++)
			{
				if(nsptbId.item(i).value != "")
				{
					var nsptb = nsptbId.item(i).value;
					var pos = parseInt(nsptb.indexOf(" - "));
					if(pos > 0)
					{
						nsptbId.item(i).value = nsptb.substring(0, pos);
						nsptb = nsptb.substr(parseInt(nsptb.indexOf(" - ")) + 3);
						nsptbDiengiai.item(i).value = nsptb.substring(0, parseInt(nsptb.indexOf(" [")));
						nsptb = nsptb.substr(parseInt(nsptb.indexOf(" [")) + 2);
						nsptbTongluong.item(i).value = nsptb.substring(0, parseInt(nsptb.indexOf("]")));
						nsptb = nsptb.substr(parseInt(nsptb.indexOf("] [")) + 3);
						nsptbTongtien.item(i).value = nsptb.substring(0, parseInt(nsptb.indexOf("]")));
					}
				}
				else
				{
					nsptbId.item(i).value = "";
					nsptbDiengiai.item(i).value = "";
					nsptbTongluong.item(i).value = "";
					nsptbTongtien.item(i).value = "";
				}			
			}
			
			setTimeout(replaces, 20);
		}
		
		function All()
		 { 
			var npp = document.getElementsByName("nppIds");
			for(i=0; i < npp.length; i++)
			{
			 	if(document.cttbForm.all.checked == true)
			 	{
			 	  	npp[i].checked = true;
				}
			 	else
			 	{
					npp[i].checked = false;
				}
			}
		}
		
		function saveform()
		{
			checkNhomsptrung();
			
			if(document.getElementById("scheme").value == "")
			{
				alert('Vui lòng nhập scheme');
				return;
			}
			
			if(document.getElementById("ngayTds").value == "")
			{
				alert('Vui lòng nhập thời gian bắt đầu tính doanh số');
				return;
			}
			if(document.getElementById("ngayKtTds").value == "")
			{
				alert('Vùi lòng nhập thời gian bắt đầu tính doanh số');
				return;
			}
			if(document.getElementById("ngayTb").value == "")
			{
				alert('Vui lòng nhập thời gian bắt đầu trưng bày');
				return;
			}
			if(document.getElementById("ngayKtTb").value == "")
			{
				alert('Vùi lòng nhập thời gian kết thúc trưng bày');
				return;
			}
			if(document.getElementById("solantt").value == "")
			{
				alert('Vui lòng nhập số lần thanh toán');
				return;
			}
			if(document.getElementById("ngansach").value == "")
			{
				alert('Vui lòng nhập ngân sách chương trình trưng bày');
				return;
			}
			
			if(checkNhomsptrungbay() == false)
			{
				alert('Không có nhóm sản phẩm trưng bày nào được chọn..');
				return;
			}
			if(checkTratb() == false)
			{
				alert('Lỗi...không có hình thức trả trưng bày nào được chọn...');
				return;
			}
			
			var ngayTds = Date.parse(document.getElementById("ngayTds").value);
			var ngayKtTds = Date.parse(document.getElementById("ngayKtTds").value);
			if(ngayTds > ngayKtTds)
			{
				alert('Lỗi...bạn phải chọn ngày kết thúc > ngày bắt đầu của ngày tính doanh số ...');
				return;
			}
			
			var ngayTb = Date.parse(document.getElementById("ngayTb").value);
			var ngayKtTds = Date.parse(document.getElementById("ngayKtTb").value);
			if(ngayTb > ngayKtTds)
			{
				alert('Lỗi...bạn phải chọn ngày kết thúc > ngày bắt đầu của ngày trưng bày ...');
				return;
			}
			
			if(checkNpp() == false)
			{
				alert('Loi...khong co nha phan phoi nao duoc chon...');
				return;
			}
			
			document.forms["cttbForm"].action.value = "save";
			
			var active = $( "#accordion" ).accordion( "option", "active" );
			document.forms["cttbForm"].active.value = active;
			document.forms["cttbForm"].submit();
		}
		
		function checkNpp()
		{
			var nhapp = document.getElementsByName("nppIds");
			for( p = 0; p < nhapp.length; p++)
				if(nhapp.item(p).checked)
					return true;
			return false;
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
		
		function submitform()
		{  
			//truyen nhu nay moi duoc....
			document.forms["cttbForm"].ttbTungay.value = document.getElementById("ttb_tungay").value;
			document.forms["cttbForm"].ttbDenngay.value = document.getElementById("ttb_denngay").value;
			document.forms["cttbForm"].ttbDiengiai.value = document.getElementById("ttb_diengiai").value;
			
			document.forms["cttbForm"].nsptbTungay.value = document.getElementById("nsptb_tungay").value;
			document.forms["cttbForm"].nsptbDenngay.value = document.getElementById("nsptb_denngay").value;
			document.forms["cttbForm"].nsptbDien_giai.value = document.getElementById("nsptb_diengiai").value;
			
			var active = $( "#accordion" ).accordion( "option", "active" );
			document.forms["cttbForm"].active.value = active;
			
		    document.forms["cttbForm"].submit();
		}
		
		function checkNhomsptrungbay()
		{
			var nsptbIds = document.getElementsByName("nsptbId");
			
			for(j = 0; j < nsptbIds.length; j++)
			{
				if(nsptbIds.item(j).value != "")
					return true; //co chon dieu kien
			}
			return false;
		}
		
		function checkNhomsptrung()
		{
			var nsptbIds = document.getElementsByName("nsptbId");
			var nsptbDiengiai = document.getElementsByName("nsptbDiengiai");
			var nsptbTongluong = document.getElementsByName("nsptbTongluong");
			var nsptbTongtien = document.getElementsByName("nsptbTongtien");
			
			for(l =0; l < parseInt(nsptbIds.length) - 1; l++)
			{
				for(m = parseInt(l) + 1; m < nsptbIds.length; m++)
				{
					if(nsptbIds.item(l).value == nsptbIds.item(m).value)
					{
						nsptbIds.item(m).value = "";
						nsptbDiengiai.item(m).value = "";
						nsptbTongluong.item(m).value = "";
						nsptbTongtien.item(m).value = "";
					}
				}
			}
		}
		
		function checkTratb()
		{
			var tratbIds = document.getElementsByName("tratbIds");
			for(k =0; k < tratbIds.length; k++)
			{
				if(tratbIds.item(k).checked)
					return true; //co chon tra khuyen mai
			}
			return false;
		}
		
	</script>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nhomtbId").select2();
});
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cttbForm" method="post" action="../../CttrungbayUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="ttbTungay" value=''>
<input type="hidden" name="ttbDenngay" value=''>
<input type="hidden" name="ttbDiengiai" value=''>
<input type="hidden" name="nsptbTungay" value=''>
<input type="hidden" name="nsptbDenngay" value=''>
<input type="hidden" name="nsptbDien_giai" value=''>
<input type="hidden" name="active" value='<%= cttbBean.getActive() %>'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý trưng bày > Khai báo > Chương trình trưng bày > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng ban<%= userTen %> &nbsp; &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" readonly="readonly" rows="1"><%= cttbBean.getMessage() %></textarea>
		         <% cttbBean.setMessage(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle">Khai báo chương trình trưng bày</legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">	
                
                  <TR>
                        <TD class="plainlabel" >Nhóm  CT Trưng bày </TD>
                        <TD class="plainlabel" colspan=3>
                            <select name="nhomtbId" id="nhomtbId"  style="width:350px" >
					        	<option value=""></option>
		                        <% if(nhomTbRs != null) {
		                         while(nhomTbRs.next()) 
		                         {
		                           if(cttbBean.getNhomTbId().indexOf(nhomTbRs.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= nhomTbRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= nhomTbRs.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= nhomTbRs.getString("pk_seq") %>" style="padding: 2px"><%= nhomTbRs.getString("ten") %></option>
		                         <%} }}%>        	
		                    </select>
                        </TD>
                    </TR>
                
                							
                    <TR>
                        <TD width="15%" class="plainlabel">Mã CTTB </TD>
                        <TD class="plainlabel" colspan=3><input type="text" name="scheme" id="scheme" size="30" value="<%= cttbBean.getScheme() %>"></TD>
                    </TR> 
                    <TR>
                        <TD width="15%" class="plainlabel" valign="top">Diễn giải </TD>
                        <TD class="plainlabel" valign="top" colspan=3><textarea name="diengiai" id="diengiai" style="width:500px" rows="1"><%= cttbBean.getDiengiai() %></textarea></TD>
                    </TR>               
                    <TR>
                        <TD class="plainlabel" >Thời gian tính doanh số</TD>
                        <TD class="plainlabel" colspan=3>
                            Từ ngày&nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="ngayTds" name="ngayTds" value="<%= cttbBean.getNgayTds() %>" maxlength="10"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngayKtTds" name="ngayKtTds" value="<%= cttbBean.getNgayktTds() %>" maxlength="10"/>
                        </TD>
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" >Thời gian trưng bày</TD>
                        <TD class="plainlabel" colspan=3>
                           Từ ngày &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="ngayTb" name="ngayTb" value="<%= cttbBean.getNgayTb() %>" maxlength="10"/>
                       &nbsp;&nbsp;&nbsp;&nbsp;Đến ngày &nbsp;&nbsp; <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngayKtTb" name="ngayKtTb" value="<%= cttbBean.getNgayktTb() %>" maxlength="10"/>
                       
                        </TD>
                    </TR>
                 
                    <TR>
                        <TD class="plainlabel" >Thời gian đăng ký</TD>
                        <TD class="plainlabel" colspan=3>
                        <% if(cttbBean.getType().equals("2")){ %>
                            <input type="checkbox" value="2" name="type" id ="ckType" checked onclick="clickTinhDoanhSo();"/>Từ ngày kết thúc tính doanh số đến ngày kết thúc trưng bày
                        <%}else{ %>
                        	<input type="checkbox" value="2" name="type" id ="ckType" onclick="clickTinhDoanhSo();"/>Từ ngày kết thúc tính doanh số đến ngày kết thúc trưng bày
                        <%} %> 
                        </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" ></TD>
                        <TD class="plainlabel" colspan=3>
                           Từ ngày &nbsp;&nbsp; <input type="text" size="10" name="ngay1" id ="ngay1" readonly value ="<%= cttbBean.getNgayBddk() %>" class="days">
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="10" id ="ngay2" name="ngay2" readonly value="<%= cttbBean.getNgayKtdk() %>" class="days">
                         </TD>
                    </TR>

                    <TR>
                        <TD class="plainlabel" >Số lần thanh toán </TD>
                        <TD class="plainlabel" colspan=3><input type="text" name="solantt" id="solantt" size="30" style="text-align:right" value="<%= cttbBean.getSolantt() %>" onkeypress="return keypress(event);"></TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Ngân sách</TD>
                        <TD class="plainlabel" colspan=3><input type="text" name="ngansach" id="ngansach" size="30" style="text-align:right" value="<%= cttbBean.getNgansach() %>" onkeypress="return keypress(event);"></TD>
                    </TR>	
                     <TR>
                        <TD class="plainlabel" >Đã sử dụng</TD>
                        <TD class="plainlabel" colspan=3><input type="text" name="dasudung" id="dasudung" size="30" style="text-align:right" value="<%= cttbBean.getDasudung() %>" readonly ></TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Thưởng đơn hàng đầu tiên</TD>
                        <TD class="plainlabel" colspan=3>
                        	<input type="text" name="thuongdhDauTien" id="thuongdhDauTien" style="text-align:right" value="<%= cttbBean.getThuongDhDautien() %>" >
                        	&nbsp;&nbsp;&nbsp;
                        	<select name="donviThuong" style="width: 200px;" >
								<% if(cttbBean.getDonvithuong().equals("0")) { %>
									<option value="0" selected="selected" > % Chiết khấu</option>
								<% } else { %> 
									<option value="0"  > % Chiết khấu</option>
								<% } %>
								
								<% if(cttbBean.getDonvithuong().equals("1")) { %>
									<option value="1" selected="selected" > VNĐ </option>
								<% } else { %> 
									<option value="1"  > VNĐ </option>
								<% } %>					
							</select>
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >&nbsp;</TD>
                        <TD class="plainlabel" colspan=3>
                        	<% if(cttbBean.isTinhdoanhso().equals("1")){ %>
                        		<input type="checkbox" name = "isTinhds" id="isTinhds" value="1" checked="checked"><i> Đăng ký khi phát sinh doanh số </i> 
                        	<% } else { %>
                        		<input type="checkbox" name = "isTinhds" id="isTinhds" value="0"><i> Đăng ký khi phát sinh doanh số </i> 
                         	<% } %>
                        </TD>
                    </TR>	
                    <tr>
                    <TD width="120px;" class="plainlabel" >Hạng khách hàng </TD>
                        <TD width="220px;" class="plainlabel"  >
                            <select name="hangkhId" id="hangkhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(hangkhRS != null) {
		                         while(hangkhRS.next()) 
		                         {
		                           if(cttbBean.getHangkhIds().indexOf(hangkhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= hangkhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= hangkhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= hangkhRS.getString("pk_seq") %>" style="padding: 2px"><%= hangkhRS.getString("ten") %></option>
		                         <%} } hangkhRS.close(); }%>        	
		                    </select>
		                 </TD>
		                 <TD width="120px;" class="plainlabel" >Vị trí khách hàng </TD>
                        <TD class="plainlabel"  >
                            <select name="vitrikhId" id="vitrikhId" multiple="multiple"  >
					        	<option value=""></option>
		                        <% if(vitrikhRS != null) {
		                         while(vitrikhRS.next()) 
		                         {
		                           if(cttbBean.getVitrikhIds().indexOf(vitrikhRS.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= vitrikhRS.getString("pk_seq") %>" selected style="padding: 2px" ><%= vitrikhRS.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= vitrikhRS.getString("pk_seq") %>" style="padding: 2px"><%= vitrikhRS.getString("ten") %></option>
		                         <%} } vitrikhRS.close(); }%>        	
		                    </select>
		                 </TD>
		                 </tr>				
                </TABLE>       
         </div>
         <div id="accordion"  style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
            <h1 style="font-size:1.8em"><a href="#">Điều kiện trưng bày</a></h1>
			<div style="height:auto">
                <TABLE width="100%" class="tabledetail" border="0" cellspacing="1px" cellpadding="1px">
                    <TR class="plainlabel">
                        <TH align="center" width="10%">Mã</TH>
                        <TH align="center" width="50%"> Diễn giải </TH>
                        <TH align="center" width="10%"> Tổng lượng</TH>
                        <TH align="center" width="10%">Tổng tiền</TH>
                        <TH align="center" width="10%">Phép toán</TH>
                    </TR>
					<% 
						int i = 0;
						if(nsptbList.size() > 0){ 
						while(i < nsptbList.size()){	
						Nhomsptrungbay nsptb = (Nhomsptrungbay)nsptbList.get(i); %>
		                    <TR class='tbdarkrow'>
		                        <TD align="center"><input type="text" name="nsptbId"  value="<%= nsptb.getId() %>" onkeyup="ajax_showOptions(this,'abc',event)" AUTOCOMPLETE="off"></TD>
		                        <TD align="left"><input type="text" name="nsptbDiengiai"  value="<%= nsptb.getDiengiai() %>" readonly></TD>
		                        <TD align="center"><input type="text" name="nsptbTongluong"  value="<%= nsptb.getTongluong() %>" style="text-align:right" readonly></TD>							           
		                        <TD align="center"><input type="text" name="nsptbTongtien"  value="<%= nsptb.getTongtien() %>" style="text-align:right" readonly></TD>
		                        <TD align="center">
		                        	<select name="nsptbPheptoan">
		                        	<% if(nsptb.getPheptoan().equals("2")){ %>
		                                <option value="2" selected>Or</option>
		                                <option value="1">And</option>
		                            <%}else{ %>		                            	
		                                <option value="1" selected>And</option>
		                                <option value="2">Or</option>
		                            <%}%>
		                            </select>
		                        </TD>
		                    </TR>
                    	<% i++; }} %>
                    	<% for(int j = i; j < 5; j++){ %>
                    		<TR class='tbdarkrow'>
		                        <TD align="center"><input type="text" name="nsptbId" value="" onkeyup="ajax_showOptions(this,'abc',event)" AUTOCOMPLETE="off"></TD>
		                        <TD align="left"><input type="text" name="nsptbDiengiai" value="" readonly></TD>
		                        <TD align="center"><input type="text" name="nsptbTongluong"  value="" style="text-align:right" readonly></TD>							           
		                        <TD align="center"><input type="text" name="nsptbTongtien" value="" style="text-align:right" readonly></TD>
		                        <TD align="center">
		                        	<select name="nsptbPheptoan">
		                            	<option value="1" selected="selected">And</option>
		                                <option value="2">Or</option>     
		                            </select>
		                        </TD>
		                    </TR>
                    	<%} %>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
                    </TR>
				</TABLE>
				<br><a class="button1" href="#">
                    <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm điều kiện trưng bày</a>
	                <div style='display:none'>
                        <div id='nhomsptrungbay' style='padding:0px 5px; background:#fff;'>
                        	<h4 align="left">Tìm kiếm nhóm sản phẩm trưng bày</h4>
							<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
                            	<tr>
                                	<td width="18%" valign="top" align="left">Diễn giải</td>
                                    <td valign="top" align="left"><textarea name="nsptb_diengiai" id="nsptb_diengiai" style="width:250px;" rows="2"><%= cttbBean.getNsptb_diengiai() %></textarea></td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">Từ ngày</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                  					id="nsptb_tungay" name="nsptb_tungay" value="<%= cttbBean.getNsptb_tungay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">Đến ngày</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                  					id="nsptb_denngay" name="nsptb_denngay" value="<%= cttbBean.getNsptb_denngay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left"></td>
                                    <td valign="top" align="left">
                                    <a class="button" href="javascript:submitform();">
        								<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
                                    </td>
                                </tr>
                            </table>
						</div>
	                </div>
     			</div>  
     	
         	 <h1 style="font-size:1.8em"><a href="#">Trả trưng bày</a></h1> 
             <div style="height:auto">
              <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                  <TR class="plainlabel">
                      <TH align="center">Mã</TH>
                      <TH align="left"> Diễn giải </TH>
                      <TH align="center">Tổng lượng</TH>
                      <TH align="center">Tổng tiền</TH>
                      <TH align="center">Loại</TH>
                      <TH align="center" width="10%">Phép toán</TH>
                      <TH align="center">Chọn</TH>
                  </TR>
				  <% int pos = 0; 
				  if(tratbRs != null){
						  try{ while(tratbRs.next()){  %>
			      			<TR class='tbdarkrow'>
		                      <TD align="center"><%= tratbRs.getString("tratbId") %></TD>
		                      <TD align="left"><%= tratbRs.getString("diengiai") %></TD>
		                      <TD align="center"><%= tratbRs.getString("tongluong") %></TD>										                                    
		                      <TD align="center"><%= tratbRs.getString("tongtien") %></TD>
		                      <TD align="center"><%= tratbRs.getString("loai") %></TD>
		                      <TD align="center">
		                        	<select name="tratbPheptoan">
		                        	<%
		                        	String key = tratbRs.getString("tratbId");
		                        	if(cttbBean.getTratbId().containsKey(key)) 
		                        	{
		                        		//System.out.println("Phep toan la: " + cttbBean.getTratbId().get(key) + "\n");
		                        		if(cttbBean.getTratbId().get(key) == 2)
		                        		{%>
		                        			<option value="1">And</option>
		                                	<option value="2" selected="selected">Or</option> 
		                        		<%}
		                        		else{%>
		                        			<option value="1" selected="selected">And</option>
		                                	<option value="2">Or</option> 
		                        		<%}
		                        	}
		                        	else{ %>
		                        		<option value="1" selected="selected">And</option>
		                                <option value="2">Or</option>
		                        	<%}%>
		                            </select>
		                      </TD>
		                      <TD align="center">
		                      <% if(cttbBean.getTratbId().get(key) != null ){ %>
		                      		<input type="checkbox" name="tratbIds" value="<%= tratbRs.getString("tratbId") + "," + Integer.toString(pos) %>" checked>
		                      <%}else{ %>
		                      		<input type="checkbox" name="tratbIds" value="<%= tratbRs.getString("tratbId") + "," + Integer.toString(pos) %>" >
		                      <%} %>
		                      </TD>
		                  </TR>
			     <%pos++; } tratbRs.close(); }catch(java.sql.SQLException e){} }%>

                  <TR>
                      <TD align="center" colspan="11" class="plainlabel">&nbsp;</TD>
                  </TR>
              	 </TABLE>   
                  <br><a class="button2" href="#" style="font-size:14px">
                      <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm trả trưng bày</a>
                      <div style='display:none'>
                        <div id='tratrungbay' style='padding:0px 5px; background:#fff;'>
                        	<h4 align="left">Tìm kiếm trả trưng bày</h4>
							<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
                            	<tr>
                                	<td width="20%" valign="top" align="left">Diễn giải</td>
                                    <td valign="top" align="left"><textarea name="ttb_diengiai" id="ttb_diengiai" style="width:250px" rows="1"><%= cttbBean.getTtb_diengiai() %></textarea></td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">Từ ngày</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                  					id="ttb_tungay" name="ttb_tungay" value="<%= cttbBean.getTtb_tungay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left">Đến ngày</td>
                                    <td valign="top" align="left">
                                    	<input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                  					id="ttb_denngay" name="ttb_denngay" value="<%= cttbBean.getTtb_denngay() %>" maxlength="10" />
                                    </td>
                                </tr>
                                <tr>
                                	<td valign="top" align="left"></td>
                                    <td valign="top" align="left">
                                    <a class="button" href="javascript:submitform();">
        								<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
                                    </td>
                                </tr>
                            </table>
						</div>
	                </div>
             </div>
            
            <h1 style="font-size:1.8em"><a href="#">Chi nhánh / Đối tác</a></h1>
            <div style="height:auto">
            <TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
                <TR class="plainlabel" valign="bottom">
                <th colspan="3">
                   
                   <fieldset style="width: 30%; float: left;"> 
			       <legend>Kênh bán hàng &nbsp;</legend> 
			       <select name="kbhIds" multiple="multiple">
                        <% if(kbhRs != null) {
                         while(kbhRs.next()) 
                         {
                           if(cttbBean.getKbhIds().indexOf(kbhRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= kbhRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= kbhRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=kbhRs.getString("pk_seq") %>" style="padding: 2px"><%= kbhRs.getString("ten") %></option>
                         <%}} kbhRs.close(); }%>        	
                    </select>
                    </fieldset>
                    
			       <fieldset style="width: 30%; float: left;">
			       <legend>Vùng &nbsp;</legend> 
			       <select name="vungIds" multiple="multiple">
                        <% if(vungRs != null) {
                         while(vungRs.next()) 
                         {
                           if(cttbBean.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= vungRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%= vungRs.getString("pk_seq") %>" style="padding: 2px"><%= vungRs.getString("ten") %></option>
                         <%}} vungRs.close(); }%>        	
                    </select>
                    </fieldset>
                    
                    <fieldset style="width: 30%; float: left;"> 
					<legend>Khu vực &nbsp;</legend>
					<select name="kvIds" multiple="multiple">
			            <% if(kvRs != null) {
			            	while(kvRs.next())
	                          {
	                            if(cttbBean.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
	                            { %>
	                              <option value="<%= kvRs.getString("pk_seq") %>" selected style="padding: 2px"><%= kvRs.getString("ten") %></option> 
	                          <%}else { %>
	                          	<option value="<%= kvRs.getString("pk_seq") %>" style="padding: 2px"><%= kvRs.getString("ten") %></option>
	                          <%}} kvRs.close(); }%>
                    </select>
                    </fieldset>
			   </th>
				</TR>
                <tr class="plainlabel" style="padding: 5px">
                	<th colspan="3">
                		<a class="button" href="javascript:submitform();">
        					<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị Npp theo điều kiện</a>
                	</th>
                </tr>
                    <TR class="tbheader">
                        <TH align="center" width="10%">Mã</TH>
                        <TH align="left" width="50%"> Tên </TH>
                        <TH align="center" width="10%"> Chọn tất cả <input type ="checkbox" name ="all" onclick ="All()"></TH>
                    </TR>
					<%
					int k = 0;
					while(nppRs.next()) {
					if(k % 2==0){
					%>
	                   	<TR class='tbdarkrow'>
	               <%}else{ %>
	                    <TR class='tblightrow'>
	               <%} %>
                        <TD align="center"><%= nppRs.getString("ma") %></TD>
	                    <TD align="left"><%= nppRs.getString("ten") %></TD>
	                    <% if(cttbBean.getNppIds().indexOf((nppRs.getString("pk_seq"))) >= 0 ) {%>
	                    	<TD align="center"><input type ="checkbox" name ="nppIds" value ="<%= nppRs.getString("pk_seq")%>" checked="checked"></TD>
	                    <%} else {%>
	                      	<TD align="center"><input type ="checkbox" name ="nppIds" value ="<%= nppRs.getString("pk_seq")%>"></TD>
	                  	<%} %>
                   </TR>
	                <% k++;}%>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
                    </TR>
                    </TABLE>
              </div>     
            
            
            
            <h1 style="font-size:1.8em"><a href="#">Thưởng Sale</a></h1>
            <div style="height:auto; font-size: 13px;">
            	 
				<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="20%" rowspan="2" >Diễn giải</TH>
                        <TH align="center" width="20%" colspan="2" >Phần trăm đạt</TH>
                        <td align="center" width="10%" colspan="2">Thưởng SR</td>
                    	<td align="center" width="20%" colspan="2" >Thưởng SS</td>
                    	<td align="center" width="20%" colspan="2" >Thưởng ASM</td>
                    </TR>
                    <TR class="tbheader">
                    	<TH align="center" width="10%" >Từ mức</TH>
                        <TH align="center" width="10%" >Đến mức</TH>
                        <td align="center" width="10%">T. SR</td>
                    	<td align="center" width="10%" >T. TĐ SR</td>
                    	<td align="center" width="10%" >T. SS</td>
                    	<td align="center" width="10%" >T. TĐ SS</td>
                    	<td align="center" width="10%" >T. ASM</td>
                    	<td align="center" width="10%" >T. TĐ ASM</td>
                    </TR>
                    
                    <%
                    	int count = 0;
                    	if( diengiaiMuc != null ) 
                    	{
                    		for(i = 0; i < diengiaiMuc.length; i++)
                    		{
								%>   
								
								<tr>
									<td>
										<input type="text" name="diengiaiMuc" value="<%= diengiaiMuc[i] %>" >
									</td>
									<td>
										<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongSR" value="<%= thuongSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongTDSR" value="<%= thuongTDSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongSS" value="<%= thuongSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongTDSS" value="<%= thuongTDSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongASM" value="<%= thuongASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
									<td>
										<input type="text" name="thuongTDASM" value="<%= thuongTDASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
									</td>
								</tr>
								                 			
                    		<% count++; }
                    		
                    	}
                    	
                    	for(i = count; i < 15; i++)
                   		{
                   			%>
                   			
                   			<tr>
								<td>
									<input type="text" name="diengiaiMuc" value="" >
								</td>
								<td>
									<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongTDSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongTDSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
								<td>
									<input type="text" name="thuongTDASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
								</td>
							</tr>
                   			
                   		<%  }	
                    	
                    %>
					
                    <TR>
                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
                    </TR>
				</TABLE>
							 
            </div>     
              
            
         </div>
    </fieldset>
  </div>    
</div>
</form>

<script>
replaces();
</script>


</BODY>
</html>
<%

try{
if(tratbRs!=null){
tratbRs.close();
}
if(kbhRs!=null){
	kbhRs.close();
	}
if(kbhRs!=null){
	kbhRs.close();
	}
if(kvRs!=null){
	kvRs.close();
	}
if(nppRs!=null){
	nppRs.close();
	}
cttbBean.DbClose();
}catch(Exception er){
	
}
%>
