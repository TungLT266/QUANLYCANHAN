<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% IErpYeucauxuatkhoNppList obj = (IErpYeucauxuatkhoNppList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet rskhoid=obj.getRsrskhoid();
ResultSet kenhbanhang = obj.getKbhRs();
ResultSet khuvuc = obj.getKvRs();
ResultSet sanpham = obj.getSpRs();
%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
   NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

   NumberFormat formatterXK = new DecimalFormat("#,###,###"); %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpPhieugiaohangNppSvl","&loai=1",userId);
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
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	 <script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
	 function submitform()
	 {   
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
<!-- <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpPhieugiaohangNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
    	<% if(obj.getPhanloai().equals("0")) { %>
        	&nbsp;Quản lý bán hàng > Bán hàng cho NPP > Phiếu giao hàng
        <% } else if(obj.getPhanloai().equals("1")) { %>
        	&nbsp;Quản lý bán hàng > Bán hàng thầu > Phiếu giao hàng
        <% } else { %>
        	&nbsp;Quản lý bán hàng > Bán hàng không thầu > Phiếu giao hàng
        <% } %>
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
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="130px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                     	<TD class="plainlabel" width="100px">
                     		Mã / tên đối tượng
                     	</TD>
                        <TD class="plainlabel">
                        	<input type="text" name="khTen" value="<%= obj.getKhten() %>"  onchange="submitform();" />
                        	
                            <%-- <select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value="">All</option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select> --%>
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Loại đơn hàng </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="loaidonhang" class="select2"  style="width: 200px;" onchange="submitform();"  >
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
                     
                    </TR>  
                     
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="1" >
                           <select name="trangthai" class="select2"  style="width: 200px;" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Chưa chốt</option>
								  	<option value="1">Đã chốt</option>
								  	<option value="2">Đã hủy</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="2">Đã hủy</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Đã chốt</option>
								  	<option value="2" selected>Đã hủy</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Đã chốt</option>
								  	<option value="2">Đã hủy</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD>  
                       <TD class="plainlabel" >Mã số phiếu</TD>
                        <TD class="plainlabel">
                            <input type="text" name="masophieu" value="<%= obj.getMaso() %>"  onchange="submitform();" />
                        </TD>                                        
                    </TR>  
                    
                    <TR>
                        <TD class="plainlabel" >Số đơn hàng</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sodonhang" value="<%= obj.getSodonhang() %>"  onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel">Số hóa đơn</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sohoadon" value="<%= obj.getSohoadon() %>" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     
                    <TR>
                        <TD class="plainlabel" >Người giao hàng</TD>
                        <TD class="plainlabel">
                            <input type="text" name="nguoigiaohang" value="<%= obj.getNguoigiao() %>"  onchange="submitform();" />
                        </TD>
                    
                       <TD  class="plainlabel">Kho hàng hóa</TD>
							<TD  class="plainlabel" >
							<select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(rskhoid!=null){ while (rskhoid.next()){ %>
								<option value="<%=rskhoid.getString("pk_seq")%>" <%if(rskhoid.getString("pk_seq").equals(obj.getKhohh())){%> selected="selected" <%}%>><%=rskhoid.getString("ten") %>  </option>
								<%} }%>
								
							</select>
							</TD>  
                    </TR>
                       <TR>
                    <TD  class="plainlabel">Kênh</TD>
							<TD  class="plainlabel" >
							<select name="kbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(kenhbanhang!=null){ while (kenhbanhang.next()){ %>
								<option value="<%=kenhbanhang.getString("pk_seq")%>" <%if(kenhbanhang.getString("pk_seq").equals(obj.getKbhId())){%> selected="selected" <%}%>><%=kenhbanhang.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Khu vực</TD>
							<TD  class="plainlabel" >
							<select name="kvid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(khuvuc!=null){ while (khuvuc.next()){ %>
								<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getKvId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
                    <tr>
                        <TR>
                    <TD  class="plainlabel">Sản phẩm</TD>
							<TD  class="plainlabel" colspan= "4" >
							<select name="spid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(sanpham!=null){ while (sanpham.next()){ %>
								<option value="<%=sanpham.getString("pk_seq")%>" <%if(sanpham.getString("pk_seq").equals(obj.getSpId())){%> selected="selected" <%}%>><%=sanpham.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phiếu giao hàng </span>&nbsp;&nbsp;
            	<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
            	<thead>
					<TR class="tbheader">
	                    <TH align="center" style="width: 12%" >Mã chứng từ</TH>
	                    <TH align="center" style="width: 7%" >Ngày giao</TH>
	                    <TH align="center" style="width: 12%" >
	                    	<% if(obj.getPhanloai().equals("0")) { %>
                     			Nhà phân phối
                     		<% } else { %>
                     			Khách hàng
                     		<% } %>
	                    </TH>
	                    <TH align="center" style="width: 8%" >Kho xuất</TH>
	                    <TH align="center" style="width: 8%" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 8%" >Số đơn hàng</TH>
	                    <TH align="center" style="width: 7%" >Trạng thái</TH>
	                    <TH align="center" style="width: 7%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 11%" >Người tạo</TH>
	                    <TH align="center" style="width: 7%; display: none;" >Ngày sửa</TH>
	                    <TH align="center" style="width: 11%; display: none;" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
	                </thead>
	                <tbody>
					<%
						int m = 0;
                 		if(nhapkhoRs != null)
                 		{
                 			while(nhapkhoRs.next())
                 			{  	
                 				String msg="Mã số: " + nhapkhoRs.getString("PK_SEQ") + "</br> Ngày giao: " + nhapkhoRs.getString("ngayyeucau") +
                 						"</br>Khách hàng/ Nhà phân phối: " + nhapkhoRs.getString("nppTEN") + "</br>Kho xuất: " + nhapkhoRs.getString("khoTEN") +
                  						"</br> Số đơn hàng: " + nhapkhoRs.getString("ddhIds");
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center" onMouseover="ddrivetip('<%=msg %>', 300)"; onMouseout="hideddrivetip()"><%= nhapkhoRs.getString("MACHUNGTU") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYYEUCAU") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>  
		                    <TD ><%= nhapkhoRs.getString("khoTEN") %></TD>  
		                    <TD ><%= nhapkhoRs.getString("sohoadon") %></TD>
		                    <TD ><%= nhapkhoRs.getString("ddhIds") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("0")) //NPP TAO
		                    			trangthai = "Chưa chốt";
		                    		else if(tt.equals("1"))
		                    			trangthai = "Đã chốt";
		                    		else
		                    			trangthai = "Đã hủy";
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center" style="display: none;" ><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center" style="display: none;"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    
		                    	<%if(quyen[Utility.SUA]!=0){ %>
                                <A href = "../../ErpPhieugiaohangNppUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
 								<% } %>     
 							                           
                             	<%if(quyen[Utility.CHOT]!=0){ %>
                                <A href = "../../ErpPhieugiaohangNppSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt yêu cầu xuất kho này?')) return false;"></A>&nbsp;
								<% } %>                                     
                                
                                <%if(quyen[Utility.XOA]!=0){ %>
                              	<A href = "../../ErpPhieugiaohangNppSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa yêu cầu xuất kho này?')) return false;"></A>
                              	<% } %>
                              	                   									
		                    <%} else{ %>
		                    
		                    		 <%if(quyen[Utility.XEM]!=0){ %>
		                    			<A href = "../../ErpPhieugiaohangNppUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    		<% } %>
		                    		
		                    		 <%if( quyen[Utility.XOA]!=0 && tt.equals("1") ){ %>
	                              		<A href = "../../ErpPhieugiaohangNppSvl?userId=<%=userId%>&mochot=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getNppId() %>&loai=<%= obj.getPhanloai() %>"><img src="../images/unChot.png" alt="Mở chốt" title="Mở chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt phiếu giao hàng này?')) return false;"></A>
	                              	 <% } %>
		                    		
		                    		 <%if(tt.equals("1")){ %>

		                    <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
                       	 
                       	 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;  background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
               				<table width="100%" align="center">
					                  <tr>
				                           	<th width="10%">Nợ/Có</th>
				                            <th width="12%">Số hiệu TK</th>
				                            <th width="15%">Số tiền</th>
				                            <th width="30%">Đối tượng</th>
				                            <th width="15%">Trung tâm CP</th>	
				                            <th width="15%">Trung tâm DT</th>										                       
						              </tr>
                   
			                            <% 		ResultSet dinhkhoan = obj.Laydinhkhoan(nhapkhoRs.getString("PK_SEQ"));	
			                            		if(dinhkhoan != null){
					                        		while(dinhkhoan.next())
				                        			{
						                        		%>
						                        			<tr>
						                        				<td>
						                        					<input type="text" style="width:100%;text-align:center" readonly="readonly" name="no_co" value="<%= dinhkhoan.getString("NO_CO") %>" />
						                        				</td>
									                            <td>											                            	
									                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= dinhkhoan.getString("SOHIEU") %>" />
									                            </td>
									                            <td>
									                            	<%if(dinhkhoan.getString("SOTIEN").trim().length()>0){ %>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= formatterXK.format(Double.parseDouble(dinhkhoan.getString("SOTIEN"))) %>" />
									                            	<%} else {%>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= dinhkhoan.getString("SOTIEN") %>"  />
									                            	<%} %>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="doituong" value="<%= dinhkhoan.getString("DOITUONG") %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= dinhkhoan.getString("TTCP")  %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= dinhkhoan.getString("TTDT")  %>" />
									                            </td>
									                        </tr>
						                        <%  }
			                            		}
						                     %>
	
               							</table>
				                     <div align="right">
				                     	<label style="color: red" ></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
				                     </div>
           						</DIV>
		                    		
		                    	<script type="text/javascript">	
									dropdowncontent.init("ktlist<%=m %>", "left-bottom", 250, "click");
								</script>	
		                    		 
		                    	<%} %>
		                    <% } %>
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
                     </tbody>
                     <tfoot>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
						</TD>
					 </tr>
					 </tfoot>
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

</form>
<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table", 100);
	</script> 
</body>
</html><%}%>