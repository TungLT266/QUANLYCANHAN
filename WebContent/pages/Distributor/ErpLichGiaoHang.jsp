<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpSoxuathangNppList obj = (IErpSoxuathangNppList)session.getAttribute("obj"); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[11];
		quyen = util.GetquyenNew("ErpLichgiaohangSvl","",userId);
		ResultSet nvgnRs = obj.getNvgnRs();
		ResultSet kbhRs = obj.getKbhRs();
		ResultSet khachhangRs = obj.getKhachhangRs();
		ResultSet khoRs = obj.getKhoRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OneOne - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
    <SCRIPT language="javascript" type="text/javascript">
	 function TaoLichGiaoHangChung()
	 {   
		 var tungay = document.getElementById("tungay").value;
		 var denngay = document.getElementById("denngay").value;
		 var nvgnId = document.getElementById("nvgnId").value;
		 
		 if( tungay == '' )
		 {
			 alert('Vui lòng chọn từ ngày')
			 return;
		 }
		 
		 document.forms["ckParkForm"].action.value = "LichGHChung";
	     document.forms["ckParkForm"].submit();
	 }
	 
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].nppId.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	 
	 function taobaocao()
	 {
		 var nvgnId = document.getElementById("nvgnId").value;
		 if( nvgnId == '' )
		 {
			 alert('Vui lòng chọn nhân viên giao hàng');
			 return;
		 }
		 
		 document.forms["ckParkForm"].action.value = "excel";
		 document.forms["ckParkForm"].submit();
	 }
	</SCRIPT>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
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
			font-size: 13;
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>
	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpLichgiaohangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
   
        	&nbsp;Quản lý bán hàng > Bán hàng > Lịch giao hàng

        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Lịch giao hàng</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" id="tungay" value="<%= obj.getTungay() %>" maxlength="10"  />
                        </TD>
                    
                        <TD class="plainlabel" width="130px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" maxlength="10"  />
                        </TD>
                    </TR>
                    <TR>
                     
                        <TD class="plainlabel" valign="middle">Loại đơn hàng </TD>
                        <TD class="plainlabel" valign="middle"  >
                           <select name="loaidonhang" class="select2" style="width: 200px"  >
								<% if (obj.getLoaidonhang().equals("0")){%>
								  	<option value="0" selected>Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getLoaidonhang().equals("1")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1" selected>ETC</option>
								  	<option value="2" >OTC</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getLoaidonhang().equals("2")) {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2" selected >OTC</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Đơn hàng NPP</option>
								  	<option value="1">ETC</option>
								  	<option value="2">OTC</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD>  
                        
                        <TD class="plainlabel">Kênh bán hàng </TD>
							<TD  class="plainlabel" >
								<select name="kbhId" class="select2" style="width: 200px"  >
									<option value="" >ALL</option>
									<%if(kbhRs!=null)
										{ while (kbhRs.next()){%>
										<option value="<%=kbhRs.getString("pk_seq")%>" <%if( kbhRs.getString("pk_Seq").equals(obj.getKbhId())){ %> selected="selected" <%} %>><%= kbhRs.getString("ten") %></option>
									<%} kbhRs.close(); } %>
								</select>
						</TD>
                    </TR> 
                   
                   <TR>
                     
                        <TD class="plainlabel" valign="middle">Kho hàng hoá </TD>
                        <TD class="plainlabel" valign="middle"  >
                            <select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(khoRs!=null){ while (khoRs.next()){ %>
								<option value="<%=khoRs.getString("pk_seq")%>" <%if(khoRs.getString("pk_seq").equals(obj.getKhohh())){%> selected="selected" <%}%>><%=khoRs.getString("ten") %>  </option>
								<%}khoRs.close(); }%>
								
							</select>
                        </TD>  
                        
                        <TD class="plainlabel">Khách hàng </TD>
							<TD  class="plainlabel" >
								<select name="khachhangId" class="select2" style="width: 200px"  >
									<option value="" >ALL</option>
									<%if(khachhangRs!=null)
										{ while (khachhangRs.next()){%>
										<option value="<%=khachhangRs.getString("pk_seq")%>" <%if( khachhangRs.getString("pk_Seq").equals(obj.getKhachhangId())){ %> selected="selected" <%} %>><%= khachhangRs.getString("ten") %></option>
									<%} khachhangRs.close(); } %>
								</select>
						</TD>
                    </TR> 
                    
		           <TR>
		           		<TD class="plainlabel">Nhân viên giao hàng </TD>
							<TD  class="plainlabel" colspan="3" >
								<select name="nvgnId" id='nvgnId' class="select2" style="width: 200px"  >
									<option value="" >ALL</option>
									<%if(nvgnRs!=null)
										{ while (nvgnRs.next()){%>
										<option value="<%=nvgnRs.getString("pk_seq")%>" <%if(nvgnRs.getString("pk_Seq").equals(obj.getNvbanhang())){ %> selected="selected" <%} %>><%=nvgnRs.getString("ten") %></option>
									<%} nvgnRs.close(); } %>
								</select>
						</TD>
								
		           </TR>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:TaoLichGiaoHangChung();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xem lịch giao hàng chung </a>
                            &nbsp;&nbsp;
                            <a class="button2" href="javascript:taobaocao();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất EXCEL </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html>
<% } %>


