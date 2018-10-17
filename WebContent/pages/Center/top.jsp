<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.center.servlets.count.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>&nbsp;</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script src="../scripts/jquery-1.8.1.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
 function submitform()
{
    document.forms["logoutForm"].submit();
}
</SCRIPT>

<script type="text/javascript">  
var flag = true;
function abc()
{
	flag = false;
}

function confirmMe()
{
if(flag)
	 {
		$(window).bind('unload', function(){
		   $.ajax({
		    cache: false,
		    async: false,
		    dataType: "script",
		        url: "../../ThongbaoAjax"
		    });
		}); 
	 }

}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"  onbeforeunload="confirmMe()">
<form name="logoutForm" method="post" action="../../LogoutSvl">
<div id="dataDisplay"></div>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">

	<TR bgcolor="#FFFFFF">
		<TD width="25%"  align="right" valign="middle">
			
			<img src="../images/salesup_erp.jpg" width="140" height="50" align="right"> &nbsp;&nbsp;
			<img src="../images/logo.gif" height="30" align="right" style=" padding-top: 10px; " /> &nbsp;
		</TD>
		<TD align="right" valign="middle" class="blanc" >
		<A href="../../LogoutSvl" target="_parent" >Đăng xuất&nbsp;&nbsp;</A>
		<A href="../../LoginSvl" onclick = "abc();" target="_parent" >Đổi mật khẩu &nbsp;&nbsp;</A>
		<div><iframe style="width: 200px ;height: 20px" id = "frame1" src = "counter.jsp" frameborder="0" scrolling="no">
        </iframe></div>
		</TD>
		
	</TR>	
<!-- 	<TR bgcolor="#FFFFFF">
		<TD width="15%"  align="right" valign="middle"></TD>
		<TD align="right" valign="top"  >
		<iframe id = "frame1" src = "counter.jsp" scrolling="no" frameborder = "0"
            style="" >
        </iframe>
		</TD>
	</TR> -->
</TABLE>
</form>
</BODY>
</HTML>
<%} %>
