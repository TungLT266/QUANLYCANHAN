<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinhNPP.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
NumberFormat formatter = new DecimalFormat("#,###,###");
IErpHoadontaichinhNPPList obj = (IErpHoadontaichinhNPPList)session.getAttribute("obj");
%>
<% ResultSet hoadonRs =  obj.getDondathangRs(); %>
<% ResultSet khRs = obj.getKhRs();
	ResultSet HTbanhang = obj.getHtbhRs();
	ResultSet kenhbanhang = obj.getKbhRs();
	ResultSet khuvuc = obj.getKvRs();
	ResultSet tinhthanh = obj.getTinhthanhRs();
	ResultSet quanhuyen = obj.getQuanhuyenRs();
	
		%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   NumberFormat formater = new DecimalFormat("##,###,###");%>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpHoadontaichinhNPPSvl","",userId);
		ResultSet rstien=obj.getRstien();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
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
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  <!--  <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
   	
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
	    document.forms["ckParkForm"].khTen.value = "";
	    document.forms["ckParkForm"].sohoadon.value = "";
	    document.forms["ckParkForm"].sodonhang.value = "";
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
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpHoadontaichinhNPPSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getnppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">	
        	&nbsp;Quản lý bán hàng > Bán hàng > Xuất hóa đơn 
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
                        <TD class="plainlabel" width="220px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel" colspan="3">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                     	<TD class="plainlabel" >
                     		Mã / Tên Khách hàng
                     	</TD>
                        <TD class="plainlabel">
	                    	<input type="text" name = "khTen" value="<%= obj.getKhTen() %>" onchange="submitform();" >
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("1")){%>
									<option value="" >ALL</option>
								  	<option value="1" selected>Chưa in hóa đơn</option>
								  	<!-- <option value="2">Chưa in hóa đơn</option>
								  	<option value="3" >Đã xóa</option> -->
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option>
								<%-- <%}else if(obj.getTrangthai().equals("2")) { %>
								 	<option value="" >ALL</option>
								 	<option value="1" >Chờ xác nhận</option>
								  	<option value="2" selected>Chưa in hóa đơn</option>
								  	<option value="3" >Đã xóa</option>
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option> --%>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="" >ALL</option>
								 	<option value="1" >Chưa in hóa đơn</option>
								  <!-- 	<option value="2" >Chưa in hóa đơn</option> -->
								  	<option value="3" selected>Đã xóa</option> 
								  	<option value="4" >Đã in HĐ</option> 
								  	<option value="5" >Đã hủy</option>	
								<%}else if(obj.getTrangthai().equals("4")) {%>
									<option value="" >ALL</option>
								 	<option value="1" >Chưa in hóa đơn</option>
								 <!--  	<option value="2" >Chưa in hóa đơn</option> -->
								  	<option value="3" >Đã xóa </option> 
								  	<option value="4" selected>Đã in HĐ</option> 
								  	<option value="5" >Đã hủy</option>
								<% }else if(obj.getTrangthai().equals("5")) {%>
									<option value="" >ALL</option>
								 	<option value="1" >Chưa in hóa đơn</option>
							<!-- 	  	<option value="2" >Chưa in hóa đơn</option> -->
								  	<option value="3" >Đã xóa </option> 
								  	<option value="4" >Đã in HĐ</option> 
								  	<option value="5" selected>Đã hủy</option>
							  	<% }else {%>
									<option value="" selected>ALL</option>
								 	<option value="1" >Chưa in hóa đơn</option>
							<!-- 	  	<option value="2">Chưa in hóa đơn</option> -->
								  	<option value="3"  >Đã xóa </option>
								  	<option value="4" >Đã in HĐ</option>
								  	<option value="5" >Đã hủy</option>
							<%} %>
                           </select>
                        </TD>  
                        
                                          
                    </TR>
                 <TR>
							<TD  class="plainlabel">Số hóa đơn</TD>
							<TD  class="plainlabel" ><INPUT name="sohoadon" type="text" size="30" value = '<%= obj.getSohoadon()%>' onChange = "submitform();"></TD>
							<TD  class="plainlabel">Số đơn hàng</TD>
							<TD  class="plainlabel"  colspan="3"><INPUT name="sodonhang" type="text" size="30" value = '<%= obj.getSodonhang()%>' onChange = "submitform();"></TD>
									
					</TR>
					
					<TR>
                    	<TD class="plainlabel"  > Loại hoá đơn </td>
                    	<TD width="300px" class="plainlabel" valign="top"  >
	                    	<% 	String[] tthai = new  String[] {"Hóa đơn bán","Hóa đơn KM ","All"  } ;
								String[] idTrangThai = new  String[] {"0","1",""} ;%> 
							<SELECT name="loaidonhang" >
			      		 <% for( int i=0;i<tthai.length;i++) { 
			    			if(idTrangThai[i].equals(obj.getLoaidonhang()  ) ){ %>
			      				<option value='<%=idTrangThai[i]%>' selected><%=tthai[i] %></option>
			      		 	<%}else{ %>
			     				<option value='<%=idTrangThai[i]%>'><%=tthai[i] %></option>
			     			<%} 
			      		 }
			      		 	%>
						       	</SELECT>
	                    	
	                    </TD>
                    
                   		<TD  class="plainlabel" colspan="3" >Giá sản phẩm</TD>
							<TD  class="plainlabel" ><INPUT name="giasp" type="text" size="30" value = '<%= obj.getGiasp()%>' onChange = "submitform();"></TD>
					
                    </TR>
					<%-- <%if(obj.getIsSearch()){ %>
								<TR><TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel" >Doanh số</TD>
									<td class="plainlabel"><input type="text" name="ds" size="6" value="<%= formatter.format(obj.getTongTruoc()) %>"></td>
									<TD class="plainlabel" >Thuế</TD>
									<td class="plainlabel"><input type="text" name="ck" size="6" value="<%= formatter.format(obj.getTongCK()) %>"></td>
									<TD class="plainlabel" >Doanh thu</TD>
									<td class="plainlabel"><input type="text" name="dt" size="6" value="<%= formatter.format(obj.getTongSau()) %>"></td>
							
								</TR>
								<%} %>   --%>     
                    <tr>
                    
                    <TR>
                    	<TD class="plainlabel" width="100px">Tên SP</TD>
						<TD class="plainlabel">
							<input type="text" name="tensp" value="<%= obj.getTensp() %>" onchange="submitform();" /></TD>
						<TD class="plainlabel" width="100px">Tên xuất HĐ</TD>
						<TD class="plainlabel" colspan="3" >
							<input type="text" name="tenxuathd" value="<%= obj.getTenxuatHD() %>"  onchange="submitform();" /></TD>
                    </TR>
                     <TR>
                     
                         <TD  class="plainlabel">Hệ thống bán hàng</TD>
						<TD  class="plainlabel"  >
							<select name="htbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(HTbanhang!=null){ while (HTbanhang.next()){ %>
								<option value="<%=HTbanhang.getString("pk_seq")%>" <%if(HTbanhang.getString("pk_seq").equals(obj.getHtbhId())){%> selected="selected" <%}%>><%=HTbanhang.getString("diengiai") %>  </option>
								<%} }%>
							</select>
							</TD>
							
                    	<TD  class="plainlabel">Kênh</TD>
						<TD  class="plainlabel" colspan="3" >
							<select name="kbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(kenhbanhang!=null){ while (kenhbanhang.next()){ %>
								<option value="<%=kenhbanhang.getString("pk_seq")%>" <%if(kenhbanhang.getString("pk_seq").equals(obj.getKbhId())){%> selected="selected" <%}%>><%=kenhbanhang.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							
                    </TR>
                    <TR>
	                    <TD  class="plainlabel">Khu vực</TD>
						<TD  class="plainlabel"  >
								<select name="kvid" class="select2"  style="width: 200px;" onchange="submitform();">
								
									<option value="">ALL</option>
									<%if(khuvuc!=null){ while (khuvuc.next()){ %>
									<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getKvId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
									<%} }%>
								</select>
						</TD>
					
						<TD  class="plainlabel">Tỉnh thành</TD>
						<TD  class="plainlabel"  colspan ="3">
								<select name="tinhthanhId" class="select2"  style="width: 200px;" onchange="submitform();">
								
									<option value="">ALL</option>
									<%if(tinhthanh!=null){ while (tinhthanh.next()){ %>
									<option value="<%=tinhthanh.getString("pk_seq")%>" <%if(tinhthanh.getString("pk_seq").equals(obj.getTinhthanhId())){%> selected="selected" <%}%>><%=tinhthanh.getString("ten") %>  </option>
									<%} }%>
								</select>
						</TD>
							
                    </TR>
                    
                    <TR>
                    	<TD  class="plainlabel">Quận huyện</TD>
						<TD  class="plainlabel"  >
							<select name="quanhuyenId" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(quanhuyen!=null){ while (quanhuyen.next()){ %>
								<option value="<%=quanhuyen.getString("pk_seq")%>" <%if(quanhuyen.getString("pk_seq").equals(obj.getQuanhuyenId())){%> selected="selected" <%}%>><%=quanhuyen.getString("ten") %>  </option>
								<%} }%>
							</select>
						</TD>
						<TD  class="plainlabel">Người tạo</TD>
						<TD  class="plainlabel" >
								<input type="text" name="nguoitao" value="<%= obj.getNguoitao() %>" >
							</TD>
						<TD  class="plainlabel">Người sửa</TD>
						<TD  class="plainlabel" >
							<input type="text" name="nguoisua" value="<%= obj.getNguoisua() %>" >
						</TD>
                    </TR>
                    
                    
                    <%if(rstien!=null){ while (rstien.next()){%>
                     <TR>
                    
                   	 <TD  class="plainlabel">Thuế GTGT</TD>
							<TD  class="plainlabel" ><INPUT name="thuegtgt" type="text" size="30" value = '<%= formater.format(rstien.getDouble("vat"))%>' onChange = "submitform();"></TD>
					
					 <TD class="plainlabel" width="100px"> Doanh thu </TD>
					 <TD class="plainlabel" colspan="3" ><input type="text" 
						name="tensp" value="<%= formater.format(rstien.getDouble("tongtienavat")) %>" maxlength="10"
						onchange="submitform();" /></TD>
							
                    </TR>
                    <%}} %>
                    
                    <tr>
                        <td colspan="6" class="plainlabel">
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
            	<legend><span class="legendtitle"> Xuất hóa đơn </span>&nbsp;&nbsp;
            	
            	  <%-- <%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
                 <%} %>   --%> 
                 
                </legend>
            	<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
            	<thead>
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%" >Mã số</TH>
	                    <TH align="center" style="width: 8%" >Ngày hóa đơn</TH>
	                    <TH align="center" style="width: 8%" >Số hóa đơn</TH>
	                    <TH align="center" style="width: 5%" >Kênh</TH>
	                    <TH align="center" style="width: 13%" >Khách hàng</TH>
	                    <TH align="center" style="width: 8%" >Trạng thái</TH>
	                    <TH align="center" style="width: 8%" >Tổng tiền</TH>
	                    <TH align="center" style="width: 8%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 8%" >Người tạo</TH>
	                    <TH align="center" style="width: 8%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 8%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
	                </thead>
	                <tbody>
					<%
                 	int m = 0;
					if( hoadonRs != null )
					{
							while( hoadonRs.next() )
							{  		
								
								String trangthai = "";
	                    		String tt = hoadonRs.getString("trangthai");
	                    		if(tt.equals("1")){ //NPP TAO
	                    			trangthai = "Chưa in hóa đơn";
	                    		}else
	                    		{
	                    			if(tt.equals("2")) {
		                    			trangthai = "Chưa in hóa đơn";
	                    			}else{
	                    				if(tt.equals("4")) 
	                    					trangthai = "Đã in HĐ";
	                    				else
	                    				{
	                    					if(tt.equals("3")) 
		                    					trangthai = "Đã xóa";
	                    					else 
	                    						trangthai = "Đã hủy";
	                    				}
	                    			}
	                    		}
	                    		
								String msg = hoadonRs.getString("tooltip");
								
								String chuoiFORMAT = "";
								//MÀU SẮC THEO CẤP ĐỘ GIAO HÀNG
								int CAPDOGIAOHANG = hoadonRs.getInt("CAPDOGIAOHANG");
                 				
                 				if(CAPDOGIAOHANG > 0 && CAPDOGIAOHANG <= 4)
									chuoiFORMAT = " style='color: red; '  ";
								else if(CAPDOGIAOHANG > 4 && CAPDOGIAOHANG <= 8)
									chuoiFORMAT = " style='color: orange; font-weight: bold; '  ";
								else if(CAPDOGIAOHANG > 8 && CAPDOGIAOHANG <= 24)
									chuoiFORMAT = " style='color: blue;'  ";
								
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow' <%= chuoiFORMAT %> >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= chuoiFORMAT %> >
		                        <%} %>
		                    <TD align="center" onMouseover="ddrivetip('<%= msg %>', 600)"; onMouseout="hideddrivetip()"><%= hoadonRs.getString("pk_seq") %></TD>
		                    <TD align="center" ><%= hoadonRs.getString("ngayxuatHD") %></TD>
		                    <TD align="center" ><%=hoadonRs.getString("sohoadon") %></TD>
		                     <TD align="center" ><%= hoadonRs.getString("kenhbanhang") %></TD>
		                    <TD ><%= hoadonRs.getString("khTen") %></TD>  
		                    <TD align="center" >
		                    	<%= trangthai %>
		                    </TD>   
		                    <TD align="center" ><%= formater.format(Double.parseDouble(hoadonRs.getString("tongtien"))) %></TD>									                                    
					     	<TD align="center" ><%= hoadonRs.getString("ngaytao") %></TD>	
		                    <TD align="center" ><%= hoadonRs.getString("nguoitao") %></TD>
         					<TD align="center" ><%= hoadonRs.getString("ngaysua") %></TD>
							<TD align="center" ><%= hoadonRs.getString("nguoisua") %></TD>
									
		                    <TD align="center"> 
		                    <%
		                     if(tt.equals("1")){ %>
								<%if(quyen[Utility.SUA]!=0){ %>
									  <A href = "../../ErpHoadontaichinhNPPUpdateSvl?userId=<%=userId%>&update=<%= hoadonRs.getString("pk_seq") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                
                                <%if(quyen[Utility.CHOT]!=0){ %>
                                	<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&chot=<%= hoadonRs.getString("pk_seq") %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Chot.png" alt="Duyệt hóa đơn" title="Duyệt hóa đơn" width="20" height="20" border=0 ></A>&nbsp;
                              	<%} %>
                              	
                              	<%if(quyen[Utility.XOA]!=0){ %>
                              		<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&delete=<%= hoadonRs.getString("pk_seq") %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa hóa đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa hóa đơn này?')) return false;"></A>									
		                  		<%} %>	
		                  			  	                		
		                    <%} else{ %>
		                    	
		                    	<%if(quyen[Utility.XEM]!=0){ %>
		                    		<A href = "../../ErpHoadontaichinhNPPUpdateSvl?userId=<%=userId%>&display=<%= hoadonRs.getString("pk_seq") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                   		<%} %>
		                   		
		                   		<%  if( !tt.equals("3") && !tt.equals("5") ) { %>	
		                   			<A href = "../../ErpHoadontaichinhNPPPdfSvl?userId=<%=userId%>&pdf=<%= hoadonRs.getString("pk_seq") %>&nppId=<%= obj.getnppId()%>&print=1"><IMG src="../images/Printer30.png" alt="In" title="In" width="20" height="20" border=0 onclick="if(!confirm('Hóa đơn này đã in rồi bạn có muốn in lại?')) return false;" ></A>
		                   			
		                   			<%if(quyen[Utility.XOA]!=0){ %>
	                              		<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&delete=<%= hoadonRs.getString("pk_seq") %>&loai=<%= obj.getPhanloai() %>"><img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa hóa đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa hóa đơn này?')) return false;"></A>									
			                  		<%} %>	
		                   			
		                    	<% } %>
		                    		
		                    <%  } %>
		                    </TD>
		                </TR>
                     <% m++; } } %>
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
<script type="text/javascript">
	
	 <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 400, "click");
	   
	  <%}%>
	  
</script>
</form>
<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		//sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = false;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table", 100);
	</script> 
</body>
</html>

<% 
	if( hoadonRs != null)
	{
		hoadonRs.close();
		hoadonRs = null;
	}
	if( khRs != null)
	{
		khRs.close();
		khRs = null;
	}
	
	obj.DBclose();
	session.setAttribute("obj", null);
	
%>

<%}%>