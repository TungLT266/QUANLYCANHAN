<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuthanhtoan.*" %>
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
		IDuyetdonmuahang obj = (IDuyetdonmuahang)session.getAttribute("ddmhBean"); %>
  <% ResultSet dvthList = obj.getDvthList() ; %>

<% ResultSet lspList = obj.getLspList(); %>
<% ResultSet nccList = obj.getNccList(); %> 
<% ResultSet htttList = obj.getHtttList(); %> 
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>
<% ResultSet polist = obj.getPoList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_DuyetthanhtoanSvl","",userId);
	obj.setNextSplittings(); 
	System.out.print("\n");

%>


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
	
    <script language="javascript" src="../scripts/datepicker.js"></scrript>
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
	 
	    document.forms["erpDmhForm"].ngaymua.value = "";
	    document.forms["erpDmhForm"].denNgay.value="";
	    document.forms["erpDmhForm"].trangThai.value = "";
	    document.forms["erpDmhForm"].maDMH.value = "";
	    document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].nguoitao.value = "";
	    document.forms["erpDmhForm"].machungtu.value = "";
	    document.forms["erpDmhForm"].tongTien.value = "";
	    
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function duyetformTP(id){
		 
		 if(!confirm('Bạn có muốn duyệt phiếu này?')) 
		 {
			 return ;
		 }
		 
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].action.value = "duyetTP";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
	function duyetformKTV(id){
			 
			 if(!confirm('Bạn có muốn duyệt phiếu này?')) 
			 {
				 return  ;
			 }
			 
		    document.forms["erpDmhForm"].Id.value = id;
		    document.forms["erpDmhForm"].action.value = "duyetKTV";
		    document.forms["erpDmhForm"].submit();
				 
		 }
		 
	function duyetformKTT(id){
		if(!confirm('Bạn có muốn duyệt phiếu này?')) 
		{
			 return  ;
		}
		 
		document.forms["erpDmhForm"].Id.value = id;
		document.forms["erpDmhForm"].action.value = "duyetKTT";
		document.forms["erpDmhForm"].submit();
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
	
	function boform(id){
		 if(!confirm('Bạn có muốn bỏ duyệt phiếu này?')) 
		 {
			 return  ;
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
	 

	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../Erp_DuyetthanhtoanSvl">
<input type="hidden" name="ctyId" value="<%= obj.getCtyId() %>" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="Id" value="" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> 
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
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">Quản lý công nợ &gt; Công nợ phải trả &gt; Duyệt đề nghị thanh toán
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
                             <TD class="plainlabel" valign="middle" >Từ ngày</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" class="days" name="ngaymua" id="ngaymua"  value="<%= obj.getNgaymua() %>" maxlength="10"  onchange="submitform()" style="border-radius:4px; height: 20px;"/>
                    	 </TD>
                    	 <TD class="plainlabel" valign="middle" >Đến ngày</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" class="days" name="denNgay" id="denNgay"  value="<%= obj.getDenNgay() %>" maxlength="10"  onchange="submitform()" style="border-radius:4px; height: 20px;"/>
                    	 </TD>                 
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
                        <TD class="plainlabel" valign="middle" >
                            <select  id="nguoitao" name="nguoitao" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(nguoitaoRs != null)
	                        		{
	                        			while(nguoitaoRs.next())
	                        			{  
	                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitao())){ %>
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
                    	<TD class="plainlabel" valign="middle" >Mã ĐMH</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" name="maDMH" id="maDMH"  value="<%= obj.getMaDMH() %>"   onchange="submitform()" style="border-radius:4px; height: 20px;" />
                    	 </TD>
                    	<TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        <TD class="plainlabel" valign="middle" >
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
                        
                        
                        
                    	
                    	 
                    </TR>
                    <TR>
                    	<td class="plainlabel" valign="middle">Trạng thái</td>
                    	<TD class="plainlabel" valign="middle"  >
                    	
                            <select name="trangThai" id="trangThai" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	
                            	<% if(obj.getTrangThai().equals("0")) { %>
                            		<option value="0" selected="selected" >Chưa chốt</option>
                            	<% } else { %> 
                            		<option value="0" >Chưa chốt</option>
                            	<%  } %>
                            	<% if(obj.getTrangThai().equals("1")) { %>
                            		<option value="1" selected="selected" >Chưa duyệt (Đã chốt)</option>
                            	<% } else { %> 
                            		<option value="1" >Chưa duyệt (Đã chốt)</option>
                            	<%  } %>
                            	<% if(obj.getTrangThai().equals("2")) { %>
                            		<option value="2" selected="selected" >Đã duyệt(Trưởng Phòng)</option>
                            	<% } else { %> 
                            		<option value="2" >Đã duyệt(Trưởng Phòng)</option>
                            	<%  } %>
                            	<% if(obj.getTrangThai().equals("3")) { %>
                            		<option value="3" selected="selected" >Đã duyệt(Kế toán gán mã chi phí)</option>
                            	<% } else { %> 
                            		<option value="3" >Đã duyệt(Kế toán gán mã chi phí)</option>
                            	<%  } %>
                            	 <% if(obj.getTrangThai().equals("4")) { %>
                            		<option value="4" selected="selected" >Đã duyệt(Kế toán trưởng)</option>
                            	<% } else { %>
                            		<option value="4" >Đã duyệt(Kế toán trưởng)</option>
                            	 <% }  %>
                            	  <% if(obj.getTrangThai().equals("5")) { %>
                            		<option value="5" selected="selected" >Đã hoàn tất</option>
                            	<% } else { %>
                            		<option value="5" >Đã hoàn tất</option>
                            	 <% }  %>
                            	 <% if(obj.getTrangThai().equals("6")) { %>
                            		<option value="6" selected="selected" >Đã xóa</option>
                            	<% } else { %>
                            		<option value="6" >Đã xóa</option>
                            	 <% }  %>
                            	  <% if(obj.getTrangThai().equals("7")) { %>
                            		<option value="7" selected="selected" >Đã hủy</option>
                            	<% } else { %>
                            		<option value="7" >Đã hủy</option>
                            	 <% }  %>
                            	
                            </select>
                    	 </TD>
                    	 <TD class="plainlabel" valign="middle" >Hình thức thanh toán</TD>
                        <TD class="plainlabel" valign="middle" >
		                    <select name="htttId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(htttList != null)
	                        	
	                        		{
	                        			while(htttList.next())
	                        			{  
	                        			  if( htttList.getString("pk_seq").equals(obj.getHtttid() )){ %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ten") %></option>
	                        		 <% } } htttList.close();
	                        		}
	                        	%>
	                        	
                            </select>  
                        </TD>   
                    	 
                    	
                    </TR>
                    
                    <tr>
                    	<TD class="plainlabel" valign="middle">Tổng tiền </TD>
                        <TD class="plainlabel" valign="middle"  >
                            <input type="text" name="tongTien" value="<%= obj.getTongTien() %>" onchange="submitform()">
                        </TD> 
                        
                        <TD class="plainlabel" valign="middle">Số chứng từ</TD>
                        <TD class="plainlabel" valign="middle"  >
                            <input type="text" name="machungtu" value="<%= obj.getMachungtu() %>" onchange="submitform()">
                        </TD> 
                    </tr>
                    
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
            	<legend><span class="legendtitle">Duyệt đề nghị thanh toán </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="15%">Duyệt</TH>
	                     <TH align="center" width="15%">Trạng thái</TH>
	                    <TH align="center" width="10%">Số chứng từ</TH>	           
	                    <TH align="center" width="10%">Mã ĐMH</TH>	                    
	                    <TH align="center" width="9%">Tổng tiền</TH>
	                    <TH align="center" width="9%">Ngày</TH>
	                    <TH align="center" width="9%">Đơn vị thực hiện</TH>
	                    <TH align="center" width="9%">Người tạo</TH>
	                    <TH align="center" width="9%">Đối tượng</TH>
	                    	                                       
	                   <!--  <TH align="center" width="9%">Mặt hàng</TH>
	                    <TH align="center" width="9%">Số lượng</TH>
	                    <TH align="center" width="9%">Đơn giá</TH>
	                    <TH align="center" width="9%">Thành tiền</TH>	   -->                  
	                </TR>
	                
		<%	try
			{
				int m = 0;
				String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
				String mhId = "";
				String mhId_bk = "";
				String trangthai = "";				
				
				while(polist.next())
				{
					mhId = polist.getString("MHID");
					trangthai = "";
					
					if(!mhId.equals(mhId_bk)){
						
						String daduyet = obj.getDaduyet(polist.getString("MHID"));
						String vuotNS = polist.getString("VUOTNGANSACH");	
						
						String ISDACHOT = polist.getString("ISDACHOT");
                		String ISTP = polist.getString("ISTP");
                		String ISKTV = polist.getString("ISKTV");
                		String ISKTT = polist.getString("ISKTT");
                		int quyentp = polist.getInt("quyentp");
						int quyentpkt = polist.getInt("quyentpkt");
						int quyennvkt = polist.getInt("quyennvkt");
						int quyengd = polist.getInt("quyengd");
				%>				
							<%//	m = 0;
							if (m % 2 != 0) { %>						
									<TR class= <%=lightrow%> >
							<%  } else { %>
									<TR class= <%= darkrow%> >
							<%  } %>
																
							<TD align = "center" > 
							 <%
							 
							 if(polist.getString("TRANGTHAI").equals("0")  )
							 { %>
								 
								 <% if(ISTP.equals("0")) { %>
								 	<a  href="javascript:duyetformTP(<%= polist.getString("MHID") %>)">
											<img  alt="Duyệt" style="top: -4px;" src="../images/Chot.png" alt="">
									</a>&nbsp;
								<%} else { %>
								
									<% if(ISKTV.equals("0")) { %>
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
								 
								 <A id='<%= polist.getString("MHID") %>' href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&update=<%= polist.getString("MHID") %>&duyetdn=1" rel="subcontent<%="suattid" + polist.getString("MHID") %>" >
			                        <img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" border=0 >
			                     </A>
					                            
					            <% if(!ISTP.equals("0") && ISKTV.equals("0")) { %>    		
                           		<DIV id="subcontent<%="suattid" + polist.getString("MHID") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
										 background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
					                    <table width="98%" align="center" cellspacing="1" >
					                        <tr>
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
					                     
					                     	&nbsp;&nbsp;|&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="suattid" + polist.getString("MHID") %>')" style="font-weight: bold;" >Đóng lại</a>
					                    </div>
						        </DIV> 
					            <script type="text/javascript">
									dropdowncontent.init('<%= polist.getString("MHID") %>', "right-top", 300, "click");
								</script>
								<%} %>        
                            	<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&display=<%= polist.getString("MHID") %>&duyetdn=1">
                            	<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;&nbsp;&nbsp;&nbsp;
																
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
								
								
<!-- 								Bỏ chốt -->
								<% if(ISTP.equals("0")&&quyentp>=1) { %>
								<a  href="javascript:boChotNhanVien(<%= polist.getString("MHID") %>)">
										<img  alt="Duyệt" style="top: -4px;" src="../images/unChot.png" alt="Bỏ chốt Nhân Viên">
								</a>&nbsp;
                           		<%} %>		
                           		
                           		<% if(ISTP.equals("1")&&ISKTV.equals("0")&&(quyennvkt>=1||quyentpkt>=1)) { %>
								<a  href="javascript:boChotTruongPhong(<%= polist.getString("MHID") %>)">
										<img  alt="Duyệt" style="top: -4px;" src="../images/unChot.png" alt="Bỏ chốt Trưởng Phòng">
								</a>&nbsp;
                           		<%} %>		
							<%} 
											 
							 else if(polist.getString("TRANGTHAI").equals("1") )
							 {
								  %>
	                            	<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&display=<%= polist.getString("MHID") %>&duyetdn=1">
	                            	<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;&nbsp;&nbsp;&nbsp;
						 		 <% 
							 }
							 %>
		                            	
		                            	
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
												// Hiện tại kế toán viên có quyền của kế toán trưởng nên hiển thị
												// kế toán trưởng luôn
												trangthai = "Đã duyệt (Kế toán trưởng)";
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
							
							<TD align = "center"><%= polist.getString("machungtu") %> </TD>
							<TD align = "center"><%= polist.getString("SOCHUNGTU") %> </TD>
							<% 
							String TONGTIEN="0";
							if(polist.getString("TONGTIENAVAT")!=null && polist.getString("TONGTIENAVAT")!="") 
							{
								TONGTIEN= polist.getString("TONGTIENAVAT");
							}%>
							<TD align = "center" ><%= formatter.format(Double.parseDouble(TONGTIEN))  %> </TD>
							<TD align = "center"><%= polist.getString("NGAY") %> </TD>
							<TD align = "center"><%= polist.getString("DVTH") %> </TD>
							<TD align = "center"><%= polist.getString("NGUOITAO") %> </TD>
							<TD align = "left" colspan = 2><%= polist.getString("NCC")  %> </TD>
							<!-- <TD align = "right" colspan = 3></TD> -->
						</TR>

						<%//	m = 0;
							if (m % 2 != 0) { %>					
								<TR class= <%=lightrow%> >
							<%  } else { %>
									<TR class= <%= darkrow%> >
							<%  } %>
							<!-- <TD align = "center">&nbsp;</TD>
							<TD align = "center">&nbsp;</TD> -->
						<%-- 	<TD align = "right" colspan = 6><%= polist.getString("MA") + " - " + polist.getString("SP") %></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))  %></TD> --%>
							<!-- <TD> </TD> -->
						</TR>			

				<%	}else{%>

					<%	m--; 
					if (m % 2 != 0) {%>						
							<TR class= <%=lightrow%> >
					<%  } else { %>
							<TR class= <%= darkrow%> >
					<%  } %>
							<!-- <TD align = "center">&nbsp;</TD>
							<TD align = "center">&nbsp;</TD> -->
							<%-- <TD align = "right" colspan = 6><%= polist.getString("MA") + " - " + polist.getString("SP")%></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))%></TD>			 --%>				
							<!-- <TD> </TD> -->
						</TR>			
			
					<%	}
					mhId_bk = polist.getString("MHID");
					m++;
		
					}
			}catch(Exception e){ e.printStackTrace();}%>
	                	<tr class="tbfooter">
									<TD align="center" valign="middle" colspan="13" class="tbfooter">
										<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
										src="../images/first.gif" style="cursor: pointer;"
										onclick="View('erpDmhForm', 1, 'view')"> &nbsp; <%}else {%>
										<img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%} %>
										<% if(obj.getNxtApprSplitting() > 1){ %> <img alt="Trang Truoc"
										src="../images/prev.gif" style="cursor: pointer;"
										onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) -1, 'view')">
										&nbsp; <%}else{ %> <img alt="Trang Truoc"
										src="../images/prev_d.gif"> &nbsp; <%} %> <%
														int[] listPage = obj.getNextSplittings();
														for(int i = 0; i < listPage.length; i++){
													%> <% 
													
													System.out.println("Current page:" + obj.getNxtApprSplitting());
													System.out.println("List page:" + listPage[i]);
													
													if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
										style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
										<%}else{ %> <a
										href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %> <input type="hidden" name="list"
										value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
										&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
										style="cursor: pointer;"
										onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) +1, 'view')">
										&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
										src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
										<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
										<img alt="Trang Cuoi" src="../images/last.gif"
										style="cursor: pointer;"
										onclick="View('erpDmhForm', -1, 'view')"> &nbsp; <%} %>
									</TD>
								</tr>
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
	}catch(Exception er){
		er.printStackTrace();	
	}
	obj.DBclose();
	session.setAttribute("ddmhBean",null);
	session.setAttribute("backAttribute",obj);
}
%>