<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.tamung.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		IDuyettamung obj = (IDuyettamung)session.getAttribute("ddmhBean"); %>
<% ResultSet dvthList = (ResultSet)obj.getDvthList() ; %>

<% ResultSet lspList = (ResultSet)obj.getLspList(); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>

<% ResultSet polist = (ResultSet)obj.getPoList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_DuyettamungSvl","",userId);
%>
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TraphacoERP - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">

    <script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
   
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
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["erpDmhForm"].submit();
		}
			
		setTimeout(replaces, 300);
	}
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
	 
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].dvthId.value = "";
	    document.forms["erpDmhForm"].ngaytao.value = "";
	    document.forms["erpDmhForm"].maDNTU.value = "";
	    document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].nguoitao.value = "";
	    document.forms["erpDmhForm"].submit();
	 }

	 function duyetformTP(id){
		 
		 if(!confirm('Bạn có muốn duyệt phiếu này?')) 
		 {
			 return  ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].action.value = "duyetTP";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
 	function duyetformKTV(id){
		 
		 if(!confirm('Bạn có muốn duyệt phiếu này?')) 
		 {
			 return ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].action.value = "duyetKTV";
	    document.forms["erpDmhForm"].submit();
			 
	 }
 	
 	function duyetformKTT(id){
		 
		 if(!confirm('Bạn có muốn duyệt phiếu này?')) 
		 {
			 return ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].action.value = "duyetKTT";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
	function boChot(id){
		if(!confirm('Bạn có muốn bỏ chốt phiếu này?')) 
		{
		 return  ;
		}
		 
		document.forms["erpDmhForm"].Id.value = id;
		document.forms["erpDmhForm"].action.value = "boChot";
		document.forms["erpDmhForm"].submit();
	}
	
	function boform(id){
		 
		 if(!confirm('Bạn có muốn bỏ duyệt phiếu này?')) 
		 {
			 return ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms['erpDmhForm'].lydomo.value = document.getElementById("lydomo" + id).value;
	    document.forms["erpDmhForm"].action.value = "boduyet";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	
	function xoaform(id){
		 
		 if(!confirm('Bạn có muốn xóa duyệt phiếu này?')) 
		 {
			 return  ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms['erpDmhForm'].lydoxoa.value = document.getElementById("lydoxoa" + id).value;
	    document.forms["erpDmhForm"].action.value = "xoaphieu";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	
	
	function suaform(id){
		 
		 if(!confirm('Bạn có muốn sửa phiếu này?')) 
		 {
			 return  ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms['erpDmhForm'].lydosua.value = document.getElementById("lydosua" + id).value;
	    document.forms["erpDmhForm"].action.value = "suaphieu";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function boChotNhanVien(id)
		{
			if(!confirm('Bạn có muốn bỏ chốt phiếu này?')) 
			{
			 return  ;
			}
			 
			document.forms["erpDmhForm"].Id.value = id;
			document.forms["erpDmhForm"].action.value = "boChotNhanVien";
			document.forms["erpDmhForm"].submit();
		}
	 
	 function boChotTruongPhong(id)
		{
			if(!confirm('Bạn có muốn bỏ chốt phiếu này?')) 
			{
			 return  ;
			}
			 
			document.forms["erpDmhForm"].Id.value = id;
			document.forms["erpDmhForm"].action.value = "boChotTruongPhong";
			document.forms["erpDmhForm"].submit();
		}
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../Erp_DuyettamungSvl">
<input type="hidden" name="ctyId" value="<%= obj.getCtyId() %>" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="Id" value="" >

<input type="hidden" name="action" value="1" >
<input type="hidden" name="lydomo" value="">
<input type="hidden" name="lydoxoa" value="">
<input type="hidden" name="lydosua" value="">
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">Quản lý công nợ &gt; Công nợ phải trả &gt; Duyệt đề nghị tạm ứng
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
									<TD class="plainlabel" width="15%">Từ ngày</TD>
									<TD class="plainlabel" width="30%">
										<input type="text" class="days" id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" onchange="submitform()" /></TD>
									<TD class="plainlabel" width="15%">Đến ngày</TD>
									<TD class="plainlabel">
										<input type="text" class="days" id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" onchange="submitform()" /></TD>
								</TR>
                    <TR>
                        <TD class="plainlabel" valign="middle"  width="15%" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle" width="30%">
                            <select name="dvthId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("DVTHID").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("DVTHID") %>" selected="selected" ><%= dvthList.getString("DVTH") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("DVTHID") %>" ><%= dvthList.getString("DVTH") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>
                        
                        <TD class="plainlabel" valign="middle" >Người tạo </TD>
                        <TD class="plainlabel" valign="middle" colspan = 3 >
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
                         <TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        <TD class="plainlabel" valign="middle">
		                    <select name="nccId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			  if( nccList.getString("pk_seq").equals(obj.getNccId() )){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("tenncc") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("tenncc") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>  
                        </TD>     
                        <TD class="plainlabel" valign="middle">Trạng thái</TD>
									<TD class="plainlabel" valign="middle">
									<select name="trangthai" id="trangthai" style="width: 200px" onchange="submitform()">
									<%
										String selected[] = {"", "", "", "", "", "", ""};
										String trangThai = obj.getTrangThai();							
											if(trangThai.equals("")){
												selected[0] = "selected=\"selected\"";
											} else if(trangThai.equals("isdachot")) {
												selected[1] = "selected=\"selected\"";
											} else if(trangThai.equals("istp")){
												selected[2] = "selected=\"selected\"";
											} else if(trangThai.equals("isktv")){
												selected[3] = "selected=\"selected\"";
											} else if(trangThai.equals("isktt")){
												selected[4] = "selected=\"selected\"";
											} else if(trangThai.equals("1")){
												selected[5] = "selected=\"selected\"";
											} else if(trangThai.equals("2")){
												selected[6] = "selected=\"selected\"";
											}
									%>
											<option value="" <%=selected[0] %>></option>
											<option value="isdachot" <%=selected[1] %>>Đã chốt</option>
											<option value="istp" <%=selected[2] %>>Đã duyệt (Trưởng phòng)</option>
											<option value="isktv" <%=selected[3] %>>Đã duyệt (Kế toán gán mã chi phí)</option>
											<option value="isktt" <%=selected[4] %>>Đã duyệt (Kế toán trưởng)</option>
											<option value="1" <%=selected[5] %>>Đã hoàn tất</option>
											<option value="2" <%=selected[6] %>>Đã xóa</option>
											
									</select></TD>                                
                    </TR>
                     <TR>
                    	<TD class="plainlabel" valign="middle" >Mã ĐNTU</TD>	
                    	<TD class="plainlabel" valign="middle" colspan="3">
                    		<input type="text" name="maDNTU" id="maDNTU"  value="<%= obj.getMaDNTU() %>"   onchange="submitform()"/>
                    	 </TD>
                    	
                    	
                    	
                    	
                    	<%-- <TD class="plainlabel" valign="middle" >Ngày tạo</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" class="days" name="ngaytao" id="ngaytao"  value="<%= obj.getNgaytao() %>" maxlength="10"  onchange="submitform()"/>
                    	 </TD> --%>
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Duyệt đề nghị tạm ứng </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="15%">Duyệt</TH>
	                    <TH align="center" width="10%">Trạng thái</TH>	
	                    <TH align="center" width="10%">Mã ĐNTU</TH>		                    
	                    <TH align="center" width="10%">NCC/Nhân viên</TH>	                               
	                    <TH align="center" width="9%">Tổng tiền</TH>
	                    <TH align="center" width="9%">Ngày</TH>
	                    <TH align="center" width="9%">Người tạo</TH>	                    
	                </TR>
	                
		<%	try
			{
				int m = 0;
				String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
				String mhId = "";
				String mhId_bk = "";
				String trangthai = "";			
				
				if (polist != null) {
					while(polist.next())
					{
						mhId = polist.getString("MHID");
						trangthai = polist.getString("TRANGTHAI");
						
						String ISDACHOT = polist.getString("ISDACHOT");
	            		String ISTP = polist.getString("ISTP");
	            		String ISKTV = polist.getString("ISKTV");
	            		String ISKTT = polist.getString("ISKTT");
	            		
						
								if (m % 2 != 0) { %>						
										<TR class= <%=lightrow%> >
								<%  } else { %>
										<TR class= <%= darkrow%> >
								<% } %>
								<TD align = "center" > 
	
		<% 					
							String daduyet = obj.getDaduyet(polist.getString("MHID"));
							
							 if(polist.getString("TRANGTHAI").equals("0"))
							 { %>			
									<% if(ISTP.equals("0")) { %>
									 	<a  href="javascript:duyetformTP(<%= polist.getString("MHID") %>)">
												<img  alt="Duyệt" style="top: -4px;" src="../images/Chot.png" alt="">
										</a>&nbsp;
									<%} else { %>
									
										<% if(ISKTV.equals("0") || ISKTT.equals("0")) { %>
											<a  href="javascript:duyetformKTV(<%= polist.getString("MHID") %>)">
													<img  alt="Duyệt" style="top: -4px;" src="../images/Active.png" alt="">
											</a>&nbsp;
										<%} else 
										{ %>
											<a  href="javascript:duyetformKTT(<%= polist.getString("MHID") %>)">
													<img  alt="Duyệt" style="top: -4px;" src="../images/hoantat.gif" alt="">
											</a>&nbsp;
											
										<% }
									
									}%>
									
									 <A id='<%= polist.getString("MHID") %>' href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&update=<%= polist.getString("MHID") %>" rel="subcontent<%="suattid" + polist.getString("MHID") %>" >
				                              	 		<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" border=0 >
				                     </A>
	                           		
	                           		<DIV id="subcontent<%="suattid" + polist.getString("MHID") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
											 background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
						                    <table width="98%" align="center" cellspacing="1" >
						                        <tr  >
						                            <td align="center" style="font-size: 10pt">Lý do sửa</td>
						                        </tr>
						                        <tr >
						                            <td align="center" style="font-size: 10pt">
						                            	<input type="text" id="<%="lydosua" + polist.getString("MHID") %>" style="width: 100%" />
						                            </td>
						                        </tr>
						                        
						                    </table>
						        					                    
						                    <div align="right">
						                    							                    
						                     	<a href="javascript:suaform('<%= polist.getString("MHID") %>');" style="color: red; font-weight: bold;">Xác nhận sửa</a>
						                     
						                     	&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="suattid" + polist.getString("MHID") %>')" style="font-weight: bold;" >Đóng lại</a>
						                    </div>
							        </DIV> 
						            <script type="text/javascript">
										dropdowncontent.init('<%= polist.getString("MHID") %>', "right-top", 300, "click");
									</script>
	                             							 
								 	&nbsp;
										
								 	<A id='xoa<%= polist.getString("MHID") %>' href="" rel="subcontent<%="xoattid" + polist.getString("MHID") %>" >
			                              	 		<img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 >
			                            </A>
	                              	 	<DIV id="subcontent<%="xoattid" + polist.getString("MHID") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
											 background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
						                    <table width="98%" align="center" cellspacing="1" >
						                        <tr  >
						                            <td align="center" style="font-size: 10pt">Lý do xóa </td>
						                        </tr>
						                        <tr >
						                            <td align="center" style="font-size: 10pt">
						                            	<input type="text" id="<%="lydoxoa" + polist.getString("MHID") %>" style="width: 100%" />
						                            </td>
						                        </tr>
						                        
						                    </table>
						        					                    
						                    <div align="right">
						                    							                    
						                     	<a href="javascript:xoaform('<%= polist.getString("MHID") %>');" style="color: red; font-weight: bold;">Xác nhận xóa phiếu</a>
						                     
						                     	&nbsp;&nbsp;|&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="xoattid" + polist.getString("MHID") %>')" style="font-weight: bold;" >Đóng lại</a>
						                    </div>
							            </DIV> 
							            <script type="text/javascript">						            
											dropdowncontent.init('xoa<%= polist.getString("MHID") %>', "right-top", 300, "click");
										</script>
										
										&nbsp;                             
		                            	<A href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&display=<%= polist.getString("MHID") %>">
		                            	<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;&nbsp;&nbsp;&nbsp;
	                            	
	<!-- 								Bỏ chốt -->
									<%-- <% if(ISTP.equals("0")) { %>
									<a  href="javascript:boChot(<%= polist.getString("MHID") %>)">
											<img  alt="Duyệt" style="top: -4px;" src="../images/unChot.png" alt="Bỏ chốt">
									</a>&nbsp;
	                           		<%} %>	 --%>
	                           		<% if(ISTP.equals("0")) { %>
								<a  href="javascript:boChotNhanVien(<%= polist.getString("MHID") %>)">
										<img  alt="Duyệt" style="top: -4px;" src="../images/unChot.png" alt="Bỏ chốt Nhân Viên">
								</a>&nbsp;
                           		<%} %>	 
                           		
                           		<% if(ISTP.equals("1")&&ISKTV.equals("0")) { %>
								<a  href="javascript:boChotTruongPhong(<%= polist.getString("MHID") %>)">
										<img  alt="Duyệt" style="top: -4px;" src="../images/unChot.png" alt="Bỏ chốt Trưởng Phòng">
								</a>&nbsp;
                           		<%} %>	  
	                           		                          	
								 <%
								}							
								
								else if(polist.getString("TRANGTHAI").equals("1"))
														
								{ %>
									 <%if(quyen[3]!=0){ %>
				                            	<A href = "../../ErpTamUngUpdateSvl?userId=<%=userId%>&display=<%= polist.getString("MHID") %>">
				                            	<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;&nbsp;&nbsp;&nbsp;
				                            	
									 <%} %> 
								<%} %>
			                            	
								</TD>
								<TD align="left">
											<%
												if(polist.getString("TRANGTHAI").equals("0"))
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
														trangthai = "Đã duyệt (Kế toán gán mã chi phí)";
													}
													
													if(ISKTT.equals("1"))
													{
														trangthai = "Đã duyệt (Kế toán trưởng)";
													}
													
												}
												else
												{
													if(polist.getString("TRANGTHAI").equals("1"))
													{													

														if(ISKTT.equals("1"))
														{
															trangthai = "Đã duyệt (Kế toán trưởng)";
														} else 
														trangthai = "Đã hoàn tất";
													}
													else
													{
														if(polist.getString("TRANGTHAI").equals("2"))
															trangthai = "Đã xóa";
														else 
															trangthai = "Đã hủy";
													}
												}
											%>
											<%= trangthai %>
										</TD>
								<TD align = "center"><%= polist.getString("SOCHUNGTU") %> </TD>
								<TD align = "center" ><%= polist.getString("NCC")  %> </TD>
								<TD align = "center" ><%= formatter.format(Double.parseDouble(polist.getString("TONGTIENAVAT")))  %> </TD>
								
								<TD align = "center"><%= polist.getString("NGAY") %> </TD>
								<TD align = "center"><%= polist.getString("NGUOITAO") %> </TD>
								<%-- <TD align = "right" colspan = 3><%= "( Đã duyệt bởi : " + daduyet + ")"  %> </TD> --%>
							</TR>			
	
					<%	
						m++;
					}}
				}catch(Exception e){ e.printStackTrace();}%>
	                
				</TABLE>
            </fieldset>
        </div>
    </div>  
	</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>

<% 
try{
if(polist!=null){
	polist.close();
}
if(dvthList!=null){
	dvthList.close();
}
if(nccList!=null){
	nccList.close();
}
if(polist!=null){
	polist.close();
}
obj.DBclose();
session.setAttribute("ddmhBean",null);
}catch(Exception er){
	
}
	}
%>