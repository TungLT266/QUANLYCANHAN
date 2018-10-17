<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpDuyetddhNppList obj = (IErpDuyetddhNppList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpLenhxuathangNppSvl","&loai=1",userId);
		ResultSet rsnvbanhang=obj.getRsnvbanhang();
		ResultSet rskhoid=obj.getRskhoid();
		ResultSet rstien=obj.getRstien();
		ResultSet kenhbanhang = obj.getKbhRs();
		ResultSet khuvuc = obj.getKvRs();
		NumberFormat formatter = new DecimalFormat("#,###,###");
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
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
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
    
    <!-- <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
   	
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
// 	    document.forms["ckParkForm"].trangthai.value = "";
// 	    document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].sodh.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>
	
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
	
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpLenhxuathangNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng > Lệnh xuất hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">
                	 <TR>
                        <TD class="plainlabel" width="100px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                    	
								<TD  class="plainlabel" width="100px">NVBH</TD>
							<TD  class="plainlabel" >
						
							<select name="nvbanhang" class="select2" style="width: 200px" onchange="submitform();">
								<option value="" >ALL</option>
									<%if(rsnvbanhang!=null)
										{ while (rsnvbanhang.next()){%>
										<option value="<%=rsnvbanhang.getString("pk_seq")%>" <%if(rsnvbanhang.getString("pk_Seq").equals(obj.getNvbanhang())){ %> selected="selected" <%} %>><%=rsnvbanhang.getString("ten") %></option>
										<%}} %>
							</select>
							
							</TD>
							
								<TD class="plainlabel" width="100px">Ngày giao</TD>
								<TD class="plainlabel"><input type="text" class="days"
									name="ngaygiao" value="<%= obj.getNgaygiao() %>" maxlength="10"
									onchange="submitform();" /></TD>	
                    
                    </TR>

                     <TR>
                     
                     	<TD class="plainlabel" >
                     		Đối tượng
                     	</TD>
                       

	                    		<TD  class="plainlabel" ><INPUT name="doituong" type="text" size="30" value = '<%= obj.getDoiTuong()%>' onChange = "submitform();"></TD>
                   
                        <TD class="plainlabel" valign="middle">Loại đơn hàng </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="loaidonhang" class="select2"  style="width: 200px;" onchange="submitform();"  >
                           		<option value="" >ALL</option>
								<%= util.getLoaidonhang( obj.getLoaidonhang() ) %>
                           </select>
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
							
							<TD class="plainlabel" width="100px">Người giao</TD>
															<TD class="plainlabel"><input type="text" 
																name="nguoigiao" value="<%= obj.getNguoigiao() %>" maxlength="10"
																onchange="submitform();" /></TD>
                     
                    </TR>  
                    <TR>
						<TD  class="plainlabel">Số đơn hàng</TD>
						<TD  class="plainlabel" ><INPUT name="sodh" type="text" size="30" value = '<%= obj.getSodh()%>' onChange = "submitform();"></TD>
						<TD class="plainlabel" width="100px">Tên SP</TD>
															<TD class="plainlabel"><input type="text" 
																name="tensp" value="<%= obj.getTensp() %>" maxlength="10"
																onchange="submitform();" /></TD>
								
						<TD  class="plainlabel">Ghi chú</TD>
							<TD  class="plainlabel" ><INPUT name="ghichu" type="text" size="30" value = '<%= obj.getGhichu()%>' onChange = "submitform();"></TD>
						
						<TD  class="plainlabel">Ngày nợ</TD>
							<TD  class="plainlabel" ><INPUT name="ngayno" type="text" size="30" value = '<%= obj.getNgayno() %>' onChange = "submitform();"></TD>
					
									
								
					</TR> 
					
					<TR>
							<TD  class="plainlabel">Số lô</TD>
							<TD  class="plainlabel" ><INPUT name="solo" type="text" size="30" value = '<%= obj.getSolo()%>' onChange = "submitform();"></TD>
							
							<TD  class="plainlabel">Người tạo</TD>
							<TD  class="plainlabel" ><INPUT name="nguoitao" type="text" size="30" value = '<%=obj.getNguoitao() %>' onChange = "submitform();"></TD>
						
								<TD class="plainlabel" valign="middle">Trạng thái</TD>
								<TD class="plainlabel" valign="middle">
								<select name="trangthai" class="select2" style="width: 200px" onchange="submitform();">	
								<% if( obj.getTrangthai().equals("0") ) { %>
										<option value= "" selected="selected" >ALL</option>
									<% } else { %>
										<option value= "" >ALL</option>
									<% } %>	
									<% if( obj.getTrangthai().equals("0") ) { %>
										<option value= "1" selected="selected" >chờ duyệt</option>
									<% } else { %>
										<option value= "1" >chờ duyệt</option>
									<% } %>
									<% if( obj.getTrangthai().equals("4") ) { %>
										<option value= "4" selected="selected" >Đã duyệt </option>
									<% } else { %>
										<option value= "4" >Đã duyệt </option>
									<% } %>
									
								</select>
									<TD  class="plainlabel">Kênh</TD>
							<TD  class="plainlabel" >
							<select name="kbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(kenhbanhang!=null){ while (kenhbanhang.next()){ %>
								<option value="<%=kenhbanhang.getString("pk_seq")%>" <%if(kenhbanhang.getString("pk_seq").equals(obj.getKbhId())){%> selected="selected" <%}%>><%=kenhbanhang.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
					</TR >
				
					<TR>
							<TD  class="plainlabel">Khu vực</TD>
							<TD  class="plainlabel" >
							<select name="kvid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(khuvuc!=null){ while (khuvuc.next()){ %>
								<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getKvId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							
						<TD class="plainlabel" width="100px">Khách hàng </TD>
                        <TD class="plainlabel" colspan = "5">
                            <select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value=""> All </option>
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
	                        		 <% } 	                        				                        				                        				                        			
	                        			
	                        			} nppRs.close();} catch(Exception ex){ ex.printStackTrace();}
	                        		}
	                        	%>
	                    	</select>	
	                    </TD>
							
					</TR>
					
					<%if(rstien!=null){ if(rstien.next()) {
								
								double doanhso = rstien.getDouble("doanhso");
								double chietkhau = rstien.getDouble("chietkhau");
								//double thuegtgt = rstien.getDouble("thuegtgt");
								//double tienthue = Math.round( ( doanhso - chietkhau ) * thuegtgt / 100.0 );
								//double doanhthu = doanhso - chietkhau + thuegtgt;
								double doanhthu = rstien.getDouble("doanhthu");
								
								%>
							<TR>

							<TD  class="plainlabel">Doanh số</TD>
							<TD  class="plainlabel" ><INPUT name="thanhtien" type="text" size="30" value = '<%=formatter.format( doanhso )%>' readonly="readonly" ></TD>
							
							<TD  class="plainlabel">Chiết khấu</TD>
							<TD  class="plainlabel" ><INPUT name="cksp" type="text" size="30" value = "<%=formatter.format( chietkhau )%>" readonly="readonly" ></TD>
								
							<%-- <TD  class="plainlabel">Thuế GTGT</TD>
							<TD  class="plainlabel" ><INPUT name="thuegtgt" type="text" size="30" value = '<%= formatter.format( thuegtgt )%>' readonly="readonly" ></TD> --%>
							
							<TD  class="plainlabel">Doanh thu</TD>
							<TD  class="plainlabel" colspan="3" ><INPUT name="tongtien" type="text" size="30" value = '<%=formatter.format( doanhthu )%>' readonly="readonly" ></TD>
						
							
							</TR>
							<%} rstien.close(); } %>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                        
                        <TD  class="plainlabel"></TD>
							<TD  class="plainlabel" ></TD>
							
							<TD  class="plainlabel"></TD>
							<TD  class="plainlabel" ></TD>
							
							
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Lệnh xuất hàng </span>&nbsp;&nbsp;
            	<%-- <%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                               <%} %> --%>
                </legend>
            	<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
            	<thead>
					<TR class="tbheader">
	                    <TH align="center" style="width: 10%" >Mã đơn hàng</TH>
	                    <TH align="center" style="width: 7%" >Ngày đặt</TH>
	                    <TH align="center" style="width: 13%" >Đối tượng</TH>
	                    <!-- <TH align="center" style="width: 10%" >Loại đơn hàng</TH> -->
	                    <TH align="center" style="width: 10%" >Ghi chú</TH>
	                    <TH align="center" style="width: 7%" >Trạng thái</TH>
	                    <TH align="center" style="width: 7%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 13%" >Người tạo</TH>
	                    <TH align="center" style="width: 7%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 13%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
	                </thead>
	                <tbody>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  	
                 				String chuoiFORMAT = "";
                 				String trangthai = "Đã duyệt";
                 				/* if(nhapkhoRs.getString("trangthai").equals("4"))
                 					trangthai = "Đã duyệt <br /> "; */
                 					
                				String loaidonhang = "";
	                    		String ldh = nhapkhoRs.getString("loaidonhang");
	                    		String hopdong_fk = "";
	                    		if(nhapkhoRs.getString("hopdong_fk") != null )
	                    			hopdong_fk = nhapkhoRs.getString("hopdong_fk");
	                    		
	                    		if(hopdong_fk.trim().length() > 0 ) //NPP TAO
	                    			loaidonhang = "ETC";
	                    		else 
                    			{
	                    			if(ldh.equals("0"))
	                    				loaidonhang = "Đơn hàng NPP";
	                    			else if(ldh.equals("1"))
	                    				loaidonhang = " ETC";
	                    			else if(ldh.equals("2"))
	                    				loaidonhang = " OTC";
	                    			else if(ldh.equals("3"))
	                    				loaidonhang = " Đơn hàng nội bộ";
                    			}
	                    		
              					String msg=nhapkhoRs.getString("tooltip")==null?"":nhapkhoRs.getString("tooltip");
              					msg += nhapkhoRs.getString("tooltip_scheme")==null?"":nhapkhoRs.getString("tooltip_scheme") + "</table>";	
                 				
    							//MÀU SẮC THEO CẤP ĐỘ GIAO HÀNG
								int CAPDOGIAOHANG = nhapkhoRs.getInt("CAPDOGIAOHANG");
                 				
                 				if(CAPDOGIAOHANG > 0 && CAPDOGIAOHANG <= 4)
									chuoiFORMAT = " style='color: red; font-weight: bold;'  ";
								else if(CAPDOGIAOHANG > 4 && CAPDOGIAOHANG <= 8)
									chuoiFORMAT = " style='color: orange; font-weight: bold; '  ";
								else if(CAPDOGIAOHANG > 8 && CAPDOGIAOHANG <= 24)
									chuoiFORMAT = " style='color: blue;'  ";
              					
              					if((m % 2 ) == 0) { 
                 					
                 					/* if(nhapkhoRs.getString("NOTE").trim().length() > 0)
                 						chuoiFORMAT = " style='color: red;' onMouseover=\"ddrivetip('" + nhapkhoRs.getString("NOTE") + "', 250)\"; onMouseout='hideddrivetip()' "; */
                 				%>
		                         	<TR class='tbdarkrow' <%= chuoiFORMAT %> >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= chuoiFORMAT %> >
		                        <%} %>
		                    <TD align="center" onMouseover="ddrivetip('<%=msg %>', 700)"; onMouseout="hideddrivetip()"><%= nhapkhoRs.getString("machungtu") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYDONHANG") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD> 
		                    <TD ><%= nhapkhoRs.getString("ghichu") %></TD>   
		                    <%-- <TD align="center">
		                    	<%= loaidonhang %>
		                    </TD>    --%>	
		                    <TD align="center">
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="left"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="left"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center">   
	                   			<%-- <%if(quyen[Utility.CHOT]!=0){ 
	                   				
                   						if(nhapkhoRs.getString("trangthai").equals("4")) {%>
                    						<A href = "../../ErpLenhxuathangNppUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>
                    					<% } else {%>
                    						<A href = "../../ErpLenhxuathangNppUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Chot.png" alt="Duyệt đơn đặt hàng" title="Duyệt đơn đặt hàng" border=0></A>
	                    			
	                   			<% } } %> --%>
	                   			
	                   			<%if(quyen[Utility.XEM]!=0){ %>
	                   			
	                   				<A href = "../../ErpLenhxuathangNppUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&phanloai=<%= obj.getPhanloai() %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>&nbsp;
	                   				
		                    		<A href = "../../ErpDondathangDoitacInSvl?userId=<%=userId%>&id=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Printer30.png" alt="In" title="In" border=0></A>
		                    	
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
</html><%
try
{
	if(khuvuc!=null)khuvuc.close();
	if(kenhbanhang!=null)kenhbanhang.close();
	if(nhapkhoRs!=null)nhapkhoRs.close();
	if(nppRs!=null)nppRs.close();
	if(rskhoid!=null)rskhoid.close();
	if(obj!=null)
	{
		obj.DBclose();
		session.setAttribute("obj",null);
	}
}
catch(Exception ex)
{
	ex.printStackTrace();
}
	
	
	}%>