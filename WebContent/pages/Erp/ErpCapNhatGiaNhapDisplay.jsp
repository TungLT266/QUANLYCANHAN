<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IErpCapnhatgianhap obj = (IErpCapnhatgianhap)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet pnkRs = (ResultSet)obj.getPnkRs(); %>
<% List<ISanpham> spList = obj.getSanphamList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
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
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
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
		 document.forms["erpCngn"].action.value = "submit";
	     document.forms["erpCngn"].submit();
	 }
	 
	 function saveform()
	 {	
		 if(!confirm('Bạn chắc chắn muốn cập nhật giá nhập cho các phiếu nhập kho này?'))
		 {
		 	return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['erpCngn'].action.value='save';
	     document.forms['erpCngn'].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpCngn"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpCngn"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var pnkIds = document.getElementsByName("pnkIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < pnkIds.length; i++)
			 {
				 pnkIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < pnkIds.length; i++)
			 {
				 pnkIds.item(i).checked = false;
			 }
		 }
	 }
</SCRIPT>
</head>
<body>
<form name="erpCngn" method="post" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý cung ứng > Quản lý tồn kho > Cập nhật giá nhập kho > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpCapnhatgianhapkhoSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    </div>
    
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Cập nhật giá nhập kho </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%" >Diễn giải </TD>
                        <TD class="plainlabel" >
                            <input type="text" name="diengiai" value="<%= obj.getGhichu() %>" />
                        </TD>
                    </TR>
                    </TABLE>    
                    <hr />
                    <TABLE width="100%" cellpadding="0px" cellspacing="1px">
                    	<tr class="tbheader">
                    		<th style="width: 10%"> Phiếu nhập</th>
                    		<th style="width: 10%"> Ngày nhập</th>
                    		<th style="width: 15%"> Mã sản phẩm</th>
                    		<th style="width: 35%" > Tên sản phẩm</th>
                    		<th style="width: 10%"> Số lượng</th>
                    		<th style="width: 10%"> Giá nhập</th>
                    		<th style="width: 10%"> Giá điều chỉnh</th>
                    	</tr>
                    	<% for(int i = 0; i < spList.size(); i++)
                    	{ 
                    		ISanpham sp = spList.get(i);
                    	%>
                    	<tr>
                    		<td>
                    			<input type="hidden" name="idphieunhap" style="width: 100%" value="<%= sp.getPnkId() %>">
                    			<input type="text" name="maphieu" style="width: 100%" value="<%= sp.getSonhapkho() %>">
                    		</td>
                    		<td>
                    			<input type="text" name="ngaynhap" style="width: 100%" value="<%= sp.getNgaynhap() %>" >
                    		</td>
                    		<td>
                    			<input type="hidden" name="idsanpham" style="width: 100%" value="<%= sp.getSpId() %>" >
                    			<input type="text" name="masanpham" style="width: 100%" value="<%= sp.getSpMa() %>" >
                    		</td>
                    		<td>
                    			<input type="text" name="tensanpham" style="width: 100%" value="<%= sp.getSpTen() %>">
                    		</td>
                    		<td>
                    			<input type="text" name="soluong" style="width: 100%; text-align: right;" value="<%= sp.getSoluong() %>" >
                    		</td>
                    		<td>
                    			<input type="text" name="gianhap" style="width: 100%; text-align: right;" value="<%= sp.getGiaOld() %>">
                    		</td>
                    		<td>
                    			<input type="text" name="giadieuchinh" style="width: 100%; text-align: right;" value="<%= sp.getGiaNew() %>"  onkeypress="return keypress(event);">
                    		</td>
                    	</tr>	
                    	<% } %>		
                    </TABLE>      
       </fieldset>            					                    
    	</div>
    </div>  
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('pnkId', "right-bottom", 300, "click");
</script>
</html>
<%
	if(lspRs!=null){
		lspRs.close();
	}
	if(pnkRs!=null){
		pnkRs.close();
	}
	spList.clear();
	obj.DbClose();
	session.setAttribute("obj", null) ;  
	
	}
%>