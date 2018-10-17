<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuthanhtoan.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IErpDonmuahangList_Giay obj = (IErpDonmuahangList_Giay)session.getAttribute("obj");
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<% ResultSet dvthList = (ResultSet)obj.getDvthList(); %>
<% ResultSet dmhList = (ResultSet)obj.getDmhList(); %>
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>
<% ResultSet lspList = (ResultSet)obj.getLoaisanpham(); 

	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpPhieuThanhToanSvl","",userId);
	
 	NumberFormat formatter = new DecimalFormat("#,###,###"); 
 	NumberFormat formater = new DecimalFormat("##,###,###");  %>
<% obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Traphaco - Project</title>
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
   
    <script type="text/javascript" src="../scripts/jquery.js"></script>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
	
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 300px;
			border: 1px solid black;
			padding: 5px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			font-size: 1.2em;
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
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["erpDmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "Tao moi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].dvth.value = "";
	    document.forms["erpDmhForm"].ncc.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].mactsp.value = "";
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].ncc.value = "";
	    document.forms["erpDmhForm"].sodonmuahang.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].nguoitao.value = "";
	    document.forms["erpDmhForm"].sochungtu.value = "";
	    document.forms["erpDmhForm"].maChungTu.value = "";
 
	    document.forms["erpDmhForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>

</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpPhieuThanhToanSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ &gt; Công nợ phải trả &gt;  Đề nghị thanh toán
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" style="border-radius:4px; height: 20px;" />
                        </TD>
                        <TD class="plainlabel" width="22%" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" style="border-radius:4px; height: 20px;" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="dvth" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("pk_seq").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                        <TD class="plainlabel" valign="middle">Nhà cung cấp/ Nhân viên/ Khách hàng</TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="ncc" value="<%= obj.getNCC() %>" onchange="submitform()" style="border-radius:4px; height: 20px;">
                        </TD>                        
                    </TR>
                   
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="trangthai" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	
                            	<% if(obj.getTrangthai().equals("0")) { %>
                            		<option value="0" selected="selected" >Chưa chốt</option>
                            	<% } else { %> 
                            		<option value="0" >Chưa chốt</option>
                            	<%  } %>
                            	<% if(obj.getTrangthai().equals("-1")) { %>
                            		<option value="-1" selected="selected" >Chưa duyệt (Đã chốt)</option>
                            	<% } else { %> 
                            		<option value="-1" >Chưa duyệt (Đã chốt)</option>
                            	<%  } %>
                            	<% if(obj.getTrangthai().equals("-2")) { %>
                            		<option value="-2" selected="selected" >Đã duyệt(Trưởng Bộ Phận)</option>
                            	<% } else { %> 
                            		<option value="-2" >Đã duyệt(Trưởng Bộ Phận)</option>
                            	<%  } %>
                            	<% if(obj.getTrangthai().equals("-3")) { %>
                            		<option value="-3" selected="selected" >Đã duyệt(Kế toán viên)</option>
                            	<% } else { %> 
                            		<option value="-3" >Đã duyệt(Kế toán viên)</option>
                            	<%  } %>
                            	 <% if(obj.getTrangthai().equals("1")) { %>
                            		<option value="1" selected="selected" >Hoàn tất</option>
                            	<% } else { %>
                            		<option value="1" >Hoàn tất</option>
                            	 <% }  %>
                            	  <% if(obj.getTrangthai().equals("3")) { %>
                            		<option value="3" selected="selected" >Đã hủy</option>
                            	<% } else { %>
                            		<option value="3" >Đã hủy</option>
                            	 <% }  %>
                            	 <% if(obj.getTrangthai().equals("4")) { %>
                            		<option value="2" selected="selected" >Đã xóa</option>
                            	<% } else { %>
                            		<option value="2" >Đã xóa</option>
                            	 <% }  %>
                            	  <% if(obj.getTrangthai().equals("5")) { %>
                            		<option value="5" selected="selected" >Đã thanh toán</option>
                            	<% } else { %>
                            		<option value="5" >Đã thanh toán</option>
                            	 <% }  %>
                            	
                            </select>
                        </TD>      
                         
                         <TD class="plainlabel" valign="middle" >Người tạo </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="nguoitao" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(nguoitaoRs != null)
	                        		{
	                        			while(nguoitaoRs.next())
	                        			{  
	                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitaoIds())){ %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
	                        		 <% } } nguoitaoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                       
                    </TR>   
                    <TR>                        
		                <TD class="plainlabel" valign="middle">Số chứng từ </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sochungtu" value="<%= obj.getSoChungTu() %>" onchange="submitform()" style="border-radius:4px; height: 20px;">
                        </TD>          
						<TD class="plainlabel" >Mã Sản Phẩm</TD>
						<TD class="plainlabel">
							<input type="text" id="mactsp" name="mactsp" value="<%= obj.getMaCtSp() %>" onchange="submitform()" style="border-radius:4px; height: 20px;" />
						</TD>                                           
                    </TR>   
					<tr>
					<TD class="plainlabel" valign="middle">Mã đề nghị thanh toán </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sodonmuahang" value="<%= obj.getSodonmuahang() %>" onchange="submitform()" style="border-radius:4px; height: 20px;">
                        </TD>          
                    	
                        <TD class="plainlabel" valign="middle" >Chi phí trong khoán/ngoài khoán </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="ngoaiKhoan" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	
                            	<% if(obj.getNgoaiKhoan().equals("1")) { %>
                            		<option value="1" selected="selected" >Chi phí ngoài khoán</option>
                            		<option value="0" >Chi phí trong khoán</option>
                            	<% } else if(obj.getNgoaiKhoan().equals("0")) { %>
                            		<option value="1"  >Chi phí ngoài khoán</option>
                            		<option value="0" selected="selected">Chi phí trong khoán</option>
                            	<%  } else { %>
                            		<option value="1" >Chi phí ngoài khoán</option>
                            		<option value="0" >Chi phí trong khoán</option>
                            	<%  } %>
                            	
                            	
                            </select>
                        </TD> 
                    </tr>
                    <tr>
                   		<TD class="plainlabel" valign="middle">Mã chứng từ </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                            <input type="text" name="maChungTu" value="<%= obj.getMaChungTu() %>" onchange="submitform()" style="border-radius:4px; height: 20px;">
                        </TD> 
                    </tr>
                    <tr>
                   		<TD class="plainlabel" valign="middle">Tổng tiền </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                            <input type="text" name="tongtien" value="<%= obj.getTongtiensauVat() %>" onchange="submitform()" style="border-radius:4px; height: 20px;">
                        </TD> 
                    </tr>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                    
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Đề nghị thanh toán </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
            	
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="7%">Mã ĐNTT</TH>
	                    <TH align="center" width="7%">Số chứng từ</TH>
	                    <TH align="center" width="7%">Mã chứng từ</TH>
	                    <TH align="center" width="7%">Ngày</TH>
	                    <TH align="center" width="7%">ĐVTH</TH>
	                    <TH align="center" width="15%">Nhà cung cấp/ Nhân viên/ Khách hàng / Chi nhánh</TH>
	                    <TH align="center" width="7%">Trạng thái</TH>
	                    <TH align="center" width="10%">Tổng tiền</TH>
	                    <TH align="center" width="5%">Tổng lượng</TH>
	                    <TH align="center" width="7%">Ngày tạo</TH>
	                    <TH align="center" width="8%"> Người tạo </TH>
	                    <TH align="center" width="7%"> Ngày sửa </TH>
	                    <TH align="center" width="8%"> Người sửa </TH>
	                    <TH align="center" width="10%">Tác vụ</TH>
	                </TR>
	                
	                <%  int m = 0;
	                	if(dmhList!=null) {
	                		while (dmhList.next())
	                		{
	                			String style="";
	                			String tt = dmhList.getString("Trangthai");
	                			
	                			String ISDACHOT = dmhList.getString("ISDACHOT");
	                    		String ISTP = dmhList.getString("ISTP");
	                    		String ISKTV = dmhList.getString("ISKTV");
	                    		String ISKTT = dmhList.getString("ISKTT");
	                    		String ISTHANHTOAN = dmhList.getString("ISTHANHTOAN");
	                    		String ISHOANTAT = dmhList.getString("ISHOANTAT");
	                    		
	                			if(dmhList.getString("noibo").equals("1"))
	                			{
	                				style="color: #006400;font-weight: bold; ";
	                			}
	                			 if((m % 2 ) == 0 & tt.equals("3")) { %>
	                			 	<TR class='tbdarkrow'  style="color: red;" <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
		                		 <%}else if((m % 2 ) != 0 & tt.equals("3")) {  %>
		                		 		<TR class='tblightrow' style="color: red;" <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >	                		 
		                		 <%}else if((m % 2 ) == 0) { %>
		                				<TR class='tbdarkrow' <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
		                		 <%}else{ %>
		                		 		<TR class='tblightrow' <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
		                		<%  } %>
	                			 <TD align="center"><%= dmhList.getString("SOCHUNGTU") %></TD>
	                			 <TD align="center"><%= dmhList.getString("SODNTT") %></TD>
	                			 <TD align="center"><%= dmhList.getString("dmhId") %></TD>
				                 <TD align="center"><%= dmhList.getString("Ngaymua") %></TD>
				                 <TD align="center"><%= dmhList.getString("Ten") %></TD>	
<!-- 				                 Dùng tab pre và định dạng lại để không mất khoảng trắng, tìm kiếm đúng -->
				                 <TD align="left"><pre style="font-family: Arial; word-wrap: break-word; white-space: pre-wrap;"><%= dmhList.getString("nccTen") %></pre></TD>	
	                			
	                		<%
				               if(tt.equals("0") || tt.equals("1") || tt.equals("2") || tt.equals("3")  ){%>
				                    <TD align="center">
				                    
				              <%}else{ %>
				                    <TD align="center" style="color: red;">
				              <%} %>
				              
				              <%			                    	
		                    		String str = "";
		                    				                    		
		                    		String isthanhtoan = dmhList.getString("ISTHANHTOAN"); 
		                    		
		                    		String trangthai = "";
		                    		
				              		if(tt.equals("0"))
					                {
										trangthai = "Chưa chốt";
										
										if(ISDACHOT.equals("1"))
										{
											trangthai = "Đã chốt";
										}
											
										if(ISTP.equals("1"))
										{
											trangthai = "Đã duyệt (Trưởng phòng)";
										}
										
										if(ISKTV.equals("1"))
										{
											//Hiện tại kế toán viên có quyền như kế toán trưởng
											// nên để luôn là kế toán trưởng duyệt
											//trangthai = "Đã duyệt (Kế toán gán mã chi phí)";
											trangthai = "Đã duyệt (Kế toán trưởng)";
										}
										
										/* if(ISKTT.equals("1"))
										{
											trangthai = "Đã duyệt (Kế toán trưởng)";
										} */
										
									}
		                    		else
		                    		{
		                    			if(tt.equals("1"))
										{
		                    				trangthai = "Đã duyệt (Kế toán trưởng)";
		                    				if(ISTHANHTOAN.equals("1"))
											{
												trangthai = "Đã thanh toán";
											}
											
											if(ISHOANTAT.equals("1"))
											{
												trangthai = "Đã hoàn tất";
											}
										}/* else if(tt.equals("2"))
										{
											
										} */
										else
										{
											if(tt.equals("3"))
												trangthai = "Đã xóa";
											else 
												trangthai = "Đã hủy";
										}
									}
										
		                    		
									
				              		
		                    		if(str.trim().length() > 0)
		                    			trangthai = trangthai + " ( " + str + " ) ";
				                    		
				                %>
				                <%= trangthai %>    
				                
				                <TD align="right"><%= formater.format(Double.parseDouble(dmhList.getString("tongtienAvat"))) + " " + dmhList.getString("tiente") + "" %></TD>
			                    <TD align="center"><%= dmhList.getString("tongluong") %></TD>
			                    <TD align="center"><%= dmhList.getString("ngaytao") %></TD>
			                    <TD align="left"><%= dmhList.getString("nguoitao") %></TD>
			                    <TD align="center"><%= dmhList.getString("ngaysua") %></TD>	
			                    <TD align="left"><%= dmhList.getString("nguoisua") %></TD>	
			                    <TD align="center">
			                    <% if(tt.equals("0")){ %>				              	                                
		                               
			                    	<% if( ISDACHOT.equals("0") ){ %>
			                    	
			                    		<% if(quyen[2] != 0 ){ %>
				                
		                               <A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&update=<%= dmhList.getString("dmhId") %>">
		                               		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;		  
		                               		                             
		                               <%} %>		
		                               
		                               <% if(quyen[4]!=0){ %>
			                                 <A id='chotphieu<%= dmhList.getString("dmhId") %>'  href="">
			                                 <img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" 
										      border="0" onclick="if(!confirm('Bạn có muốn chốt phiếu này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpPhieuThanhToanSvl?userId=<%=userId%>&chot=<%= dmhList.getString("dmhId")%>');}"  >
										    </A>
									   <%} %>
									    
									    
		                            <%} %>		                            
		                            <%if(quyen[3]!=0){ %>
		                            	<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>">
		                            			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                				<%} %>
                                    <%if(quyen[1]!=0){ %>
                                		<A href = "../../ErpPhieuThanhToanSvl?userId=<%=userId%>&delete=<%= dmhList.getString("dmhId") %>"><img src="../images/Delete20.png" width="20" height="20" border=0 
                                		alt="Xóa Quản lý mua hàng" title="Xóa Quản lý mua hàng" onclick="if(!confirm('Bạn có muốn xóa Đề nghị thanh toán này?')) return false;"></A>&nbsp;
                                	<%} %>
                                		                							
		                         <% } else{ if(tt.equals("1")){ %>
		                         		<%if(quyen[3]!=0){ %>
		                            	<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            	
		                            	<%} %> 
		                            	
		                            	 <% if(quyen[4]!=0 && dmhList.getString("HTTT_FK").equals("100004") && ISTP.equals("1")){ %>
			                                 <A id='unchot<%= dmhList.getString("dmhId") %>'  href="">
			                                 <img src="../images/unChot.png" alt="Mở Chốt" width="20" height="20" title=" Mở Chốt" 
										      border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt phiếu này?')) {return false ;}else{ processing('<%="unchot"+dmhList.getString("dmhId")%>' , '../../ErpPhieuThanhToanSvl?userId=<%=userId%>&unchot=<%= dmhList.getString("dmhId")%>');}"  >
										    </A>
									   <%} %>
		                            	
		                         <% }else { %>
		                         		<%if(quyen[3]!=0){ %>
		                            	<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>">
		                            			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                				<%} %>
		                            	<%} 								
		                          }%>
				                    </TD>
				                </TR>
				                    		
	                		<%  m++; }
	                		} %>
					
						
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="14" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
<script type="text/javascript">
	 <%for(int k=0; k < m; k++) {%>
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 300, "click");
	  <%}%>	  
</script>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>

<%
    if(htList != null ){ htList.clear(); htList = null; }
	if(dmhList != null ){ dmhList.close(); dmhList = null; }
	if(nguoitaoRs != null ){ nguoitaoRs.close(); nguoitaoRs = null; }
	if(dvthList != null ){ dvthList.close(); dvthList = null; }
	if(lspList != null ){ lspList.close(); lspList = null; }

	if(obj!= null){ obj.DBclose(); obj = null; }
	session.setAttribute("obj",null);
	session.setAttribute("backAttribute",obj);
	}
%>