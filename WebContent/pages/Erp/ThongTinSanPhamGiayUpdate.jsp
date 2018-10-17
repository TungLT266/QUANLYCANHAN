<%@page import="java.util.Iterator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.thongtinsanphamgiay.*"%>
<%@page import="geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP" %>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page import="geso.traphaco.center.util.*"%>
<% NumberFormat formatter=new DecimalFormat("#######.########"); %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}
	else
	{
	String loadLanDau = (String) session.getAttribute("loadlandau"); if(loadLanDau == null) loadLanDau = "1";
	
	IThongtinsanphamgiay spBean = (IThongtinsanphamgiay) session.getAttribute("spBean");

	ResultSet dvdl = (ResultSet) spBean.getDvdl();
	ResultSet dvkd = (ResultSet) spBean.getDvkd();
	ResultSet nh = (ResultSet) spBean.getNh();
	ResultSet cl = (ResultSet) spBean.getCl();
	ResultSet nspRs = (ResultSet) spBean.getNsp();
	ResultSet loaisp = (ResultSet) spBean.getLoaiSpRs();
	ResultSet packsizeRs = (ResultSet) spBean.getPacksizeRs();
	ResultSet splist = (ResultSet) spBean.getSanphamRs();
	ResultSet dangBaoCheRs = (ResultSet) spBean.getDangBaoCheRs();
	ResultSet tscdRs = (ResultSet) spBean.getTscdRs();
	String dvdlId = (String) spBean.getDvdlId();
	String dvdlTen = (String) spBean.getDvdlTen();
	String dvkdId = (String) spBean.getDvkdId();
	String nhId = (String) spBean.getNhId();
	String clId = (String) spBean.getClId();
	String[] nhacungcap = spBean.getNhacungcap();
	String[] hancongbo = spBean.getHancongbo();
	String[] hinhcongbo = spBean.getHinhcongbo();
	String[] filenamecb = spBean.getFilenamecb();
	String[] luongdattoithieu = spBean.getLuongdattoithieu();
	String[] thoihangiaohang = spBean.getThoihangiaohang();
	String[] fhinh = spBean.getFilehinhcb();
	
	List<ITieuchikiemdinh> tckdList = (List<ITieuchikiemdinh>)spBean.getTieuchikiemdinhDinhluongList();
	List<ITieuchikiemdinh> tckd_dinhtinhList = (List<ITieuchikiemdinh>)spBean.getTieuchikiemdinhDinhtinhList();
	List<IThongtinNCC> ThongtinNCCList =spBean.GetThongtinNCClist();
	List<IHoaChat_SanPham> hoaChatKiemDinhList = (List<IHoaChat_SanPham>) spBean.getHoaChatKiemDinhList();
	List<IMayMoc_SanPham> mayMocKiemDinhList = spBean.getMayMocKiemDinhList();
	String tenDonViDoLuong = "Đơn vị";
	
	List<String> toantuList = new ArrayList<String>();
	toantuList.add("=");
	toantuList.add("<");
	toantuList.add("<=");
	toantuList.add(">");
	toantuList.add(">=");
	
	ResultSet RsBTP = (ResultSet) spBean.getBTPRs();
	ResultSet RsPhepham = (ResultSet) spBean.getPhePhamRs();
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script>
<script type="text/javascript" src="../scripts/erp-loadHoaChatList.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
 
 <style type="text/css">
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

#ajax_listOfOptions div {re
	/* General rule for both .optionDiv and .optionDivSelected */ margin:1px;
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
</style>
 
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
    <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	$(document).ready(function()
			{
				$("#loaisp").select2();
				$("#dvdlId").select2();
				$("#dvkdId").select2();
				$("#nhId").select2();
				$("#clId").select2();
				$("#nhacungcap").select2();
				$("#packsize").select2();
				
				$("#loaihanghoa").select2();
				$("#dangbaoche").select2();
				
				$("#thietbicanid").select2();
				$("#thietbicankhacid").select2();
				
				$("#nhacungcap0").select2();
				$("#nhacungcap1").select2();
				$("#nhacungcap2").select2();
				$("#nhacungcap3").select2();
				$("#nhacungcap4").select2();
				$("#nhacungcap5").select2();
				$("#nhacungcap6").select2();
				$("#nhacungcap7").select2();
				$("#nhacungcap8").select2();
				$("#nhacungcap9").select2();
			});
	
	
	</script>
	<script type="text/javascript">
		$(document).ready(function() {		
				 
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

	
	<script src="../Uploadify/swfobject.js" type="text/javascript"></script>
	<script src="../Uploadify/jquery.uploadify.v2.1.4.min.js" type="text/javascript"></script>
	<link href="../Uploadify/uploadify.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

 

<SCRIPT language="javascript" type="text/javascript">
function checkMavt(mavattu)
{
	var mavt = document.getElementsByName("mavt");
	var i;
	for(i = 0; i < mavt.length ; i++)
	{
		if(mavt.item(i).value == mavattu)
			return true;
	}
	return false;
}
function replacesTSCD()
{
	//Lay ra mot mang giong nhu getParameterValue
	var idts = document.getElementsByName("PK_SEQMM");
	var mats = document.getElementsByName("maMayMoc");
	var tents = document.getElementsByName("tenMayMoc");
	var sodong =  mats.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		if(  mats.item(i).value != ""    )
		{ 
			var ts = mats.item(i).value;
			var pos = parseInt(ts.indexOf(" -- "));
			
			if(pos > 0)
			{
				mats.item(i).value = ts.substring(0, pos);
				ts = ts.substr(parseInt(ts.indexOf(" -- ")) + 3);
				tents.item(i).value = ts.substring(0, parseInt(ts.indexOf(" [")));
				ts = ts.substr(parseInt(ts.indexOf(" [")) + 2);
				idts.item(i).value = ts.substring(0, parseInt(ts.indexOf("]")));
				console.log(idts.item(i).value);
			}
			
			 
		}
		else 
		{
			mats.item(i).value="";
			idts.item(i).value = "";
			tents.item(i).value = ""; 
			 
		}
	}
		var idHoaChat = document.getElementsByName("PK_SEQHC");
		var maHoaChat = document.getElementsByName("maHoaChat");
		var tenHoaChat = document.getElementsByName("tenHoaChat");
		var dvChatKiemDinh = document.getElementsByName("dvChatKiemDinh");
		var soLuongChatKiemDinh = document.getElementsByName("soLuongChatKiemDinh");
		var soDongHoaChat =  maHoaChat.length;
		var i;
		for(i = 0; i < soDongHoaChat; i++)
		{
			if(  maHoaChat.item(i).value != ""    )
			{ 
				var hc = maHoaChat.item(i).value;
				var pos = parseInt(hc.indexOf(" -- "));
				
				if(pos > 0)
				{
					maHoaChat.item(i).value = hc.substring(0, pos);
					hc = hc.substr(parseInt(hc.indexOf(" -- ")) + 3);
					tenHoaChat.item(i).value = hc.substring(0, parseInt(hc.indexOf(" [")));
					hc = hc.substr(parseInt(hc.indexOf(" [")) + 2);
					dvChatKiemDinh.item(i).value = hc.substring(0, parseInt(hc.indexOf("] [")));
					hc = hc.substr(parseInt(hc.indexOf("] [")) + 3);
					idHoaChat.item(i).value = hc.substring(0, parseInt(hc.indexOf("]")));
					var a = idHoaChat.item(i).value;
					console.log(a);
				}
				
				 
			}
			else 
			{
				maHoaChat.item(i).value="";
				idHoaChat.item(i).value = "";
				tenHoaChat.item(i).value = ""; 
				dvChatKiemDinh.item(i).value = "";
				soLuongChatKiemDinh.item(i).value = 0; 
			}
		}
			setTimeout(replacesTSCD, 500);
}

function changeCheck()
{
	var is_khongthue = document.getElementById("is_khongthue");
	if(is_khongthue.checked){
		document.getElementById("thueSuat").value = 0;
	}
}
function changevat(i)
{
	
	document.getElementById("is_khongthue").checked = false;
}
function replacesMayMoc_NCC()
{
	 //Lay ma nha cung cap
	console.log(ncc);
	
	var list = document.getElementsByName("nhacungcap");
	var dem;
	if (list !== null) {
		for (dem = 0; dem < list.length; dem ++) {
			var ncc = list.item(dem).value;
			if (ncc !== "" ){
				var idts = document.getElementsByName("PK_SEQMM"+ncc);
				var mats = document.getElementsByName("maMayMoc"+ncc);
				var tents = document.getElementsByName("tenMayMoc"+ncc);
				var sodong =  mats.length;
				var i;
				for(i = 0; i < sodong; i++)
				{
					if(  mats.item(i).value != ""    )
					{ 
						var ts = mats.item(i).value;
						var pos = parseInt(ts.indexOf(" -- "));
						
						if(pos > 0)
						{
							mats.item(i).value = ts.substring(0, pos);
							ts = ts.substr(parseInt(ts.indexOf(" -- ")) + 3);
							tents.item(i).value = ts.substring(0, parseInt(ts.indexOf(" [")));
							ts = ts.substr(parseInt(ts.indexOf(" [")) + 2);
							idts.item(i).value = ts.substring(0, parseInt(ts.indexOf("]")));
							console.log(idts.item(i).value);
						}
						
						 
					}
					else 
					{
						mats.item(i).value="";
						idts.item(i).value = "";
						tents.item(i).value = ""; 
						 
					}
				} 
			}	
		}
	}

	setTimeout(replacesMayMoc_NCC, 500);
	
}


function replacesHoaChat_NCC()
{
	//Lay ma nha cung cap
	var list = document.getElementsByName("nhacungcap");
	var dem;
	if (list !== null) {
		for (dem = 0; dem < list.length; dem ++) {
			var ncc = list.item(dem).value;
			if (ncc !== "" ){
				var idHoaChat = document.getElementsByName("PK_SEQHC"+ncc);
				var maHoaChat = document.getElementsByName("maHoaChat"+ncc);
				var tenHoaChat = document.getElementsByName("tenHoaChat"+ncc);
				var dvChatKiemDinh = document.getElementsByName("dvChatKiemDinh"+ncc);
				var soLuongChatKiemDinh = document.getElementsByName("soLuongChatKiemDinh"+ncc);
				var soDongHoaChat =  maHoaChat.length;
				var i;
				for(i = 0; i < soDongHoaChat; i++)
				{
					if(  maHoaChat.item(i).value != ""    )
					{ 
						var hc = maHoaChat.item(i).value;
						var pos = parseInt(hc.indexOf(" -- "));
						
						if(pos > 0)
						{
							maHoaChat.item(i).value = hc.substring(0, pos);
							hc = hc.substr(parseInt(hc.indexOf(" -- ")) + 3);
							tenHoaChat.item(i).value = hc.substring(0, parseInt(hc.indexOf(" [")));
							hc = hc.substr(parseInt(hc.indexOf(" [")) + 2);
							dvChatKiemDinh.item(i).value = hc.substring(0, parseInt(hc.indexOf("] [")));
							hc = hc.substr(parseInt(hc.indexOf("] [")) + 3);
							idHoaChat.item(i).value = hc.substring(0, parseInt(hc.indexOf("]")));
						}
						
						 
					}
					else 
					{
						maHoaChat.item(i).value="";
						idHoaChat.item(i).value = "";
						tenHoaChat.item(i).value = ""; 
						dvChatKiemDinh.item(i).value = "";
						soLuongChatKiemDinh.item(i).value = 0; 
					}
				}
			
			}	
		}
	}

	setTimeout(replacesHoaChat_NCC, 500);
	
}

function replaces()
{
	var mavt = document.getElementsByName("mavt");
	var tenvt = document.getElementsByName("tenvt");
	var donvitinhvt = document.getElementsByName("dvtvt");
	var soluong = document.getElementsByName("soluong");
	var i;
	for(i=0; i < mavt.length; i++)
	{
		if(mavt.item(i).value != "")
		{
			var vt = mavt.item(i).value;
			var pos = parseInt(vt.indexOf("-"));
			//alert(pos);
			if(pos > 0)
			{
				mavt.item(i).value = vt.substring(0, pos);
				vt = vt.substr(parseInt(vt.indexOf("-")) + 3);
				tenvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" [")));
				vt = vt.substr(parseInt(vt.indexOf("[")) + 1);
				//alert(vt);
				donvitinhvt.item(i).value = vt.substring(0, parseInt(vt.indexOf(" ]")));						
			}	
			
			if(checkMavt(mavt.item(i).value) == true)
			{
				mavt.item(i).parentNode.parentNode.bgColor = "#9FC";
			}		
		}
		else
		{
			tenvt.item(i).value = "";
			donvitinhvt.item(i).value = "";		
			soluong.item(i).value = "";
		}
	}	
	setTimeout(replaces,20);
}
 function submitform()
{	document.spForm.action.value='abc';
    document.forms["spForm"].submit();
}
 
 function submitloaisp()
 {	
	 document.forms["spForm"].doiloai.value = '1';
	document.spForm.action.value='abc';
 	document.forms["spForm"].submit();
}

 function saveform()
 {
	 document.getElementById("Msg").value = "";
 	var error = document.getElementById("Msg");
 	var masp = document.getElementById("masp");
 	if (masp.value=="")
 	{
 	 	error.value = "Vui lòng nhập mã sản phẩm";
 	 	masp.focus();
 	 	return;
 	}

 	var tennoibo = document.getElementById("tennoibo");
 	if (tennoibo.value=="")
 	{
 	 	error.value = "Vui lòng nhập tên đăng ký SX của sản phẩm";
 	 	tennoibo.focus();
 	 	return;
 	}
 	var tenthuongmai = document.getElementById("tenthuongmai");
	/* if (tenthuongmai.value=="")
	{
	 	error.value = "Vui lòng nhập tên thương mại của sản phẩm";
	 	tenthuongmai.focus();
	 	return;
	} */
	
 	var tensp = document.getElementById("tensp");
 	if (tensp.value=="")
 	{
 	 	error.value = "Vui lòng nhập tên sản phẩm";
 	 	tensp.focus();
 	 	return;
 	}

 	var loaisp = document.getElementById("loaisp");
 	if (loaisp.value=="")
 	{
 	 	error.value = "Vui lòng chọn loại sản phẩm";
 	 	loaisp.focus();
 	 	return;
 	}
 	
 	/* var filename2 = document.getElementById("filename2");
 	if (filename2.value=="")
 	{
 	 	error.value = "Vui lòng upload hình ảnh sản phẩm";
 	 	document.getElementById("filename1").focus();
 	 	return;
 	} */
 	
 	var dvdlId = document.getElementById("dvdlId");
 	if (dvdlId.value=="")
 	{
 	 	error.value = "Vui lòng chọn đơn vị đo lường";
 	 	dvdlId.focus();
 	 	return;
 	}
 	
 	/* var kl = document.getElementById("kl");
 	if (kl.value=="")
 	{
 	 	error.value = "Vui lòng nhập khối lượng sản phẩm";
 	 	kl.focus();
 	 	return;
 	}
 	
 	var tt = document.getElementById("tt");
 	if (tt.value=="")
 	{
 	 	error.value = "Vui lòng nhập thể tích sản phẩm";
 	 	tt.focus();
 	 	return;
 	} */
 	
 	 var dvkdId = document.getElementById("dvkdId");
 	if (dvkdId.value=="")
 	{
 	 	error.value = "Vui lòng chọn đơn vị kinh doanh";
 	 	dvkdId.focus();
 	 	return;
 	}
 	
 	/*  var nhId = document.getElementById("nhId");
 	if (nhId.value=="")
 	{
 	 	error.value = "Vui lòng chọn nhãn hàng";
 	 	nhId.focus();
 	 	return;
 	}
 	
 	var clId = document.getElementById("clId");
 	if (clId.value=="")
 	{
 	 	error.value = "Vui lòng chọn chủng loại";
 	 	clId.focus();
 	 	return;
 	}  */
 	/* var tonkhoantoan = document.getElementById("tonkhoantoan");
 	if (tonkhoantoan.value=="")
 	{
 	 	error.value = "Vui lòng nhập tồn kho an toàn";
 	 	tonkhoantoan.focus();
 	 	return;
 	}
 	
 	var giablc = document.getElementById("giablc");
 	if (giablc.value=="")
 	{
 	 	error.value = "Vui lòng nhập giá bán lẻ chuẩn";
 	 	giablc.focus();
 	 	return;
 	} */
 	
 	/* var giamua = document.getElementById("giamua");*/
 	
 	var mucdonguyhiem = document.getElementById("mucdonguyhiem").value.trim();
	if(mucdonguyhiem != ""){
		var mucdonh = parseFloat(mucdonguyhiem);
		if(isNaN(mucdonh)){
			document.getElementById("Msg").value = "Mức độ nguy hiểm chỉ được phép nhập số nguyên từ 1 đến 6.";
			return false;
		} else if (mucdonh < 1 || mucdonh > 6){
			document.getElementById("Msg").value = "Mức độ nguy hiểm chỉ được phép nhập số nguyên từ 1 đến 6.";
			return false;
		}
	}

 	var loaispma = document.getElementById("loaispma");
 	

 	/* var packsize = document.getElementById("packsize");
 	if (packsize.value=="")
 	{
 	 	error.value = "Vui lòng chọn packsize";
 	 	packsize.focus();
 	 	return;
 	} */

 	 if(loaispma.value != "CCDC")
 	 {
 	 var kl = document.forms["spForm"].kl.value;
 	 var tt = document.forms["spForm"].tt.value;
 	 if(isNaN(kl) && kl.length > 0)
 		 alert("khoi luong phai nhap so");
 	 if(isNaN(tt) && tt.length > 0)
 		 alert("the tich phai nhap so");
 	 }
 	 document.forms["spForm"].action.value='save';
      document.forms["spForm"].submit();
 	
 }
 
 function upload(k) // nhan nut cap nhat
 {
	   	document.forms["spForm"].sott.value = k;
		 document.forms["spForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["spForm"].submit();	
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
 function chonSp(mm)
 {
	 var spIds = document.getElementById("spIds" + mm);
	 var spSoluong = document.getElementById("spSoLuong" + mm);
	 if(spIds != null)
	 {
		 if(spIds.checked)
		 {
			 if(spSoluong.value == '')
			 {
				 spIds.checked = false;
				 alert('Ban phai nhap so luong san pham nay');
				 return;
			 }
			 else
			 {
				 spIds.value = spIds.value + '-' + spSoluong.value;
			 }
		 }
	 }
	 return false;
 }
 
 function updateUoM()
 {
	 var dvdlId = document.getElementById("dvdlId");
	 var dvdl1 = document.getElementsByName("dvdl1");
	 dvdl1.item(0).value = dvdlId.options[dvdlId.selectedIndex].value;
	 submitform();
 }
 
function CheckValid()
{
	var masp=document.getElementById("masp").value;
	
	if(masp=="")
	{
		return 'Vui lòng nhập mã sản phẩm';
	}
}

function goBack() {
	document.forms["spForm"].action.value='back';
    document.forms["spForm"].submit();
}

function checkedAll(dk,chk) {
	for(i=0; i<chk.length; i++){
	 	if(dk.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

var timerObj, newWindow;

function viewFile(url) {
   	newWindow = window.open(url, '', 'height=800,width=1000, scrollbars=1');
}

function showhideChonkho(){
	if(document.getElementById("khongqlsl").checked == true){
		document.getElementById("chonkho1").style.display = "none";
		document.getElementById("tab_6").style.display = "none";
	} else {
		document.getElementById("chonkho1").style.display = "";
	}
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="initToolTips();">
<div id="toolTipLayer" style="position:absolute; visibility: hidden;left:0;right:0"></div>
	<form name="spForm" method="post" action="../../ThongtinsanphamgiayUpdateSvl">
		<input type="hidden" name="userId" value='<%= spBean.getUserId() %>'> 
		<input type="hidden" name="action" value="1">
		<input type="hidden" name="sott" value = "-1" >
		<input type="hidden" name="addpic" value = "0" >
		<input type="hidden" name="doiloai" value='0'>
		<input type="hidden" name="id" value='<%= spBean.getId() %>'>
		
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm &gt; Thông tin sản phẩm &gt; Cập nhật
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</TD>
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
										<TD width="30" align="left"><A href="javascript:goBack();"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve"
												style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A href="javascript: saveform()"><IMG src="../images/Save30.png"
												title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A></TD>
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
									<textarea name="dataerror" id="Msg" style="width: 100%" readonly="readonly" rows="1"><%=spBean.getMessage()%></textarea>
									<% spBean.setMessage(""); %>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
									<TR>
										<TD>
											<FIELDSET>
												<LEGEND class="legendtitle" style="color: black">Sản phẩm </LEGEND>
												<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
													<TR>
														<TD width="10%" class="plainlabel">Mã sản phẩm <FONT class="erroralert"> *</FONT></TD>
														<TD width="25%" class="plainlabel">
															<INPUT type="text" name="masp" id="masp" value='<%=spBean.getMasp()%>'>
														</TD>
														<TD width="10%" class="plainlabel">Tên đăng ký SX<FONT class="erroralert">*</FONT></TD>
														<TD width="25%" class="plainlabel" colspan="4">
															<input type="text" name="tennoibo" id="tennoibo" value='<%=spBean.getTennoibo()%>'>
														</TD>
														<%-- <TD width="10%" class="plainlabel">Tên sản phẩm <FONT class="erroralert">*</FONT></TD>
														<TD class="plainlabel">
															<input type="text" name="tensp" id="tensp" value='<%=spBean.getTen()%>'>
														</TD> --%>
													</TR>
													
													
													<TR>
														
														<TD width="10%" class="plainlabel">Tên sản phẩm <FONT class="erroralert">*</FONT></TD>
														<TD class="plainlabel">
															<input type="text" name="tensp" id="tensp" value='<%=spBean.getTen()%>'>
														</TD>
														
														<TD width="10%" class="plainlabel">Tên thương mại</TD>
														<TD width="25%" class="plainlabel" colspan="4">
															<INPUT type="text" name="tenthuongmai" id="tenthuongmai" value='<%=spBean.getTenthuongmai()%>'>
														
													</TR>
													
													<TR >
														<TD class="plainlabel">Loại sản phẩm<FONT class="erroralert"> *</FONT></TD>
														<TD  class="plainlabel">
															<%if (spBean.checkThayDoiLoaiSanpham()) {%>
															<select name="loaisp" id="loaisp" onchange="submitloaisp();" style="width:200px" >
																<%
																	try
																		{
																			while (loaisp.next())
																			{
																				if (spBean.getLoaiSpId().equals(loaisp.getString("loaiId")))
																				{
																				%>
																					<option value='<%=loaisp.getString("loaiId")%>' selected><%=loaisp.getString("loaiTen")%></option>
																				<% }  
																			}
																			loaisp.close();
																		}
																		catch (java.sql.SQLException e) { }
																%>
															</select>
																
														<%} else {%>
															<select name="loaisp" id="loaisp" onchange="submitloaisp();" style="width:200px">
																<option value=""></option>
																<%
																	try
																		{
																		System.out.println("LOAI SAN PHAM :23232323  "+spBean.getLoaiSpId());
																			while (loaisp.next())
																			{
																				System.out.println("Loai 1: "+loaisp.getString("loaiId").trim());
																				
																				if (spBean.getLoaiSpId().trim().equals(loaisp.getString("loaiId").trim()))
																				{
																				%>
																					<option value='<%=loaisp.getString("loaiId")%>' selected><%=loaisp.getString("loaiTen")%></option>
																				<% } else {
																					%>
																					<option value='<%=loaisp.getString("loaiId")%>'><%=loaisp.getString("loaiTen")%></option>
																				<% } }
																			loaisp.close();
																		}
																		catch (java.sql.SQLException e) { }
																%>
															</select>
														<%}%>
															<input type = "hidden" id = "loaispma"  name = "loaispma" value="<%=spBean.getLoaiSpMa()%>">
														</TD>
													
						<!-- NẾU LOẠI SẢN PHẨM KHÔNG PHẢI LÀ CCDC -->	
													    <% if(!spBean.getLoaiSpMa().equals("CCDC")) {%> 
														<TD class="plainlabel">Hình sản phẩm <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel"  >
															<INPUT type="file" name="image" id="filename1" title="" style="width: 70px;" value='' onChange="upload(-2);">
															<INPUT type="text" name="imagesp_name" id="filename2" value='<%= spBean.getNameHinhanhSp()%>' style="width: 120px" readonly>
															<INPUT type="hidden" name="imagesp_path" value='<%= spBean.getPathHinhanhSp()%>' style="width: 160px">
														<%
															 if(!spBean.getNameHinhanhSp().equals(""))
															 {%>
																<button onclick="javascript:viewFile('../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId %>&print=<%=spBean.getNameHinhanhSp() %>')">
																    <code>Xem hình</code>
																</button>
															<%} %>
														</TD>
														<TD class="plainlabel">Số ngày hạn cảnh báo <FONT class="erroralert">  </FONT></TD>
														<TD class="plainlabel"  >
															 <input type="text" name="songayhancanhbao" id="songayhancanhbao" value='<%=spBean.getSongayhancanhbao()%>'>
														</TD>
														
														<%-- <TD width="110px" class="plainlabel">
															<a href="" id="pubup" rel="subcontent"><img alt="Hình ảnh sản phẩm" src="../images/vitriluu.png"></a>
															<DIV id="subcontent" style=" position:absolute; visibility: hidden; border: 5px solid #80CB9B;
				                             						background-color: white; max-height:300px;overflow:auto ; width: 300px; height: 300px; padding: 4px;">
				                             					<div style="width: 100%; height: 280px;">
				                             						<img alt="" src='<%="../Templates/images/"+spBean.getNameHinhanhSp()%>' id="pic" width="100%" height="100%">		
				                             					</div>
				                             					<div style="width: 100%; height: 20px; float: left;">
				                             						<div style="float: left;"><a href="javascript:deleteImage();">Xóa</a></div>
				                             						<div style="float: right;"><a href="javascript:dropdowncontent.hidediv('subcontent');">Đóng</a></div>
				                             					</div>                             						
				                             				</DIV>
														</TD> --%>
														<TD class="plainlabel"></TD>
													</TR>
													<TR>
														
														<TD class="plainlabel">Đơn vị đo lường <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel">
															<select style="width:200px" name="dvdlId" id="dvdlId" onChange="submitform();" >
															<option value=""></option>
															<%
																try {
																	dvdl.beforeFirst();
																	while (dvdl.next())
																	{
																		if (dvdlId.equals(dvdl.getString("dvdlId")))
																		{ 
																			tenDonViDoLuong= dvdl.getString("dvdlTen");
																		%>
																			<option value='<%=dvdl.getString("dvdlId")%>' selected><%= tenDonViDoLuong %></option>
																		<% }  else { %>
																			<option value='<%=dvdl.getString("dvdlId")%>'><%=dvdl.getString("dvdlTen")%></option>
																		<% } }
																} catch (java.sql.SQLException e) { }
															%>
															</select>
														</TD>
														<TD class="plainlabel">Khối lượng <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel">
															<INPUT type="text" name="kl" id="kl" style="text-align: right;" value='<%=spBean.getKL()%>' onkeypress="return keypress(event);">
															<b><i>kg</i></b></TD>
														<TD class="plainlabel">Thể tích <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel">
															<INPUT type="text" name="tt" id="tt" style="text-align: right;" value='<%=spBean.getTT()%>' onkeypress="return keypress(event);">
															<b><i>m<sup>3</sup></i></b></TD>
													</TR>
													<tr>
														
													</tr>
												
												
												
													 <TR >
														<TD class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert">*</FONT></TD>
														<TD class="plainlabel">
															<select style="width:200px" name="dvkdId" id="dvkdId" onChange="submitform();">
																	<option value=""></option>
																	<%
																		try { 
																			while (dvkd.next()) 
																			{
																				if (dvkdId.equals(dvkd.getString("dvkdId")))
																				{ %>
																					<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen")%></option>
																				<% } else { %>
																					<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen")%></option>
																				<% } }
																				dvkd.close();
																			}
																			catch (java.sql.SQLException e){}
																	%>
															</select>
														</TD>
													
														<TD class="plainlabel">Nhãn hàng <FONT class="erroralert">*</FONT></TD>
														<TD class="plainlabel">
															<%
																if (dvkdId.length() > 0)
																{ %> 
																	<select style="width:200px" name="nhId" id="nhId" onChange="submitform();">
																<% } else { %>
																	<select style="width:200px" name="nhId" id="nhId" disabled="disabled">
																<% } %>
																
																<option value=""></option>
																	<% try
																		{
																			while (nh.next())
																			{
																				if (nhId.equals(nh.getString("nhId")))
																				{
																			%>
																				<option value='<%=nh.getString("nhId")%>' selected><%=nh.getString("nhTen")%></option>
																			<% } else { %>
																				<option value='<%=nh.getString("nhId")%>'><%=nh.getString("nhTen")%></option>
																			<% }
																			} nh.close();
																		} catch (java.sql.SQLException e) {}
																	%>
															 </select>
													    </TD>
														<TD class="plainlabel">Chủng loại<FONT class="erroralert"> *</FONT></TD>
														<TD  class="plainlabel">
															<%
															 if ((nhId.length() > 0) & (dvkdId.length() > 0))
															 { %> 
															 	<select style="width:200px" name="clId" id="clId">
															<% } else { %>
																<select style="width:200px" name="clId" id="clId" disabled="disabled">
															<% } %>
																<option value=""></option>
																	<%
																	try
																		{
																			while (cl.next())
																			{
																				if (clId.equals(cl.getString("clId")))
																				{ %>
																					<option value='<%=cl.getString("clId")%>' selected><%=cl.getString("clTen")%></option>
																				<% } else { %>
																					<option value='<%=cl.getString("clId")%>'><%=cl.getString("clTen")%></option>
																				<% } }
																				cl.close();
																			}
																			catch (java.sql.SQLException e) { }
																	%>
															</select>
														</TD>
													</TR> 
													
													
													
													
													<TR>
														<TD class="plainlabel">Tồn kho an toàn<FONT class="erroralert"> *</FONT></TD>
														<TD  class="plainlabel">
															<input type="text" name="tonkhoantoan" id="tonkhoantoan" style="text-align: right" value='<%=spBean.getTonKhoAnToan()%>' onkeypress="return keypress(event);">
														</TD>
														<TD class="plainlabel">Giá bán lẻ chuẩn <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel" align="left"><input type="text" name="giablc" id="giablc" size="10"
															style="text-align: right;" value='<%=spBean.getGiablc()%>' onkeypress="return keypress(event);"></TD>
																											
														<TD class="plainlabel">Giá vốn (mua)<!--  <FONT class="erroralert"> *</FONT> --></TD>
														<TD class="plainlabel" align="left"><input type="text" name="giamua" id="giamua" size="10"
															style="text-align: right;" value='<%=spBean.getGiaMua() %>' onkeypress="return keypress(event);"></TD>
														
														
													</tr>
													<TR>
														<TD class="plainlabel">Packsize<FONT class="erroralert"> *</FONT></TD>
														<TD  class="plainlabel">
															<select style="width:200px" name="packsize" id="packsize">
																<option value=""></option>
																<%
																	try {
																		while (packsizeRs.next())
																		{
																			if (spBean.getPacksizeId().equals(packsizeRs.getString("PK_SEQ")))
																			{
																			%>
																				<option value='<%=packsizeRs.getString("PK_SEQ")%>' selected><%=packsizeRs.getString("TEN")%></option>
																			<% } else { %>
																				<option value='<%=packsizeRs.getString("PK_SEQ")%>'><%=packsizeRs.getString("TEN")%></option>
																			<% } }
																		packsizeRs.close();
																	}
																	catch (java.sql.SQLException e) {
																		System.out.println("Đóng vừa thôi");
																	}
																%>
															</select>
														</TD>
															<td class="plainlabel">Hạn sử dụng</td>
															<TD class="plainlabel" align="left">
															<input name="hansudung" type="text" size="10" style="text-align: right;width:100px" value='<%=spBean.getHanSuDung()%>' onkeypress="return keypress(event);">
																<% if(spBean.getcheck_VoThoiHan().equals("1")) { %>
																<input type="checkbox" name="check_vothoihan" value='1' checked="checked"   ><i>Vô thời hạn</i> 
															<% } else { %>
																<input type="checkbox" name="check_vothoihan" value='1'  ><i>Vô thời hạn</i> 
															<% } %>
															</TD>														
															
														<td class="plainlabel" colspan="2">

															<% if(spBean.getKiemTraDinhLuong().equals("1")){ %>
																<input type="checkbox" name="kiemdinh_dinhluong" value='1' checked="checked" onchange="submitform();" ><i>Kiểm tra định lượng</i>
															<%} else { %>
																<input type="checkbox" name="kiemdinh_dinhluong" value='1' onchange="submitform();" ><i>Kiểm tra định lượng</i>
															<% } %>
															
															&nbsp;&nbsp;&nbsp;
															
															<% if(spBean.getKiemTraDinhTinh().equals("1")) { %>
																<input type="checkbox" name="kiemdinh_dinhtinh" value='1' checked="checked" onchange="submitform();" ><i>Kiểm tra định tính</i> 
															<% } else { %>
																<input type="checkbox" name="kiemdinh_dinhtinh" value='1' onchange="submitform();" ><i>Kiểm tra định tính</i> 
															<% } %>
														
														</td>
													</TR>
													<TR>
														<TD class="plainlabel">Chi phí nhân công</TD>
														<TD class="plainlabel" align="left"><input name="cpnc" type="text" size="10"
															style="text-align: right;" value='<%=spBean.getCPNC()%>' onkeypress="return keypress(event);"></TD>
														<TD class="plainlabel">Chi phí vận chuyển</TD>
														<TD class="plainlabel" align="left"><input name="cpvc" type="text" size="10"
															style="text-align: right;" value='<%=spBean.getCPVC()%>' onkeypress="return keypress(event);"></TD>
														<%-- <TD class="plainlabel">
															<%if(spBean.getLoaiSpId().equals("100005")){ %>
																<%if (spBean.getHangbo().equals("1")) { %> 
														 		<input name="hangbo" type="checkbox" value="1" checked> 
														 	<% } else { %> 
														 		<input name="hangbo" type="checkbox" value="0"> 
														 	<%	} %>
														 	<i>Hàng bó</i> 
															<%} %>
														</TD> --%>
														<TD class="plainlabel" colspan="2"> <%
															if(spBean.getLoaiSpId().equals("100005")){
																if (spBean.getHangbo().equals("1")) { %> 
														 		<input name="hangbo" type="checkbox" value="1" checked> 
															 	<i>Hàng bó</i> 
															 	<% } else { %> 
															 		<input name="hangbo" type="checkbox" value="0"> 
															 	<i>Hàng bó</i> 
															 	<%	} %>
															<% } %>
														 	
														 	<%if (spBean.getTrangthai().equals("1")) { %> 
														 		<input name="trangthai" type="checkbox" value="1" checked> 
														 	<% } else { %> 
														 		<input name="trangthai" type="checkbox" value="0"> 
														 	<%	} %>
														 	<i>Hoạt động</i> 
														 	
														 	<% if(spBean.getgetBatbuockiemdinh().equals("1")) { %>
																<input type="checkbox" name="batbuockiemdinh" value='1' checked="checked"><i>Bắt buộc kiểm định</i> 
															<% } else { %>
																<input type="checkbox" name="batbuockiemdinh"  value='1'   ><i>Bắt buộc kiểm định</i> 
															<% } %>
															
														</TD>
													</TR>
														
													<TR>
														<TD class="plainlabel">Thuế suất</TD>
														<TD class="plainlabel" align="left"><input name="thueSuat"  id = "thueSuat" type="text" size="10" 
															style="text-align: right;width:30%;" value='<%=spBean.getThueSuat()%>' onkeypress="return keypress(event);" onchange = "changevat();">
															<%if(spBean.getIs_khongthue().equals("1")){ %>
									                        	<input type="checkbox"  checked="checked"  id="is_khongthue" name="is_khongthue" style="width:10%;"  value="1"  onchange="changeCheck();"> 
									                        <%}else{ %>
									                        <input type="checkbox"  id="is_khongthue" name="is_khongthue" style="width:10%;"  value="1"  onchange="changeCheck()"> 
									                        <%} %>
															<span style="width:60%;">Không thuế</span> 
														</TD>
														<TD class="plainlabel">Tiêu chuẩn kỹ thuật</TD>
														<TD class="plainlabel">
															<input name="tckt" id="tckt" type="text" size="10" style="text-align: left;" value='<%=spBean.getTckt() %>'>
														</TD>
														<TD class="plainlabel">Dạng bào chế</TD>
														<TD class="plainlabel">
															<select name="dangbaoche" id="dangbaoche" style="width:200px;" >
																<%-- <option value="" > </option>	
																 <!-- 0. Tây y, 1. Thuốc nước, 2. Thuốc mỡ, 4. Thuốc Viên, 5. Nang Mềm -->
																<% String[] key = {"0","1","2","3","4"} ;
																   String[] value = {"Tây y","Thuốc nước","Thuốc mỡ","Thuốc viên","Nang mềm"};
																
																	for(int i=0; i<key.length; i++){
																		if(spBean.getDangBaoChe().equals(key[i])){
																			%>
																			<option value="<%= key[i] %>" selected> <%= value[i] %> </option>
																	<%	}else { %>
																			<option value="<%= key[i] %>" > <%= value[i] %> </option>
																<% 		}
																	}
																%> --%>
																
																<option value=""></option>
																<%
																	try {
																		if(dangBaoCheRs!=null){
																		while (dangBaoCheRs.next())
																		{
																			if (spBean.getDangBaoChe().equals(dangBaoCheRs.getString("PK_SEQ")))
																			{ 
								
																			%>
																				<option value='<%=dangBaoCheRs.getString("PK_SEQ")%>' selected><%= dangBaoCheRs.getString("TEN") %></option>
																			<% }  else { %>
																				<option value='<%=dangBaoCheRs.getString("PK_SEQ")%>'><%=dangBaoCheRs.getString("TEN")%></option>
																			<% } } dangBaoCheRs.close();}
																	} catch (java.sql.SQLException e) { e.printStackTrace(); }
																%>
																
															</select>
														</TD>
													</TR>	
													
													<TR>
														<TD class="plainlabel">Loại hàng hóa </TD>
														<TD class="plainlabel" align="left">
															<select name="loaihanghoa" id="loaihanghoa" style="width:200px;" >
																<option value="" > </option>	
																 <!-- 0 : MUA BÊN NGOÀI,1-- SẢN XUẤT--2 GIA CÔNG-->
																<% String[] key1 = {"0","1","2"} ;
																   String[] value1 = {"Mua bên ngoài","Sản xuất","Gia công"};
																
																	for(int i=0; i<key1.length; i++){
																		if(spBean.getLoaiHangHoa().equals(key1[i])){
																			%>
																			<option value="<%= key1[i] %>" selected> <%= value1[i] %> </option>
																	<%	}else { %>
																			<option value="<%= key1[i] %>" > <%= value1[i] %> </option>
																<% 		}
																	}
																%>
																
															</select>
															
														</TD>
														<TD class="plainlabel">
															<% if(spBean.getKiemtraoe().equals("1")) { %>
																<input type="checkbox" name="kiemdinh_oe" value='1' checked="checked"  ><i>Kiểm tra EO/ định tính</i> 
															<% } else { %>
																<input type="checkbox" name="kiemdinh_oe" value='1' ><i>Kiểm tra EO/ định tính</i> 
															<% } %>
														</TD>
														<TD class="plainlabel">
															<% if(spBean.getIschietkhau().equals("1")) { %>
																<input type="checkbox" name="ischietkhau" value='1' checked="checked"  ><i>Trả chiết khấu</i> 
															<% } else { %>
																<input type="checkbox" name="ischietkhau" value='1' ><i>Trả chiết khấu</i> 
															<% } %>
														</TD>
														<TD class="plainlabel"> Ngày hoàn thành </TD>
														<TD class="plainlabel">
															<input name="ngayhoanthanh" id="ngayhoanthanh" type="text" size="10" style="text-align: left;" value='<%=spBean.getNgayhoanthanh() %>'>
														</TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Thông tin hồ sơ lô sản xuất</TD>
														<TD class="plainlabel">
															<a href="" id="thongtinhosolo" rel="subcontent_hosolo">
								           	 					&nbsp;<img alt="Nhập thông số" src="../images/vitriluu.png">
								           	 				</a>
								           	 				
								           	 				<DIV id="subcontent_hosolo" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 700px; max-height:200px; overflow:auto; padding: 4px;">
								           	 					<table width="95%" align="center">
								           	 						<tr>
											                        	<th width="20%" style="font-size: 11px">Yêu cầu nguyên liệu SX</th>
											                            <th width="20%" style="font-size: 11px">Mô tả cảm quan sản phẩm</th>
											                            <th width="20%" style="font-size: 11px">Yêu cầu đóng gói, ghi nhãn</th>
											                            <th width="20%" style="font-size: 11px">Thiết bị cân</th>
											                            <th width="20%" style="font-size: 11px">Thiết bị cân khác</th>
											                        </tr>
											                        
											                        <tr>
											                        	<td><input type="text" style="width: 100%;" name="ycnlsx" value="<%=spBean.getYcnlsx() %>"></td>
											                        	<td><input type="text" style="width: 100%;" name="motasp" value="<%=spBean.getMotaSp() %>"></td>
											                        	<td><input type="text" style="width: 100%;" name="yeucaudonggoi" value="<%=spBean.getYeucaudonggoi() %>"></td>
											                        	<td>
																			<select style="width:130px" name="thietbicanid" id="thietbicanid">
																				<option value=""></option>
																				<%if(tscdRs != null){
																					tscdRs.beforeFirst();
																					while(tscdRs.next()){
																						if(tscdRs.getString("pk_seq").equals(spBean.getThietBiCan())){%>
																							<option selected value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																						<%} else { %>
																							<option value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																						<%}
																					}
																				} %>
																			</select>
																		</td>
																		<td>
																			<select style="width:130px" name="thietbicankhacid" id="thietbicankhacid">
																				<option value=""></option>
																				<%if(tscdRs != null){
																					tscdRs.beforeFirst();
																					while(tscdRs.next()){
																						if(tscdRs.getString("pk_seq").equals(spBean.getThietBiCanKhac())){%>
																							<option selected value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																						<%} else { %>
																							<option value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																						<%}
																					}
																				} %>
																			</select>
																		</td>
											                        </tr>
								           	 					</table>
								           	 					
								           	 					<div align="right">
											                     	<label style="color: red" ></label>
											                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											                     	<a href="javascript:dropdowncontent.hidediv('subcontent_hosolo')" > Đóng lại </a>
										                     	</div>
								           	 				</DIV>
								           	 				
								           	 				<script type="text/javascript">
												            	dropdowncontent.init('thongtinhosolo', "right-top", 300, "click");
												            </script>
														</TD>
														
														<TD class="plainlabel">Thông tin hồ sơ kiểm nghiệm</TD>
														<TD class="plainlabel">
															<a href="" id="thongtinhosokiemnghiem" rel="subcontent_hosokiemnghiem">
								           	 					&nbsp;<img alt="Nhập thông số" src="../images/vitriluu.png">
								           	 				</a>
								           	 				
								           	 				<DIV id="subcontent_hosokiemnghiem" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; max-height:200px; overflow:auto; padding: 4px;">
								           	 					<table width="95%" align="center">
								           	 						<tr>
											                        	<th width="20%" style="font-size: 11px">Mô tả đặc tính kỹ thuật (lý, hóa) của sản phẩm</th>
											                            <th width="20%" style="font-size: 11px">Công thức hóa học (hóa chất)</th>
											                            <th width="20%" style="font-size: 11px">Nhóm lưu trữ (hóa chất)</th>
											                            <th width="20%" style="font-size: 11px">Mức độ nguy hiểm (hóa chất)</th>
											                            <th width="20%" style="font-size: 11px">Khu vực bảo quản (hóa chất)</th>
											                        </tr>
											                        
											                        <tr>
											                        	<td><input type="text" style="width: 100%;" name="dactinhkythuat" value="<%=spBean.getDactinhkythuat() %>"></td>
											                        	<td><input type="text" style="width: 100%;" name="congthuchoahoc" value="<%=spBean.getCongthuchoahoc() %>"></td>
											                        	<td><input type="text" style="width: 100%;" name="nhomluutru" value="<%=spBean.getNhomluutru() %>"></td>
											                        	<td><input type="text" style="width: 100%;" name="mucdonguyhiem" id="mucdonguyhiem" value="<%=spBean.getMucdonguyhiem() %>" onkeypress="return keypress(event);"></td>
											                        	<td><input type="text" style="width: 100%;" name="khuvucbaoquan" value="<%=spBean.getKhuvucbaoquan() %>"></td>
											                        </tr>
								           	 					</table>
								           	 					
								           	 					<div align="right">
											                     	<label style="color: red" ></label>
											                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											                     	<a href="javascript:dropdowncontent.hidediv('subcontent_hosokiemnghiem')" > Đóng lại </a>
										                     	</div>
								           	 				</DIV>
								           	 				
								           	 				<script type="text/javascript">
												            	dropdowncontent.init('thongtinhosokiemnghiem', "right-top", 300, "click");
												            </script>
														</TD>
														
														<TD class="plainlabel" colspan="2">
															<div <%=spBean.getThuocloaisp().equals("2") ? "" : "style=\"display: none;\"" %>>
																<%if(spBean.getKhongqlsl().equals("1")){ %>
									                            	<input type="checkbox" name="khongqlsl" id="khongqlsl" value="1" checked onchange="showhideChonkho();"> Không quản lý số lượng
									                            <%} else { %>
									                            	<input type="checkbox" name="khongqlsl" id="khongqlsl" value="1" onchange="showhideChonkho();"> Không quản lý số lượng
									                            <%} %>
								                            </div>
														</TD>
													</TR>
							 <!-- KẾT THÚC CÁC LOẠI SP K PHẢI LÀ CCDC -->	
													<% }else{ %>
						     <!-- CÒN NẾU LOẠI SP LÀ CCDC THÌ CÓ NHỮNG CÁI NÀY -->
														<TD class="plainlabel">Đơn vị đo lường <FONT class="erroralert"> *</FONT></TD>
														<TD class="plainlabel">
															<select style="width:200px" name="dvdlId" id="dvdlId" onChange="submitform();" >
															<option value=""></option>
															<%
																try {
																	dvdl.beforeFirst();
																	while (dvdl.next())
																	{
																		if (dvdlId.equals(dvdl.getString("dvdlId")))
																		{ 
																			tenDonViDoLuong= dvdl.getString("dvdlTen");
																		%>
																			<option value='<%=dvdl.getString("dvdlId")%>' selected><%= tenDonViDoLuong %></option>
																		<% }  else { %>
																			<option value='<%=dvdl.getString("dvdlId")%>'><%=dvdl.getString("dvdlTen")%></option>
																		<% } }
																} catch (java.sql.SQLException e) { }
															%>
															</select>
														</TD>
														<TD class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert">*</FONT></TD>
														<TD class="plainlabel">
															<select style="width:200px" name="dvkdId" id="dvkdId" onChange="submitform();">
																	<option value=""></option>
																	<%
																		try { 
																			while (dvkd.next()) 
																			{
																				if (dvkdId.equals(dvkd.getString("dvkdId")))
																				{ %>
																					<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen")%></option>
																				<% } else { %>
																					<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen")%></option>
																				<% } }
																				dvkd.close();
																			}
																			catch (java.sql.SQLException e){}
																	%>
															</select>
														</TD>
													  	</TR>
													  	<TR>
													  	<TD class="plainlabel">Thuế suất</TD>
														<TD class="plainlabel" align="left"><input name="thueSuat" type="text" size="10"
															style="text-align: right;" value='<%=spBean.getThueSuat()%>' onkeypress="return keypress(event);">
														</TD>
														<TD class="plainlabel" colspan="4">
														<%if (spBean.getTrangthai().equals("1")) { %> 
														 		<input name="trangthai" type="checkbox" value="1" checked> 
														 	<% } else { %> 
														 		<input name="trangthai" type="checkbox" value="0"> 
														 	<%	} %>
														 	<i>Hoạt động</i> 
														</TD>
														</TR>
														
														<TR>
															<TD class="plainlabel">Thông tin hồ sơ lô sản xuất</TD>
															<TD class="plainlabel">
																<a href="" id="thongtinhosolo" rel="subcontent_hosolo">
									           	 					&nbsp;<img alt="Nhập thông số" src="../images/vitriluu.png">
									           	 				</a>
									           	 				
									           	 				<DIV id="subcontent_hosolo" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 700px; max-height:200px; overflow:auto; padding: 4px;">
									           	 					<table width="95%" align="center">
									           	 						<tr>
												                        	<th width="20%" style="font-size: 11px">Yêu cầu nguyên liệu SX</th>
												                            <th width="20%" style="font-size: 11px">Mô tả cảm quan sản phẩm</th>
												                            <th width="20%" style="font-size: 11px">Yêu cầu đóng gói, ghi nhãn</th>
												                            <th width="20%" style="font-size: 11px">Thiết bị cân</th>
												                            <th width="20%" style="font-size: 11px">Thiết bị cân khác</th>
												                        </tr>
												                        
												                        <tr>
												                        	<td><input type="text" style="width: 100%;" name="ycnlsx" value="<%=spBean.getYcnlsx() %>"></td>
												                        	<td><input type="text" style="width: 100%;" name="motasp" value="<%=spBean.getMotaSp() %>"></td>
												                        	<td><input type="text" style="width: 100%;" name="yeucaudonggoi" value="<%=spBean.getYeucaudonggoi() %>"></td>
												                        	<td>
																				<select style="width:130px" name="thietbicanid" id="thietbicanid">
																					<option value=""></option>
																					<%if(tscdRs != null){
																						tscdRs.beforeFirst();
																						while(tscdRs.next()){
																							if(tscdRs.getString("pk_seq").equals(spBean.getThietBiCan())){%>
																								<option selected value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																							<%} else { %>
																								<option value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																							<%}
																						}
																					} %>
																				</select>
																			</td>
																			<td>
																				<select style="width:130px" name="thietbicankhacid" id="thietbicankhacid">
																					<option value=""></option>
																					<%if(tscdRs != null){
																						tscdRs.beforeFirst();
																						while(tscdRs.next()){
																							if(tscdRs.getString("pk_seq").equals(spBean.getThietBiCanKhac())){%>
																								<option selected value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																							<%} else { %>
																								<option value='<%=tscdRs.getString("pk_seq") %>'><%=tscdRs.getString("ten")%></option>
																							<%}
																						}
																					} %>
																				</select>
																			</td>
												                        </tr>
									           	 					</table>
									           	 					
									           	 					<div align="right">
												                     	<label style="color: red" ></label>
												                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												                     	<a href="javascript:dropdowncontent.hidediv('subcontent_hosolo')" > Đóng lại </a>
											                     	</div>
									           	 				</DIV>
									           	 				
									           	 				<script type="text/javascript">
													            	dropdowncontent.init('thongtinhosolo', "right-top", 300, "click");
													            </script>
															</TD>
															
															<TD class="plainlabel">Thông tin hồ sơ kiểm nghiệm</TD>
															<TD class="plainlabel">
																<a href="" id="thongtinhosokiemnghiem" rel="subcontent_hosokiemnghiem">
									           	 					&nbsp;<img alt="Nhập thông số" src="../images/vitriluu.png">
									           	 				</a>
									           	 				
									           	 				<DIV id="subcontent_hosokiemnghiem" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; max-height:200px; overflow:auto; padding: 4px;">
									           	 					<table width="95%" align="center">
									           	 						<tr>
												                        	<th width="20%" style="font-size: 11px">Mô tả đặc tính kỹ thuật (lý, hóa) của sản phẩm</th>
												                            <th width="20%" style="font-size: 11px">Công thức hóa học (hóa chất)</th>
												                            <th width="20%" style="font-size: 11px">Nhóm lưu trữ (hóa chất)</th>
												                            <th width="20%" style="font-size: 11px">Mức độ nguy hiểm (hóa chất)</th>
												                            <th width="20%" style="font-size: 11px">Khu vực bảo quản (hóa chất)</th>
												                        </tr>
												                        
												                        <tr>
												                        	<td><input type="text" style="width: 100%;" name="dactinhkythuat" value="<%=spBean.getDactinhkythuat() %>"></td>
												                        	<td><input type="text" style="width: 100%;" name="congthuchoahoc" value="<%=spBean.getCongthuchoahoc() %>"></td>
												                        	<td><input type="text" style="width: 100%;" name="nhomluutru" value="<%=spBean.getNhomluutru() %>"></td>
												                        	<td><input type="text" style="width: 100%;" name="mucdonguyhiem" id="mucdonguyhiem" value="<%=spBean.getMucdonguyhiem() %>" onkeypress="return keypress(event);"></td>
												                        	<td><input type="text" style="width: 100%;" name="khuvucbaoquan" value="<%=spBean.getKhuvucbaoquan() %>"></td>
												                        </tr>
									           	 					</table>
									           	 					
									           	 					<div align="right">
												                     	<label style="color: red" ></label>
												                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												                     	<a href="javascript:dropdowncontent.hidediv('subcontent_hosokiemnghiem')" > Đóng lại </a>
											                     	</div>
									           	 				</DIV>
									           	 				
									           	 				<script type="text/javascript">
													            	dropdowncontent.init('thongtinhosokiemnghiem', "right-top", 300, "click");
													            </script>
															</TD>
															
															<TD class="plainlabel" colspan="2">
																<div <%=spBean.getThuocloaisp().equals("2") ? "" : "style=\"display: none;\"" %>>
																	<%if(spBean.getKhongqlsl().equals("1")){ %>
										                            	<input type="checkbox" name="khongqlsl" id="khongqlsl" value="1" checked onchange="showhideChonkho();"> Không quản lý số lượng
										                            <%} else { %>
										                            	<input type="checkbox" name="khongqlsl" id="khongqlsl" value="1" onchange="showhideChonkho();"> Không quản lý số lượng
										                            <%} %>
									                            </div>
															</TD>
														</TR>
													<% } %>
								<!--  KẾT THÚC PHẦM CCDC -->
													
													<%-- <%if(spBean.getLoaiSpId().equals("100041")  || spBean.getLoaiSpId().equals("100042")){%>
													<TR>
														<TD class="plainlabel"> Phế phẩm </TD>
														<TD class="plainlabel" align="left">
														<select style="width:200px" name="phephamid" id="phephamid">
																<option value=""></option>
																<%
																System.out.println("PP : "+spBean.getPhephamId());
																System.out.println("BTP : "+spBean.getBTPId());
																
																	try {
																		while (RsPhepham.next())
																		{
																			if (spBean.getPhephamId().equals(RsPhepham.getString("PK_SEQ")))
																			{
																			%>
																				<option value='<%=RsPhepham.getString("PK_SEQ")%>' selected><%=RsPhepham.getString("TEN")%></option>
																			<% } else { %>
																				<option value='<%=RsPhepham.getString("PK_SEQ")%>'><%=RsPhepham.getString("TEN")%></option>
																			<% } }
																		RsPhepham.close();
																	}
																	catch (java.sql.SQLException e) { }
																%>
															</select>
	
															</TD>
														<TD class="plainlabel"> Bán thành phẩm</TD>
														<TD class="plainlabel" align="left">
														 <select style="width:200px" name="btpid" id="btpid">
																<option value=""></option>
																<%
																	try {
																		while (RsBTP.next())
																		{
																			if (spBean.getBTPId().equals(RsBTP.getString("PK_SEQ")))
																			{
																			%>
																				<option value='<%=RsBTP.getString("PK_SEQ")%>' selected><%=RsBTP.getString("TEN")%></option>
																			<% } else { %>
																				<option value='<%=RsBTP.getString("PK_SEQ")%>'><%=RsBTP.getString("TEN")%></option>
																			<% } }
																		RsBTP.close();
																	}
																	catch (java.sql.SQLException e) { }
																%>
															</select>
														</TD>
														<TD class="plainlabel">
															 
														</TD>
														<TD class="plainlabel">  
														    
														</TD>
													</TR>
													
													 <%}  %> --%>
													
													<TR>
														<TD colspan="6">
															
														<div id="tabContaier">
																<% if( spBean.getLoaiSpMa().contains("VT")||spBean.getLoaiSpMa().contains("NL")||spBean.getLoaiSpMa().contains("VLP")||spBean.getLoaiSpMa().contains("BB")){ %>
																	 
																	<div class="titleTab"><a  class="active" href="#tab_1">Thông tin nhà cung cấp</a></div>
																	
																<% }  else  %>
																
																
																
																<%  if( spBean.getLoaiSpMa().contains("TP") ){ %>
																	 
																	<div class="titleTab"><a  class="active" href="#tab_2">Thành phẩm</a></div>
																<% } %>
																
																	<div class="titleTab" <%=spBean.getKhongqlsl().equals("1") ? "style=\"display: none;\"" : "" %> id="chonkho1">
																		<a  class="active" href="#tab_6">Chọn kho</a></div>
																 
																<div class="titleTab"><a  class="active" href="#tab_3">Quy cách</a></div>
																
																<% if(spBean.getKiemTraDinhLuong().equals("1")) { %>
																	 
																	<div class="titleTab">
																		<a  class="active" href="#tab_4">Kiểm định định lượng</a></div>
																<% } %>
																
																<% if(spBean.getKiemTraDinhTinh().equals("1")) { %>
																	 
																	<div class="titleTab">
																		<a  class="active" href="#tab_5">Kiểm định định tính</a></div>
																<% } %>
																
																<% if(spBean.getKiemTraDinhTinh().equals("1") || spBean.getKiemTraDinhLuong().equals("1")) { %>
																	<div class="titleTab"><a  class="active" href="#tab_7">Hóa chất kiểm định</a></div>
																	<div class="titleTab"><a  class="active" href="#tab_8">Máy móc kiểm định</a></div>
																<% } %>
							          	  					 
														</div>
														  
														<div class="tabDetails">
																
																 
																<!-- LOẠI SẢN PHẨM LÀ NGUYÊN LIỆU, VẬT TƯ, BAO BÌ -->
																<% if( spBean.getLoaiSpMa().contains("VT")||spBean.getLoaiSpMa().contains("NL")||spBean.getLoaiSpMa().contains("VLP")||spBean.getLoaiSpMa().contains("BB") ){ %>
																<div id="tab_1" class="tabContents" style="display:block" >
																
																	<table style="width: 100%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR class="tbheader">
																			<TH width="25%">Nhà cung cấp</TH>
																			<TH width="30%">Hồ sơ công bố</TH>
																			<TH width="10%">Ngày hết hạn công bố</TH>
																			<th width="9%">
																				Tiêu chí định lượng
																			</th>
																			<th width="9%">
																				Tiêu chí định tính
																			</th>
																			<th width="9%">
																				Hóa chất kiểm định
																			</th>
																			<th width="9%">
																				Máy móc kiểm định
																			</th>
																		</TR>
																		
																	<%for(int i = 0; i < ThongtinNCCList.size(); i++){	
																		
																		IThongtinNCC tnncc=ThongtinNCCList.get(i);
																		String ncc  = "";
													 				 	ncc=tnncc.getnhacungcap();
														 				String chuthich = tnncc.getchuthich();
														 				String hinh  = tnncc.gethinhcongbo();
														 				String hinhten =tnncc.getfilenamecb();
													 				/* if(hinhcongbo != null)
													 				{
													 					if(hinhcongbo[i] != null)
													 					{
													 						hinh = hinhcongbo[i];
													 						hinhten = filenamecb[i];
													 						chuthich = "Hình ảnh đã được upload. Click để thay đổi hình ảnh";
													 					}
													 				} */
													 				
													 				System.out.println("hinh cong bo "+i+": "+hinh);
													 				
													 				String hcb  = "";
													 				 
													 						hcb = tnncc.gethancongbo();	
													 				 
													 				
													 				String ldtt  = "";
													 				 
													 						ldtt = tnncc.getluongdattoithieu();
													 				 
													 				
													 				String thgh  = "";
													 				 
													 						thgh = tnncc.getthoihangiaohang();
													 				 
													 				
													 				String fh  = "";
													 				 
													 						fh = tnncc.getfhinh();
													 				 
													 				
													 				%>
																		<TR class= 'tblightrow' style="height:30px">
											  								<TD align="center">
											  									<select name="nhacungcap" onchange="submitform();" id = "<%="nhacungcap" + i %>" style="width:100%;height:22" >
											  										<option value=""> </option>
											  										<%	
											  										String ma = "";
											  										if (spBean.getLoaiSpMa().length()>0) ma = spBean.getLoaiSpMa();
											  										ResultSet nccRs = spBean.createNccRs(ma);		
											  											if(nccRs!=null)
																						try{
																							while(nccRs.next()){
																								if((ncc != null)){
																									if (ncc.equals(nccRs.getString("nccId"))){%>																																																																					
																										<option value="<%= nccRs.getString("nccId") %>" selected><%= nccRs.getString("nccTen")%> </option>
																						<%			}else{ %>
																										<option value="<%= nccRs.getString("nccId") %>" ><%= nccRs.getString("nccTen")%> </option>
																						<%			}
																								}else{ %>
																									<option value="<%= nccRs.getString("nccId") %>" ><%= nccRs.getString("nccTen")%> </option>
																						<%		}
																							}
																						}catch(java.sql.SQLException e){}%>
																						
																						
											  									</select>
											  									
											  								</TD>
											  								<TD align="left">
											  									<INPUT type="file" name="<%="filehinhcb" + i%>" title="<%=chuthich %>" style="width: 70px;" value='<%=fh %>' onChange="upload(<%=i%>)">
											  									<input type="text" name="filenamecb" value = "<%=hinhten %>" style="width: 65%; height:22px" readonly >
																				<input type="hidden" name="hinhcongbo" value = "<%=hinh %>" >
											  									
											  									<%
																				 if(!hinh.equals(""))
																					{%>
																					<button onclick="javascript:viewFile('../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId %>&print=<%=hinhten %>')">
																					    <code>Xem</code>
																					</button>
																					<%-- <img onmouseout="toolTip();" onmouseover="javascript:show('<%=0 %>','<%="../Templates/images/"+hinhten%>');" 
																						src="../images/image-add.png" height=20px> &nbsp;
																				 <%}else{%> 
																				 	<img src="../images/image-notfound.png" title="Không có hình ảnh" height=20px> --%>
																				<%} %>
																				
											  								</TD>
											  									
											  								<TD align="center"><INPUT type="date" name="hancongbo" style="width: 100%; text-align: right;" class = "days" value='<%=hcb%>'></TD>
											  								
											  								<td align="center">
											  								 	<a href="" id="<%=ncc%>pubup_ton" rel="subcontent<%=ncc%>">
										           	 								<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
										           	 								<DIV id="subcontent<%=ncc%>"  style=" position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             											background-color: white; max-height:600px;overflow:auto ; width: 700px; padding: 4px;" >
													                            	 <!-- KIỂM TRA ĐỊNH LƯỢNG -->
													                         
													                            	 
													                            	 
																		<% if(spBean.getKiemTraDinhLuong().equals("1") ) { %>
																		 
																			<p>Khai báo tiêu chí kiểm định định lượng</p>
																			
																			<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
																				<TR class="tbheader" >
																				
																					<TH width="5%">STT</TH>
																					<TH width="60%">Tiêu chí </TH>
																					<TH width="15%">Toán tử</TH>						
																					<TH width="20%">Giá trị chuẩn</TH>
																				
																				</TR>
																				
																				<%  
																				List<ITieuchikiemdinh> tckdList_ncc = tnncc.getTieuchikiemdinhDinhluongList();
																				List<ITieuchikiemdinh> tckd_dinhtinhList_ncc = tnncc.getTieuchikiemdinhDinhtinhList();
																				
																				int countDL_ncc = 0;
																				for(int k = 0; k < tckdList_ncc.size(); k++) 
																				{
																					ITieuchikiemdinh tckd = tckdList_ncc.get(k);	%>
																					
																					<TR>
																						<Td>
																							<input type="text"  value="<%= countDL_ncc %>" style="width: 100%; text-align: center;"> 
																						</Td>
																						<Td>
																							<input type="text" name="tieuchi<%=ncc%>" value="<%= tckd.getTieuchi() %>" style="width: 100%"> 
																						</Td>						
																						<Td>
																							<select name="toantu<%=ncc %>" style="width: 100%; text-align: center;">
																							<%
																								for(int z = 0; z < toantuList.size(); z++) {
																									if(toantuList.get(z).equals(tckd.getToantu())) { %>
																										<option value="<%=toantuList.get(z)%>" selected><%=toantuList.get(z)%></option>
																									<%} else { %>
																										<option value="<%=toantuList.get(z)%>"><%=toantuList.get(z)%></option>
																									<%}
																								}
																							%>
																							</select>
																						</Td>
																						<Td>
																							<input type="text" name="giatrichuan<%=ncc%>" value="<%= tckd.getGiatrichuan() %>" style="width: 100%; text-align: right;" placeholder="nhập từ mức - đến mức" ><!-- onkeypress="return keypress(event);" > --> 
																						</Td>
																					</TR>
																					
																				<% countDL_ncc++; } %>
																				
																				<% for(int k = countDL_ncc; k <= 15; k++ ) { %>
																					
																					<TR>
																						<Td>
																							<input type="text"  value="<%= k %>" style="width: 100%; text-align: center;"> 
																						</Td>
																						<Td>
																							<input type="text" name="tieuchi<%=ncc%>" value="" style="width: 100%"> 
																						</Td>
																						
																						<Td>
																							<select name="toantu<%=ncc%>" style="width: 100%; text-align: center;">
																							<% for(int z = 0; z < toantuList.size(); z++) {%>
																								<option value="<%=toantuList.get(z)%>"><%=toantuList.get(z)%></option>
																							<% } %>
																							</select>
																						</Td>
																						<Td>
																							<input type="text" name="giatrichuan<%=ncc%>" value="" style="width: 100%; text-align: right;" placeholder="nhập từ mức - đến mức"><!--  onkeypress="return keypress(event);" > --> 
																						</Td>												
																					</TR>
																					
																				<% } %>
																				
																			</TABLE>
																					 
																					<% } %>
																					<!-- KIỂM TRA ĐỊNH LƯỢNG -->
																					
																					
													                   			  <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%=ncc%>');">Hoàn tất</a></div>
													               				 </DIV>
											  								</td>
											  								<td align="center">
											  								 	<a href="" id="<%=ncc%>pubup_ton_dt" rel="subcontent_dt<%=ncc%>">
										           	 								<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
										           	 								<DIV id="subcontent_dt<%=ncc%>"  style=" position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             											background-color: white; max-height:600px;overflow:auto ; width: 700px; padding: 4px;" >
													                            	 <!-- KIỂM TRA ĐỊNH LƯỢNG -->
													                            	 
													                            	 
																		<% if(spBean.getKiemTraDinhTinh().equals("1") ) { %>
																		
																		 
																			<p>Khai báo tiêu chí kiểm định định tính</p>
																			
																			<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
																				<TR class="tbheader" >
																				
																					<TH width="5%">STT</TH>
																					<TH width="60%">Tiêu chí </TH>
																					 
																				
																				</TR>
																				
																				<%  
																			 
																				List<ITieuchikiemdinh> tckd_dinhtinhList_ncc = tnncc.getTieuchikiemdinhDinhtinhList();
																				
																				int countDL_ncc = 0;
																				for(int k = 0; k < tckd_dinhtinhList_ncc.size(); k++) 
																				{
																					ITieuchikiemdinh tckd = tckd_dinhtinhList_ncc.get(k);	%>
																					
																					<TR>
																						<Td>
																							<input type="text"  value="<%= countDL_ncc %>" style="width: 100%; text-align: center;"> 
																						</Td>
																						<Td>
																							<input type="text" name="tieuchi_dinhtinh<%=ncc%>" value="<%= tckd.getTieuchi() %>" style="width: 100%"> 
																						</Td>						
																					 
																					</TR>
																					
																				<% countDL_ncc++; } %>
																				
																				<% for(int k = countDL_ncc; k <= 15; k++ ) { %>
																					
																					<TR>
																						<Td>
																							<input type="text"  value="<%= k %>" style="width: 100%; text-align: center;"> 
																						</Td>
																						<Td>
																							<input type="text" name="tieuchi_dinhtinh<%=ncc%>" value="" style="width: 100%"> 
																						</Td>
																						
																						 												
																					</TR>
																					
																				<% } %>
																				
																			</TABLE>
																					 
																					<% } %>
																					<!-- KIỂM TRA ĐỊNH LƯỢNG -->
													                   			  <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent_dt<%=ncc%>');">Hoàn tất</a></div>
													               				 </DIV>
											  								</td>
											  								
											  							<TD align="center"><!-- Hóa chất kiểm định của nhà cung cấp-->
											  									<a href="" id="<%=ncc%>hdkd_ncc" rel="hckd_ncc_subcontent<%=ncc%>">
										           	 								<img alt="Hóa chất kiểm định" src="../images/vitriluu.png"></a>
										           	 								<DIV id="hckd_ncc_subcontent<%=ncc%>"  style=" position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             											background-color: white; max-height:600px;overflow:auto ; width: 700px; padding: 4px;" >
											  										
											  											<% if(spBean.getKiemTraDinhTinh().equals("1") || spBean.getKiemTraDinhLuong().equals("1")) { %>
											  												<p>Khai báo hóa chất dùng trong kiểm định</p>
											  												<TABLE style="width: 40%" cellpadding="1px" cellspacing="1px" align="center">
											  													<TR>
																									<TD style="width: 3%"><strong>Số lượng chuẩn</strong></TD>
																								<%
																									System.out.println(tnncc.getnhacungcap());
																									List<IHoaChat_SanPham> hoaChatKiemDinhList_NCC = tnncc.getHoaChatKiemDinhList();
																									int soLuongChuan = (hoaChatKiemDinhList_NCC.size() > 0) ? hoaChatKiemDinhList_NCC.get(0).getsoLuongChuan() : 0;
																									if (hoaChatKiemDinhList_NCC.size() > 0) {
																										hoaChatKiemDinhList_NCC.get(0).setDvtSanPhamDuocKiemDinh(tenDonViDoLuong);
																									}
																								%>	
																									<Td style="width: 2%">
																											<input type="number" style="width: 95%" name="soLuongChuan<%=ncc%>" min="0" value="<%= soLuongChuan %>" align="center"  > 
																									</Td>
																									<Td style="width: 2%">
																											<input type="text" style="width: 95%" name="donViChuan" value="<%= tenDonViDoLuong %>" align="center" readonly="readonly">
																														
																									</Td>
																								</TR>
											  												</TABLE>
											  												</br></br>
											  												<TABLE border="0" style="width: 100%" cellpadding="1px" cellspacing="1px" align="center">
												  												<TR class="tbheader" >
																									<TH width="10%">Số thứ tự</TH>
																									<TH width="30%">Mã hóa chất</TH>
																									<TH width="40%">Tên hóa chất</TH>
																									<TH width="10%">Số lượng</TH>
																									<TH width="10%">Đơn vị</TH>
																								</TR>
																								
																								<% 
																									int countHoaChat = 0;
																									if (hoaChatKiemDinhList_NCC.size() > 0) {
																										Iterator<IHoaChat_SanPham> iteratorHoaChat = hoaChatKiemDinhList_NCC.iterator();
																										while (iteratorHoaChat.hasNext()) {
																											IHoaChat_SanPham elementHoaChat = iteratorHoaChat.next();
																								%>
																									<TR>
																										<Td>
																											<input type="text"  value="<%= countHoaChat + 1 %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																											<input type="hidden"  name="PK_SEQHC<%=ncc%>" style="width: 100%" value="<%= elementHoaChat.getPK_SEQHoaChatKiemDinh() %>" readonly="readonly" > 
																										</Td>
																										<Td>
																											<input type="text"  name="maHoaChat<%=ncc%>" style="width: 100%" value="<%= elementHoaChat.getMaHoaChatKiemDinh() %>" onkeyup="ajax_showOptions(this,'hoaChat',event)" AUTOCOMPLETE="off"> 
																										</Td>
																										<Td>
																											<input type="text"  name="tenHoaChat<%=ncc%>" style="width: 100%" value="<%= elementHoaChat.getTenHoaChatKiemDinh() %>" readonly="readonly" > 
																										</Td>
																										<Td>
																											<input type="number"  name="soLuongChatKiemDinh<%=ncc%>" style="width: 100%" value="<%= elementHoaChat.getSoLuongChatKiemDinh() %>" align="center" > 
																										</Td>
																										<Td>
																											<input type="text"  name="dvChatKiemDinh<%=ncc%>" style="width: 100%" value="<%= elementHoaChat.getDvtHoaChatKiemDinh() %>" align="center" readonly="readonly"> 
																										</Td>
																																																						
																									</TR>	
																								
																								<%
																											countHoaChat++; 
																										} %>	
																								<% } %>	
											  													<% for(int dem = countHoaChat; dem < 15; dem++) { %>
											  														<TR>
																										<Td>
																											<input type="text"  value="<%= dem + 1  %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																											<input type="hidden"  name="PK_SEQHC<%=ncc%>" style="width: 100%" value=""  > 
																										</Td>
																										<Td>
																											<input type="text"  name="maHoaChat<%=ncc%>" style="width: 100%" value="" onkeyup="ajax_showOptions(this,'hoaChat',event)" AUTOCOMPLETE="off"> 
																										</Td>
																										<Td>
																											<input type="text"  name="tenHoaChat<%=ncc%>" style="width: 100%" value="" readonly="readonly" > 
																										</Td>
																										<Td>
																											<input type="number"  name="soLuongChatKiemDinh<%=ncc%>" style="width: 100%" value="" align="center" min="0" > 
																										</Td>
																										<Td>
																											<input type="text"  name="dvChatKiemDinh<%=ncc%>" style="width: 100%" value="" align="center" readonly="readonly"> 
																										</Td>
																									</TR>	
													  													<% } %>
											  												</TABLE><!-- Kết thúc bảng hóa chất kiểm định của nhà cung cấp-->
											  												
											  											<%} %>
											  										<div align="right"><a href="javascript:dropdowncontent.hidediv('hckd_ncc_subcontent<%=ncc%>');">Hoàn tất</a></div>
													               				
													  								
											  										
											  										</DIV>
											  								</TD>
											  								<TD align="center"><!-- Bat dau máy móc kiểm định nha cung cap--> 	
											  									<a href="" id="<%=ncc%>mmkd_ncc" rel="mmkd_ncc_subcontent<%=ncc%>">
											           	 						<img alt="Máy móc kiểm định" src="../images/vitriluu.png"></a>
											           	 						<DIV id="mmkd_ncc_subcontent<%=ncc%>"  style=" position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             											background-color: white; max-height:600px;overflow:auto ; width: 700px; padding: 4px;" >
					                             									
					                             									<% if(spBean.getKiemTraDinhTinh().equals("1") || spBean.getKiemTraDinhLuong().equals("1")) { %>
					                             											<p>Khai báo máy móc dùng trong kiểm định</p>
					                             											<TABLE border="0" style="width: 100%" cellpadding="1px" cellspacing="1px" align="center">
					                             												<TR class="tbheader" >
																									<TH width="10%">Số thứ tự</TH>
																									<TH width="30%">Mã máy móc</TH>
																									<TH width="60%">Tên máy móc</TH>
																									
																								</TR>	
																								
																								<% 
																								List<IMayMoc_SanPham> mayMocKiemDinhList_NCC = tnncc.getMayMocKiemDinhList();
																								int countMayMoc = 0;
																								if (mayMocKiemDinhList_NCC.size() > 0) {
																									Iterator<IMayMoc_SanPham> iteratorMayMoc = mayMocKiemDinhList_NCC.iterator();
																									while (iteratorMayMoc.hasNext()) {
																										IMayMoc_SanPham elementMayMoc = iteratorMayMoc.next();
																								%> 
																									<TR>
																										<Td>
																											<input type="text"  value="<%= countMayMoc + 1 %>" style="width: 100%; text-align: center;"> 
																											<input type="hidden"  name="PK_SEQMM<%=ncc%>" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getId() %>"  readonly="readonly" > 
																										</Td>
																										<Td>
																											<input type="text"  name="maMayMoc<%=ncc%>" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getMa() %>" onkeyup="ajax_showOptions(this,'mayMoc',event)" AUTOCOMPLETE="off"  > 
																										</Td>
																										<Td>
																											<input type="text"  name="tenMayMoc<%=ncc%>" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getDiengiai() %>" readonly="readonly" > 
																										</Td>
																																																					
																									</TR>
																								
																									<% countMayMoc++; 
																									}  %>
																								<%}  %>
																								<% for(int dem = countMayMoc; dem < 15; dem++ ) { %>
																									<TR>
																										<Td>
																											<input type="text"  value="<%= dem + 1 %>" style="width: 100%; text-align: center;" readonly="readonly" > 
																											<input type="hidden"  name="PK_SEQMM<%=ncc%>" style="width: 100%" value=""  > 
																										</Td>
																										<Td>
																											<input type="text"  name="maMayMoc<%=ncc%>" style="width: 100%" value="" onkeyup="ajax_showOptions(this,'mayMoc',event)" AUTOCOMPLETE="off" > 
																										</Td>
																										<Td>
																											<input type="text"  name="tenMayMoc<%=ncc%>" style="width: 100%" value="" readonly="readonly" > 
																										</Td>
																																																						
																									</TR>
																								<% } %>
					                             											</TABLE>
					                             									<%} %>
					                             									<div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%=ncc%>');">Hoàn tất</a></div>
					                             								</DIV>
											  								
											  								</TD><!-- Ket thuc máy móc kiểm định nha cung cap--> 	
															  	
																	  	</TR>
																	<% } %>
																	
																	<%for(int i =  ThongtinNCCList.size(); i < ThongtinNCCList.size() +5; i++){	
																		
																		 
																		String ncc  = "";
													 				 	ncc="";
														 				String chuthich ="Hình ảnh đã được upload. Click để thay đổi hình ảnh";
														 				String hinh  = "";
														 				String hinhten ="";
														 				String hcb  = "";
														 				String ldtt  = "";
													 					String thgh  = "";
													 					String fh  = "";
													 				   
													 				%>
																		<TR class= 'tblightrow' style="height:30px">
											  								<TD align="center">
											  									<select name="nhacungcap"  onchange="submitform();" id = "<%="nhacungcap" + i %>" style="width:100%;height:22" >
											  										<option value=""> </option>
											  										<%	
											  										String ma = "";
											  										if (spBean.getLoaiSpMa().length()>0) ma = spBean.getLoaiSpMa();
											  										ResultSet nccRs = spBean.createNccRs(ma);		
											  											if(nccRs!=null)
																						try{
																							while(nccRs.next()){
																								if((ncc != null)){
																									if (ncc.equals(nccRs.getString("nccId"))){%>																																																																					
																										<option value="<%= nccRs.getString("nccId") %>" selected><%= nccRs.getString("nccTen")%> </option>
																						<%			}else{ %>
																										<option value="<%= nccRs.getString("nccId") %>" ><%= nccRs.getString("nccTen")%> </option>
																						<%			}
																								}else{ %>
																									<option value="<%= nccRs.getString("nccId") %>" ><%= nccRs.getString("nccTen")%> </option>
																						<%		}
																							}
																						}catch(java.sql.SQLException e){}%>
																						
																						
											  									</select>
											  									
											  								</TD>
											  								<TD align="left">
											  									<INPUT type="file" name="<%="filehinhcb" + i%>" title="<%=chuthich %>" style="width: 70px;" value='<%=fh %>' onChange="upload(<%=i%>)">
											  									<input type="text" name="filenamecb" value = "<%=hinhten %>" style="width: 65%; height:22px" readonly >
																				<input type="hidden" name="hinhcongbo" value = "<%=hinh %>" >
											  									
											  									<%
																				 if(!hinh.equals(""))
																					{%>
																					<button onclick="javascript:viewFile('../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId %>&print=<%=hinhten %>')">
																					    <code>Xem</code>
																					</button>
																					<%-- <img onmouseout="toolTip();" onmouseover="javascript:show('<%=0 %>','<%="../Templates/images/"+hinhten%>');" 
																						src="../images/image-add.png" height=20px> &nbsp;
																				 <%}else{%> 
																				 	<img src="../images/image-notfound.png" title="Không có hình ảnh" height=20px> --%>
																				<%} %>
																				 
											  								</TD>
											  								<TD align="center"><INPUT type="date" name="hancongbo" style="width: 100%; text-align: right;" class = "days" value='<%=hcb%>'></TD>
											  								<TD></TD>
																	  		<TD></TD>
																	  		<TD></TD>
																	  		<TD></TD>
																	  	</TR>
																	<% } %>
																		
																	</TABLE>
																</div>
																
																<!-- LOẠI SẢN PHẨM LÀ THÀNH PHẨM -->
																<% } else if( spBean.getLoaiSpMa().contains("TP") ){ %>
																<div id="tab_2" class="tabContents" style="display:none" >
																
																<TABLE border="0" width="100%" cellpadding="4" cellspacing="0" align="center">
																		<TR>
																			<TD width="50%">
																			
																	<TABLE border="0" width="100%" cellpadding="4" cellspacing="0" align="center">
																		<TR>
																			<TD width="25%" style="font-size: 9pt;">Hồ sơ công bố</TD>
																			<TD width="75%" style="font-size: 9pt;">
																			<%
																				 String chuthich = "";
																				 if(!spBean.getHinhcongboTP().equals("")) chuthich = "Click để upload hồ sơ công bố"; 
																				 else chuthich="Hồ sơ công bố đã được upload. Click để thay đổi";
																			%>
										  										<INPUT type="file" name="filename" title="<%=chuthich %>" style="width: 70px;" value="" onChange="upload(-1)">
											  									<input type="text" name="filenamecbTP" value = "<%=spBean.getFilenamecbTP() %>" style="width: 33%; height:22px" readonly >	
											  									<input type="hidden" name="hinhcongboTP" value = "<%=spBean.getHinhcongboTP() %>" >
																				<%
																				 if(!spBean.getHinhcongboTP().equals(""))
																				 {%>
																					<button onclick="javascript:viewFile('../../ThongtinsanphamgiayUpdateSvl?userId=<%=userId %>&print=<%=spBean.getFilenamecbTP() %>')">
																					    <code>Xem hồ sơ</code>
																					</button>
																					
																					<!-- <img src="../images/image-up.png" title="Xem hồ sơ công bố" height=20px> -->
																					<%-- <img onmouseout="toolTip();" onmouseover="javascript:show('<%=0 %>','<%="../Templates/images/"+spBean.getFilenamecbTP()%>');" 
																						src="../images/image-add.png" height=20px> &nbsp; --%>
																					
																				 <%-- <%}else{%> 
																				 	<img src="../images/image-notfound.png" title="Không có hình ảnh" height=20px> --%>
																				<%} %>
																			</TD>
																		</TR>
																		<TR>
																			<TD style="font-size: 9pt;">Ngày hết hạn</TD>
																			<TD style="font-size: 9pt;"><INPUT type="date" name="hancongboTP" style="width: 80%; text-align: right;" class = "days" value='<%=spBean.getHancongboTP()%>'></TD>
																		</TR>
																		<TR>
																			<TD style="font-size: 9pt;" colspan=2 align=left>Chọn kho vùng</TD> 	  									
																		</TR>
										
										<tr>	
											<td colspan=2>						
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="15%">Mã</TH>
													<TH width="50%">Tên</TH>
													<TH width="10%">Chọn
														<input type="checkbox" name="chonkho" onClick="checkedAll(document.spForm.chonkho,document.spForm.khovung)">								
													</TH>
										<% 
										String[] kho = spBean.getKhovung();
										String kv="";
										int j;
										int i = 0;
										boolean ok = false;
		                                String lightrow = "tblightrow";
		                                String darkrow = "tbdarkrow";
										
		                                ResultSet khoRs = spBean.createKhoRs();
		                                
		                                %>
		                                
		                                <%
											if(khoRs!=null)
										try{
											while(khoRs.next()){
										  	if (i % 2 != 0) {		%>
                                              <TR class= <%=lightrow%> >
                                   <%	  	} else {%>
	                                          <TR class= <%= darkrow%> >
	                               <%	  	}%>
												<TD><%= khoRs.getString("khoMa") %></TD>
												<TD><%= khoRs.getString("khoTen") %></TD>																									
												<TD align="center">
												
												<%
												ok = false;
												kv = khoRs.getString("khoId");
												for (j=0;j<kho.length;j++)
													if (kho!=null) if (kho[j]!=null) if (kho[j].equals(kv)) 
													{
														ok = true; break;
													}
												if (ok){%>
												<input type="checkbox" name="khovung" value='<%= kv %>' checked>	
												<%
													}else{ %>
													<input type="checkbox" name="khovung" value='<%= kv %>'>
													<%} %>								
													</TD>
											 	</TR>
																									
									<%		i++;
										}}catch(java.sql.SQLException e){} %>
										
											</TABLE>	
											</td>
																		</TR>
																		<tr class="tbheader"></tr>
																	</TABLE>
																	
																	</TD>
																	<TD valign=top>
																	<TABLE border="0" width="100%" cellpadding="4" cellspacing="0" align="left">
																		<TR style="height:30px">
																			<TD style="font-size: 9pt;" width="30%"> </TD>
																			<TD align="left"></TD>
																		</TR>
																		
																		<TR>
														<TD style="font-size: 9pt;" width="25%"> Phế phẩm </TD>
														<TD align="left" width="75%">
														<select style="width:200px; height:22px" name="phephamid" id="phephamid">
																<option value=""></option>
																<%
																System.out.println("PP : "+spBean.getPhephamId());
																System.out.println("BTP : "+spBean.getBTPId());
																
																	try {
																		while (RsPhepham.next())
																		{
																			if (spBean.getPhephamId().equals(RsPhepham.getString("PK_SEQ")))
																			{
																			%>
																				<option value='<%=RsPhepham.getString("PK_SEQ")%>' selected><%=RsPhepham.getString("TEN")%></option>
																			<% } else { %>
																				<option value='<%=RsPhepham.getString("PK_SEQ")%>'><%=RsPhepham.getString("TEN")%></option>
																			<% } }
																		RsPhepham.close();
																	}
																	catch (java.sql.SQLException e) { }
																%>
															</select>
	
															</TD>
														</TR>
														<TR>
														<TD style="font-size: 9pt;"> Bán thành phẩm</TD>
														<TD align="left"></TD>
													</TR>
													
													<tr>	
											<td colspan=2>						
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="30%">Mã</TH>
													<TH width="50%">Tên</TH>
													<TH width="20%">Chọn
														<input type="checkbox" name="chonbtp" onClick="checkedAll(document.spForm.chonbtp,document.spForm.bantp)">								
													</TH>
										<% 
										String[] bantp = spBean.getBantp();
										String btp="";
										int jj;
										int ii = 0;
										boolean ok2 = false;
										
		                                %>
		                                
		                                <%
											if(RsBTP!=null)
										try{
											while(RsBTP.next()){
										  	if (ii % 2 != 0) {		%>
                                              <TR class= <%=lightrow%> >
                                   <%	  	} else {%>
	                                          <TR class= <%= darkrow%> >
	                               <%	  	}%>
												<TD><%= RsBTP.getString("ma")%></TD>	
												<TD><%= RsBTP.getString("ten") %></TD>																										
												<TD align="center">
												
												<%
												ok2 = false;
												btp = RsBTP.getString("pk_seq");
												System.out.println("btp Id: "+btp);
												for (jj=0;jj<bantp.length;jj++)
													if (bantp!=null) if (bantp[jj]!=null) if (bantp[jj].equals(btp)) 
													{
														ok2 = true; break;
													}
												if (ok2){%>
												<input type="checkbox" name="bantp" value='<%= btp %>' checked>	
												<%
													}else{ %>
													<input type="checkbox" name="bantp" value='<%= btp %>'>
													<%} %>								
													</TD>
											 	</TR>
																									
									<%		i++;
										}}catch(java.sql.SQLException e){} %>
										
											</TABLE>	
											</td>
																		</TR>
																		<tr class="tbheader"></tr>
																	</TABLE>
																	</TD>
																</TR>
															</TABLE>
																</div>
																<% } %>
																<!-- LOẠI SẢN PHẨM LÀ THÀNH PHẨM -->
																
																<div  id="tab_3" class="tabContents" style="display:none" >

																	<p>Thuộc nhóm sản phẩm</p>
																	<table style="width: 95%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR class="tbheader">
																			<TH width="20%">Mã nhóm</TH>
																			<TH width="70%">Tên nhóm</TH>
																			<TH width="10%">Chọn</TH>
																		</TR>
																		<%
																			if(nspRs != null)
																			{
																				while(nspRs.next())
																				{ %>
																					
																				<tr>
																					<td>
																						<input type="text" style="width: 100%" value="<%= nspRs.getString("nspId") %>" readonly="readonly">
																					</td>
																					<td>
																						<input type="text" style="width: 100%" value="<%= nspRs.getString("nspTen") %>" readonly="readonly">
																					</td>
																					<td align="center">
																					<% if(spBean.getNspIds().indexOf(nspRs.getString("nspId")) >= 0 ) { %>
																						<input type="checkbox" name="nspIds" value="<%= nspRs.getString("nspId") %>" checked="checked" >
																					<% } else { %>
																						<input type="checkbox" name="nspIds" value="<%= nspRs.getString("nspId") %>" >
																					<% } %>
																					</td>
																				</tr>	
																					
																				<% }
																			}
																		%>
																	</TABLE>
																	
																	<p>Thiết lập quy cách</p>
																	<table style="width: 95%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR class="tbheader">
																			<TH width="20%">Số lượng</TH>
																			<TH width="20%">Đơn vị đo lường</TH>
																			<TH width="20%">Quy đổi</TH>
																			<TH width="20%">Số lượng</TH>
																			<TH width="20%">Đơn vị đo lường</TH>
																		</TR>
																		
																		<%			
																		String[] sl1 = spBean.getSl1();
																		String[] sl2 = spBean.getSl2();
																		String[] dvdl1 = spBean.getDvdl1();
																		String[] dvdl2 = spBean.getDvdl2();
																		boolean[] fixed = new boolean[5];
																		
																		for (int i=0;i<5;i++) fixed[i] = false;
																		
																		/* if (spBean.CheckDVDL())
																		{
																			for (int i=0;i<5;i++){
																				fixed[i] =false;
																				try{
																					if (sl1[i].length()*sl2[i].length()*dvdl1[i].length()*dvdl2[i].length()>0){
																						fixed[i] = false;
																					}
																				}catch(Exception er ){}
																			}
																		} */
															
														for(int i = 0; i < 5; i++){	%>
															<TR class= 'tblightrow'>
											  					<TD align="center" >
											  					<%	if (!(dvdl1[i] == null)){
											  							if (dvdl1[i].trim().length()>0){   
											  										if (fixed[i]){%>
																			<input name="sl1" type="text" style="width:100%" value="<%=sl1[i] %>" readonly >
																			<%		}else{  %>
											  							<input name="sl1" type="text" style="width:100%" value="<%=sl1[i] %>" >
																<%		}}
											  							else{  %>
																			<input name="sl1" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl1" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
															  	<%	dvdl = spBean.createDvdlRS(); 
																	if (i == 0){
																		if (fixed[i]){%>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" onChange="updateUoM();" >
																	<%												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																						<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																		 </select>
																		 
																<%		}else{  %>
											  							<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" onChange="updateUoM();" >
											  							<option value=""> </option>																	 	
																	<%												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																						<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}else{ %>
																						<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																    </select>
																<%		}
																	}else{ if (fixed[i]){%>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22">
																	<%												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																						<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																    </select>
																    
															<%		}else{  %>
											  							<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" >
											  							<option value=""> </option>																	 	
																	<%												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																						<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}else{ %>
																						<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																    </select>
																<%		}} %>
																	
															  	</TD>
															  	<TD align="center" > = </TD>
											  					<TD align="center" >
											  					<%	if (!(dvdl2[i] == null)){
											  							if (dvdl2[i].trim().length()>0){
											  								if (fixed[i]){%>   
											  									
																			<input name="sl2" type="text" style="width:100%" value="<%=sl2[i] %>" readonly>
																<%		}else{ %>
																			<input name="sl2" type="text" style="width:100%" value="<%=sl2[i] %>" >
											  					<% 		}}else{  %>
																			<input name="sl2" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl2" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
															  	
															  	<%if (fixed[i]) {%>
																	<select name="dvdl2" style="width:100%;height:22">
																	<% 	
																		dvdl = spBean.createDvdlRS();
																	try{
																		while(dvdl.next()){
																			if(!(dvdl2[i] == null)){
																				if (dvdl2[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																					<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}
																			}else{ %>
																				<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																	<%		}
																		}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																    </select>		
																    
																	<%}else{ %>
																	
																	<select name="dvdl2" style="width:100%;height:22" >
																	<option value=""> </option>
																	<% 	
																		dvdl = spBean.createDvdlRS();
																	try{
																		while(dvdl.next()){
																			if(!(dvdl2[i] == null)){
																				if (dvdl2[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																					<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}
																			}else{ %>
																				<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																	<%		}
																		}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																    </select>		
																	<%} %>
																										  
															  	</TD>
														  	</TR>
														<% } %>
																		
																	</TABLE>
															
																</div>
																
																<div id="tab_6" class="tabContents" style="display:none" >
																
																<TABLE border="0" width="100%" cellpadding="4" cellspacing="0" align="center">
																		<TR>
																			<TD width="50%">
																			
																	<TABLE border="0" width="100%" cellpadding="4" cellspacing="0" align="center">
																		<TR>
																			<TD style="font-size: 9pt;" colspan=2 align=left>Chọn kho vùng</TD> 	  									
																		</TR>
										
										<tr>	
											<td colspan=2>						
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="15%">Mã</TH>
													<TH width="50%">Tên</TH>
													<TH width="10%">Chọn
														<input type="checkbox" name="chonkho" onClick="checkedAll(document.spForm.chonkho,document.spForm.khovung)">								
													</TH>
										<% 
										String[] kho = spBean.getKhovung();
										String kv="";
										int j;
										int i = 0;
										boolean ok = false;
		                                String lightrow = "tblightrow";
		                                String darkrow = "tbdarkrow";
										
		                                ResultSet khoRs = spBean.createKhoRs();
		                                
		                                %>
		                                
		                                <%
											if(khoRs!=null)
										try{
											while(khoRs.next()){
										  	if (i % 2 != 0) {		%>
                                              <TR class= <%=lightrow%> >
                                   <%	  	} else {%>
	                                          <TR class= <%= darkrow%> >
	                               <%	  	}%>
												<TD><%= khoRs.getString("khoMa") %></TD>
												<TD><%= khoRs.getString("khoTen") %></TD>																									
												<TD align="center">
												
												<%
												ok = false;
												kv = khoRs.getString("khoId");
												for (j=0;j<kho.length;j++)
													if (kho!=null) if (kho[j]!=null) if (kho[j].equals(kv)) 
													{
														ok = true; break;
													}
												if (ok){%>
												<input type="checkbox" name="khovung" value='<%= kv %>' checked>	
												<%
													}else{ %>
													<input type="checkbox" name="khovung" value='<%= kv %>'>
													<%} %>								
													</TD>
											 	</TR>
																									
									<%		i++;
										}}catch(java.sql.SQLException e){} %>
										
											</TABLE>	
											</td>
										</tr>
																	</TABLE>
																</TABLE>
																</div>
																
																<!-- KIỂM TRA ĐỊNH LƯỢNG -->
																<% 
																
																System.out.println("jsp: getKiemTraDinhLuong = " + spBean.getKiemTraDinhLuong());
																if(spBean.getKiemTraDinhLuong().trim().equals("1") ) { %>
																<div id="tab_4" class="tabContents" style="display:none" >
																	<p>Khai báo tiêu chí kiểm định định lượng</p>
																	
																	<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
																		<TR class="tbheader" >
																		
																			<TH width="5%">STT</TH>
																			<TH width="60%">Tiêu chí </TH>
																			<TH width="15%">Toán tử</TH>						
																			<TH width="20%">Giá trị chuẩn</TH>
																		
																		</TR>
																		
																		<% int countDL = 0;
																		for(i = 0; i < tckdList.size(); i++) 
																		{
																			ITieuchikiemdinh tckd = tckdList.get(i);	%>
																			
																			<TR>
																				<Td>
																					<input type="text"  value="<%= countDL %>" style="width: 100%; text-align: center;"> 
																				</Td>
																				<Td>
																					<input type="text" name="tieuchi" value="<%= tckd.getTieuchi() %>" style="width: 100%"> 
																				</Td>						
																				<Td>
																					<select name="toantu" style="width: 100%; text-align: center;">
																					<%
																						for(int z = 0; z < toantuList.size(); z++) {
																							if(toantuList.get(z).equals(tckd.getToantu())) { %>
																								<option value="<%=toantuList.get(z)%>" selected><%=toantuList.get(z)%></option>
																							<%} else { %>
																								<option value="<%=toantuList.get(z)%>"><%=toantuList.get(z)%></option>
																							<%}
																						}
																					%>
																					</select>
																				</Td>
																				<Td>
																					<input type="text" name="giatrichuan" value="<%= tckd.getGiatrichuan() %>" style="width: 100%; text-align: right;" placeholder="nhập từ mức - đến mức" ><!-- onkeypress="return keypress(event);" > --> 
																				</Td>
																			</TR>
																			
																		<% countDL++; } %>
																		
																		<% for(i = countDL; i <= 15; i++ ) { %>
																			
																			<TR>
																				<Td>
																					<input type="text"  value="<%= i %>" style="width: 100%; text-align: center;"> 
																				</Td>
																				<Td>
																					<input type="text" name="tieuchi" value="" style="width: 100%"> 
																				</Td>
																				
																				<Td>
																					<select name="toantu" style="width: 100%; text-align: center;">
																					<% for(int z = 0; z < toantuList.size(); z++) {%>
																						<option value="<%=toantuList.get(z)%>"><%=toantuList.get(z)%></option>
																					<% } %>
																					</select>
																				</Td>
																				<Td>
																					<input type="text" name="giatrichuan" value="" style="width: 100%; text-align: right;" placeholder="nhập từ mức - đến mức"><!--  onkeypress="return keypress(event);" > --> 
																				</Td>												
																			</TR>
																			
																		<% } %>
																		
																	</TABLE>
																</div>
																<% } %>
																<!-- KIỂM TRA ĐỊNH LƯỢNG -->
																
																<!-- KIỂM TRA ĐỊNH TÍNH -->
																<% 
																
																System.out.println("jsp: getKiemTraDinhTinh = " + spBean.getKiemTraDinhTinh());
																if(spBean.getKiemTraDinhTinh().trim().equals("1") ) { %>
																<div id="tab_5" class="tabContents" style="display:none" >
																	<p>Khai báo tiêu chí kiểm định định tính</p>
																	
																	<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
																		<TR class="tbheader" >
																		
																			<TH width="5%">STT</TH>
																			<TH width="95%">Tiêu chí </TH>
																			
																		</TR>
																		
																		<% int countDT = 0;
																		for(i = 0; i < tckd_dinhtinhList.size(); i++) 
																		{
																			ITieuchikiemdinh tckd = tckd_dinhtinhList.get(i);	%>
																			
																			<TR>
																				<Td>
																					<input type="text"  value="<%= countDT %>" style="width: 100%; text-align: center;"> 
																				</Td>
																				<Td>
																					<input type="text" name="tieuchi_dinhtinh" value="<%= tckd.getTieuchi() %>" style="width: 100%"> 
																				</Td>						
																			</TR>
																			
																		<% countDT++; } %>
																		
																		<% for(i = countDT; i <= 15; i++ ) { %>
																			
																			<TR>
																				<Td>
																					<input type="text"  value="<%= i %>" style="width: 100%; text-align: center;"> 
																				</Td>
																				<Td>
																					<input type="text" name="tieuchi_dinhtinh" value="" style="width: 100%"> 
																				</Td>
																																																
																			</TR>
																			
																		<% } %>
																		
																	</TABLE>
																</div>
																<% } %>
																<!-- KIỂM TRA ĐỊNH TÍNH -->
																<!-- HÓA CHẤT KIỂM ĐỊNH -->
																<% if(spBean.getKiemTraDinhTinh().equals("1") || spBean.getKiemTraDinhLuong().equals("1")) { %>
																<div id="tab_7" class="tabContents" style="display:none" >
																	<p>Khai báo hóa chất dùng trong kiểm định</p>
																	
																	<TABLE style="width: 45%" cellpadding="0px" cellspacing="1px" align="center">
																		<TR>
																			<TD style="width: 3%"><strong>Số lượng chuẩn</strong></TD>
																		<%
																			int soLuongChuan = (hoaChatKiemDinhList.size() > 0) ? hoaChatKiemDinhList.get(0).getsoLuongChuan() : 0;
																			if (hoaChatKiemDinhList.size() > 0) {
																				hoaChatKiemDinhList.get(0).setDvtSanPhamDuocKiemDinh(tenDonViDoLuong);
																				tenDonViDoLuong = hoaChatKiemDinhList.get(0).getDvtSanPhamDuocKiemDinh();
																			}
																		%>	
																			<Td style="width: 2%">
																					<input type="number" style="width: 95%" name="soLuongChuan" min="0" value="<%= soLuongChuan %>" align="center" > 
																			</Td>
																			<Td style="width: 2%">
																					<input type="text" style="width: 95%" name="donViChuan" value="<%= tenDonViDoLuong %>" align="center" readonly="readonly">
																								
																			</Td>
																		</TR>
																	</TABLE>
																	</br></br>
																	
																	<div> 
																	
																	</div>
																	<TABLE border="0" style="width: 65%" cellpadding="1px" cellspacing="1px" align="center">
																		<TR class="tbheader" >
																			<TH width="15%">Số thứ tự</TH>
																			<TH width="30%">Mã hóa chất</TH>
																			<TH width="30%">Tên hóa chất</TH>
																			<TH width="15%">Số lượng</TH>
																			<TH width="10%">Đơn vị</TH>
																		</TR>
																		 
																		<% 
																		int countHoaChat = 0;
																		if (hoaChatKiemDinhList.size() > 0) {
																			Iterator<IHoaChat_SanPham> iteratorHoaChat = hoaChatKiemDinhList.iterator();
																			while (iteratorHoaChat.hasNext()) {
																				IHoaChat_SanPham elementHoaChat = iteratorHoaChat.next();
																		%>
																			<TR>
																				<Td>
																					<input type="text"  value="<%= countHoaChat + 1 %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																					<input type="hidden"  name="PK_SEQHC" style="width: 100%" value="<%= elementHoaChat.getPK_SEQHoaChatKiemDinh() %>" readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="text"  name="maHoaChat" style="width: 100%" value="<%= elementHoaChat.getMaHoaChatKiemDinh() %>" AUTOCOMPLETE="off" > 
																				</Td>
																				<Td>
																					<input type="text"  name="tenHoaChat" style="width: 100%" value="<%= elementHoaChat.getTenHoaChatKiemDinh() %>" readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="number"  name="soLuongChatKiemDinh" style="width: 100%" value="<%= elementHoaChat.getSoLuongChatKiemDinh() %>" align="center" > 
																				</Td>
																				<Td>
																					<input type="text"  name="dvChatKiemDinh" style="width: 100%" value="<%= elementHoaChat.getDvtHoaChatKiemDinh() %>" align="center" readonly="readonly"> 
																				</Td>
																																																
																			</TR>
																			<%
																			countHoaChat++; } %>	
																		<% } %>	
																		
																		
																		
																		<% for(i = countHoaChat; i < 15; i++) { %>
																			<TR>
																				<Td>
																					<input type="text"  value="<%= i + 1  %>" style="width: 100%; text-align: center;" readonly="readonly"> 
																					<input type="hidden"  name="PK_SEQHC" style="width: 100%" value=""  > 
																				</Td>
																				<Td>
																					<input type="text"  name="maHoaChat" style="width: 100%" value=""  onkeyup="ajax_showOptions(this,'hoaChat',event)" AUTOCOMPLETE="off" > 
																				</Td>
																				<Td>
																					<input type="text"  name="tenHoaChat" style="width: 100%" value="" readonly="readonly" > 
																				</Td>
																				<Td>
																					<input type="number"  name="soLuongChatKiemDinh" style="width: 100%" value="" align="center" min="0" > 
																				</Td>
																				<Td>
																					<input type="text"  name="dvChatKiemDinh" style="width: 100%" value="" align="center" readonly="readonly"> 
																				</Td>
																				
																			<% } %>
																		
																		<%-- <% int countDT = 0;
																		for(int i = countDT; i <= 15; i++ ) { %> --%>
																			
																			
																			
																		<%-- <% } %> --%>
																	<% } %>	
																	</TABLE>
																</div>
																<!-- HÓA CHẤT KIỂM ĐỊNH -->
																
																<!-- MÁY MÓC KIỂM ĐỊNH -->
																<% if(spBean.getKiemTraDinhTinh().equals("1") || spBean.getKiemTraDinhLuong().equals("1")) { %>
																<div id="tab_8" class="tabContents" style="display:none" >
																	<p>Khai báo máy móc dùng trong kiểm định</p>
																	<TABLE border="0" style="width: 65%" cellpadding="1px" cellspacing="1px" align="center">
																		<TR class="tbheader" >
																			<TH width="10%">Số thứ tự</TH>
																			<TH width="30%">Mã máy móc</TH>
																			<TH width="60%">Tên máy móc</TH>
																			
																		</TR>
																		<% 
																		int countMayMoc = 0;
																		if (mayMocKiemDinhList.size() > 0) {
																			Iterator<IMayMoc_SanPham> iteratorMayMoc = mayMocKiemDinhList.iterator();
																			while (iteratorMayMoc.hasNext()) {
																				IMayMoc_SanPham elementMayMoc = iteratorMayMoc.next();
																		%> 
																				<TR>
																					<Td>
																						<input type="text"  value="<%= countMayMoc + 1 %>" style="width: 100%; text-align: center;"> 
																						<input type="hidden"  name="PK_SEQMM" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getId() %>"  readonly="readonly" > 
																					</Td>
																					<Td>
																						<input type="text"  name="maMayMoc" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getMa() %>"  onkeyup="ajax_showOptions(this,'mayMoc',event)" AUTOCOMPLETE="off" > 
																					</Td>
																					<Td>
																						<input type="text"  name="tenMayMoc" style="width: 100%" value="<%= elementMayMoc.getMayMocKiemDinh().getDiengiai() %>" readonly="readonly" > 
																					</Td>
																																																
																				</TR>
																			<% countMayMoc++; }  %>
																		<%}  %>
																		<% for(i = countMayMoc; i < 15; i++ ) { %>
																			
																			<TR>
																				<Td>
																					<input type="text"  value="<%= i + 1 %>" style="width: 100%; text-align: center;"> 
																					<input type="hidden"  name="PK_SEQMM" style="width: 100%" value=""  > 
																				</Td>
																				<Td>
																					<input type="text"  name="maMayMoc" style="width: 100%" value=""  onkeyup="ajax_showOptions(this,'mayMoc',event)"> 
																				</Td>
																				<Td>
																					<input type="text"  name="tenMayMoc" style="width: 100%" value="" readonly="readonly" > 
																				</Td>
																																																
																			</TR>
																			
																		<% } %>
																	<% } %>	
																	</TABLE>
																</div>
																<!--MÁY MÓC KIỂM ĐỊNH -->
																
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
				</TD>
		</TABLE>
		<script type="text/javascript">
		
	  <%
	  	String[] maNCCList = new String[ThongtinNCCList.size()];
	  	for(i = 0; i < ThongtinNCCList.size(); i++){	
	  			
				IThongtinNCC tnncc=ThongtinNCCList.get(i);
				String ncc  = tnncc.getnhacungcap();
				maNCCList[i] = ncc;
			%>
				dropdowncontent.init("<%=ncc%>pubup_ton", "left-top", 600, "click");
				dropdowncontent.init("<%=ncc%>pubup_ton_dt", "left-top", 600, "click");
				dropdowncontent.init("<%=ncc%>hdkd_ncc", "left-top", 600, "click");
				dropdowncontent.init("<%=ncc%>mmkd_ncc", "left-top", 600, "click");
			<%
		}
	  	%>

		
//	replaces();
	// dropdowncontent.init("subcontent100070", "center", 300, "click");
	replacesMayMoc_NCC();
	replacesHoaChat_NCC();
	replacesTSCD();
</script>
	</form>
</BODY>
											  										

</HTML>
<%
	if (dvdl != null)
			dvdl.close();
		if (dvkd != null)
			dvkd.close();
		if (nh != null)
			nh.close();
		if (cl != null)
			cl.close();
		if (nspRs != null)
			nspRs.close();
		spBean.DBClose();
	}
%>