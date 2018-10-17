<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.park.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpParkList obj = (IErpParkList)session.getAttribute("obj"); %>
<% ResultSet parkList = (ResultSet)obj.getParkList(); %>
<% ResultSet nhacungcapList = (ResultSet)obj.getNhacungcapList(); %>
<% ResultSet sanphamRs = (ResultSet)obj.getSanphamRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>
<% 
String sum = (String) session.getAttribute("sum");
Utility  util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoERP/index.jsp");
}else{
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpParkHoadonduyetSvl","",userId);
	 System.out.println("Quyen tac vu: ");
	 for (int i = 0; i < quyen.length; i++)
	 	System.out.println("i: " + quyen[i]);
obj.setNextSplittings(); %>
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
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
   
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
  	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
  	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 1000px;
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
    function duyetform(Id)
    {
    	 if(!confirm('Bạn có muốn duyệt hóa đơn này?')) 
    	 {
    		 return false ;
    	 }
    	 
    	 document.forms['erpParkForm'].parkid.value = Id;
    	 document.forms['erpParkForm'].action.value= 'chot';
    	 document.forms['erpParkForm'].submit();
    }

	function xuatExcel() {
		document.forms['erpParkForm'].action.value = 'excel';
		document.forms['erpParkForm'].submit();
	}
	 function submitform()
	 {   
		document.forms['erpParkForm'].action.value = "";
	    document.forms["erpParkForm"].submit();
	 }
	 
	 function newform()
	 {   
		document.forms["erpParkForm"].action.value = "Tao moi";
	    document.forms["erpParkForm"].submit();
	 }
	 
	 function clearform()
	 {   
	    document.forms["erpParkForm"].ngayghinhan.value = "";
	    document.forms["erpParkForm"].sopark.value = "";
	    document.forms["erpParkForm"].nhacungcap.value = "";
	    document.forms["erpParkForm"].loaihang.value = "";
	    document.forms["erpParkForm"].sohoadon.value = "";
	    document.forms["erpParkForm"].donViThucHienId.value = "";

		document.forms['erpParkForm'].action.value = "";

	    document.forms["erpParkForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpParkForm"].msg.value);
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
	   	$(document).ready(function() { $("select").select2(); });
	    
		</script>	
</head>
<body>
<form name="erpParkForm" method="post" action="../../ErpParkHoadonduyetSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="id" value='<%= obj.getId()%>'>
<input type="hidden" name="action" value="1" >
<input type="hidden" name="parkid" id="parkid"  >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ &gt; Công nợ phải trả &gt; Duyệt hóa đơn nhà cung cấp
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
                        <TD class="plainlabel" width="5%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="ngayghinhan" name="ngayghinhan" value="<%= obj.getNgayghinhan() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <td class="plainlabel" width="5%">Đến ngày </td>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="denNgay" name="denNgay" value="<%= obj.getDenNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        
                    </TR>
                    <TR>
                    	 <TD class="plainlabel" valign="middle" >Nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle">
                            <select id="nhacungcap" name="nhacungcap" onchange="submitform();" style="width:300px">
                            	<option value=""></option>
                            	<%
	                        		if(nhacungcapList  != null)
	                        		{
	                        			while(nhacungcapList .next())
	                        			{  
	                        			if( nhacungcapList.getString("pk_seq").equals(obj.getNcc())){ %>
	                        				<option value="<%= nhacungcapList .getString("pk_seq") %>" selected="selected" ><%=nhacungcapList.getString("ma")+"--"+ nhacungcapList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nhacungcapList .getString("pk_seq") %>" ><%= nhacungcapList.getString("ma")+"--"+nhacungcapList.getString("ten") %></option>
	                        		 <% } } nhacungcapList .close();
	                        		}
	                        	%>
                            </select>
                        </TD>  
                        <TD class="plainlabel" width="15%">Số (Park No.) </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sopark"  name="sopark" value="<%= obj.getId() %>"   onchange="submitform()" />
                        </TD>
                         
                    </TR>
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Loại hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="loaihang" id="loaihang" onChange="submitform();" style="width:300px">
								<% if(obj.getLoaihang().equals("1")) { %>
									<option value="1" selected>Hóa đơn NCC trong nước</option>
								  	<option value="2">Hóa đơn NCC chi phí dịch vụ</option>		
								  	<option value="0">Hóa đơn NCC nhập khẩu</option>								  	
									<option value=""> </option>
								<% } else { if(obj.getLoaihang().equals("2")){ %>
									<option value="1" >Hóa đơn NCC trong nước</option>
								  	<option value="2" selected>Hóa đơn NCC chi phí dịch vụ</option>		
								  	<option value="0">Hóa đơn NCC nhập khẩu</option>
									<option value=""> </option>
								<% } else { if(obj.getLoaihang().equals("0")){ %> 
									<option value="1" >Hóa đơn NCC trong nước</option>
								  	<option value="2" >Hóa đơn NCC chi phí dịch vụ</option>		
								  	<option value="0" selected>Hóa đơn NCC nhập khẩu</option>
									<option value=""> </option>
								<%} else { %>
									<option value="" selected="selected"> </option>
									<option value="1" >Hóa đơn NCC trong nước</option>
								  	<option value="2" >Hóa đơn NCC chi phí dịch vụ</option>		
								  	<option value="0">Hóa đơn NCC nhập khẩu</option>
								<% } } } %>
							</select>
                        </TD>  
                        <TD class="plainlabel" width="15%">Số hóa đơn </TD>
                        <TD class="plainlabel">
                            <input type="text" id="sohoadon"  name="sohoadon" value="<%= obj.getSOHOADON() %>" onchange="submitform()" />
                        </TD>
                          
                    </TR>
                    
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="trangthai" id="trangthai" onChange="submitform();" style="width:300px">
								<% if(obj.getTrangthai().equals("1")) { %>
									<option value="1" selected="selected">Chưa duyệt</option>
									<option value="2">Đã duyệt</option>
									<option value="3">Hoàn tất</option>
									<option value=""> </option>
								<% } else { if(obj.getTrangthai().equals("2")){ %>
									<option value="1"  >Chưa duyệt</option>
									<option value="2" selected="selected">Đã duyệt</option>
									<option value="3">Hoàn tất</option>
									<option value=""> </option>
								<% } else { if(obj.getTrangthai().equals("3")){ %> 
									<option value="1" >Chưa duyệt</option>
									<option value="2">Đã duyệt</option>
									<option value="3" selected="selected">Hoàn tất</option>
									<option value=""> </option>
								<%} else { %>
									<option value="" selected="selected"> </option>
									<option value="1">Chưa duyệt</option>
									<option value="2">Đã duyệt</option>
									<option value="3">Hoàn tất</option>
								<% } } } %>
							</select>
                        </TD> 
                        <TD class="plainlabel" width="15%">Phòng ban</TD>
                        <TD class="plainlabel">
                            <select name="donViThucHienId" id="donViThucHienId" style="width: 300px;" onChange="submitform();">
								<option value=""></option>
								<% 
								if (obj.getDonViThucHienList() != null)
								{
									for (Erp_Item item : obj.getDonViThucHienList())
									{
										if (item.getValue().equals(obj.getDonViThucHienId() + ""))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
                        </TD>
                        
                    </TR>
                    <TR>
                    <TD class="plainlabel" width="15%">Người tạo</TD>
                        <TD class="plainlabel" >
                            <select name="nguoiTaoId" id="nguoiTaoId" style="width: 300px;" onChange="submitform();">
								<option value=""></option>
								<% 
								if (obj.getNguoiTaoList() != null)
								{
									for (Erp_Item item : obj.getNguoiTaoList())
									{
										if (item.getValue().equals(obj.getNguoiTaoId() + ""))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
                        </TD>
                        <TD class="plainlabel" valign="middle" >Sản phẩm </TD>
                        <TD class="plainlabel" valign="middle">
                            <select id="sanpham" name="sanpham" onchange="submitform();" style="width:300px">
                            	<option value=""></option>
                            	<%
	                        		if(sanphamRs  != null)
	                        		{
	                        			while(sanphamRs.next())
	                        			{  
	                        			if( sanphamRs.getString("pk_seq").equals(obj.getSanphamId())){ %>
	                        				<option value="<%= sanphamRs .getString("pk_seq") %>" selected="selected" ><%=sanphamRs.getString("ma")+"--"+ sanphamRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= sanphamRs .getString("pk_seq") %>" ><%= sanphamRs.getString("ma")+"--"+sanphamRs.getString("ten") %></option>
	                        		 <% } } sanphamRs .close();
	                        		}
	                        	%>
                            </select>
                        </TD>  
                         </TR>
                    <TR style="display: none">
                        <TD class="plainlabel" width="15%" style="display: none">Loại nhập mua</TD>
                        <TD class="plainlabel" style="display: none">
                            <select name="loaiNhapMuaId" id="loaiNhapMuaId" style="width: 300px;">
								<option value=""></option>
								<% 
								if (obj.getLoaiNhapMuaList() != null)
								{
									for (Erp_Item item : obj.getLoaiNhapMuaList())
									{
										if (item.getValue().equals(obj.getLoaiNhapMuaId() + ""))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
                        </TD>
                        <TD class="plainlabel" width="15%">Chi phí nhận hàng</TD>
                        <TD class="plainlabel" colspan="3">
                            <select name="chiPhiNhanHang" id="chiPhiNhanHang" style="width: 300px;">
								<option value=""></option>
								<% 
								if (obj.getNguoiTaoList() != null)
								{
									for (Erp_Item item : obj.getNguoiTaoList())
									{
										if (item.getValue().equals(obj.getNguoiTaoId() + ""))
										{
										%>
											<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
										<% } else { %>
											<option value="<%=item.getValue()%>"><%=item.getName()%></option>
								<% } }} %>
							</select>
                        </TD>
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a>    
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Duyệt hóa đơn NCC </span>
                <!-- 	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> -->
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số (Park Nr.)</TH>
	                    <TH align="center">Ngày ghi nhận</TH>
	                    <TH align="center">Số hóa đơn</TH>
	                    <TH align="center">Tên nhà cung cấp</TH>
	                   <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%   int m = 0;		
					
					if(parkList!=null)
					{
						while(parkList.next())
						{
							if((m % 2 ) == 0) {%>
                         	<TR class='tbdarkrow' <%= parkList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + parkList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
	                        <%}else{ %>
	                          	<TR class='tblightrow' <%= parkList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + parkList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
	                        <%} %>
	                        <% String msg= parkList.getString("tooltip")==null?"":parkList.getString("tooltip"); 
	                      %>
	                       
	                        <TD align="center" onMouseover='ddrivetip("<%= msg %>" , 800)'
									onMouseout="hideddrivetip()"><%= parkList.getString("PREFIX")+ parkList.getString("PK_SEQ") %></TD>
	                        
	                         <TD align="center"><%= parkList.getString("NGAYGHINHAN") %></TD>
	                         <TD align="center"><%= parkList.getString("SOHOADON") %></TD>
	                         <TD align="left"><%= parkList.getString("TENNHACUNGCAP") %></TD>
	                      <%--    <TD align="left">
	                         	<%
		                    		String loaihd = parkList.getString("LOAIHD");
	                         		String loaihdncc = "";
		                    		if(loaihd.equals("0"))
		                    			loaihdncc = "Hóa đơn mua hàng nhập khẩu";
		                    		else if(loaihd.equals("1"))
		                    			loaihdncc = "Hóa đơn mua hàng trong nước";
		                    		else 
		                    			loaihdncc = "Hóa đơn mua VT/CPDV/TSCĐ/CCDC";	
		                    	%>
		                    	<%= loaihdncc %>
	                         </TD> --%>
	                         
	                         <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = parkList.getString("trangthai");
		                    		if(tt.equals("1"))
		                    			trangthai = "Chưa duyệt";
		                    		else if(tt.equals("2"))
		                    		{
		                    			trangthai = "Đã duyệt";
		                    		}
		                    		else 
		                    		{
		                    			if(tt.equals("3"))
		                    				trangthai = "Hoàn tất";
		                    			else if(tt.equals("4")){
			                    			trangthai = "Đã hủy";
			                    		}
		                    		}	
		                    	%>
		                    	<%= trangthai %>
		                    </TD>
		                    
		                    <TD align="center"><%= parkList.getString("NGAYTAO") %></TD>	
		                    <TD align="left"><%=  parkList.getString("TENNV")  %></TD>
         					<TD align="center"><%= parkList.getString("NGAYSUA") %></TD>
							<TD align="left"><%= parkList.getString("TENNVS") %></TD>
							
							<TD align="center"> 
		                    <% if(tt.equals("1")){ 
		                    	 if( parkList.getString("LOAIHD").equals("0")) // NHẬP KHẨU
		                    	 {
		                    		 if(quyen[2]!=0){ %>
		                    			 <A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
		                    		<% }
		                    	 }
		                    	 else if(parkList.getString("LOAIHD").equals("1")) // TRONG NƯỚC
		                    	 {
		                    		 if(quyen[2]!=0){ %>
	                    			  	<A href = "../../ErpParkHoadontrongnuocUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
	                    			<% }
		                    	 }
		                    	 else if(parkList.getString("LOAIHD").equals("2")) // CCDC || TAI SAN
		                    	 {
		                    		 if(quyen[2]!=0){ %>
	                    			  	<A href = "../../ErpParkHoadonUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
	                    			<% }
		                    	 }
		                    	 
		                    	 if(quyen[4]!=0){ %>
		                    		 <A id='chotphieu<%=parkList.getString("PK_SEQ")%>'
								      	   href="">
								      	   <img src="../images/Chot.png" alt="Duyệt" width="20" height="20" title="Duyệt"
								           border="0" onclick="if(!confirm('Bạn có muốn duyệt hóa đơn này?')) {return false ;}else{ processing('<%="chotphieu"+parkList.getString("PK_SEQ")%>' , '../../ErpParkHoadonduyetSvl?userId=<%=userId%>&chot=<%= parkList.getString("PK_SEQ") %>&nhacungcap=<%= obj.getNcc() %> ');}"  >
									</A>
		                    	<% } %>
								    		
					   		<%} else 
					   		{
					   			if( parkList.getString("LOAIHD").equals("0")) // NHẬP KHẨU
		                    	 {
		                    		 if(quyen[2]!=0){ %>
		                    			 <A href = "../../ErpParkHoadonnhapkhauUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    		<% }
		                    	 }
		                    	 else if(parkList.getString("LOAIHD").equals("1")) // TRONG NƯỚC
		                    	 {
		                    		 if(quyen[2]!=0){ %>
	                    			  	<A href = "../../ErpParkHoadontrongnuocUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
	                    			<% }
		                    	 }
		                    	 else if(parkList.getString("LOAIHD").equals("2")) // CCDC || TAI SAN
		                    	 {
		                    		 if(quyen[2]!=0){ %>
	                    			  	<A href = "../../ErpParkHoadonUpdateSvl?userId=<%=userId%>&display=<%= parkList.getString("PK_SEQ") %>&duyet=1"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
	                    			<% }
		                    	 }
					   		} %>
					   		
                            </TD>
					  <% m++;}  }%>

                            
					<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<%if(obj.getNxtApprSplitting() >1) {%> 
									<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpParkForm', 1, 'view')"> &nbsp; 
									<%}else {%>
									<img alt="Trang Dau" src="../images/first.gif"> 
									&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> 
									<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('erpParkForm', eval(erpParkForm.nxtApprSplitting.value) -1, 'view')">
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
									href="javascript:View('erpParkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View('erpParkForm', eval(erpParkForm.nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
									<img alt="Trang Cuoi" src="../images/last.gif">
									&nbsp; <%}else{ %> <img alt="Trang Cuoi"
									src="../images/last.gif" style="cursor: pointer;"
									onclick="View('erpParkForm', -1, 'view')"> &nbsp; <%} %>
								</TD>
							</tr>
				</TABLE>

            </fieldset>
        </div>
    </div>  
</div>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script>
<%

try{
/* 	if(parkList!=null){
		parkList.close();
	} */
	if(nhacungcapList!=null){
		nhacungcapList.close();
	}
	
	obj.close();  
 	session.setAttribute("obj",null);
}catch(Exception er){
	
}
}%>
</form>
</body>
</html>