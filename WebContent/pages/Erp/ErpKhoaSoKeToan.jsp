<%@page import="geso.traphaco.erp.beans.khoasothang.IKiemTraDLN"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.khoasothang.IErpKhoasoketoan"%>
<%@page import="java.util.Calendar"%>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
 
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% 	IErpKhoasoketoan obj = (IErpKhoasoketoan)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<% 	//ResultSet chungtuRs = (ResultSet) obj.getChungtuRs(); 
	Utility util=new Utility();
	Utility util1 = (Utility) session.getAttribute("util");
	String url="";
//	url = util1.getUrl("ErpKhoasothangSvl","");
	int[] quyen = new  int[5];
//	quyen=util.Getquyen("ErpKhoasothangSvl", "92",userId);

	NumberFormat formatter = new DecimalFormat("#,###,###"); 

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>Phanam - Project</TITLE>  
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	 <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
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
<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>
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
		 var msg = document.getElementById("msg").value;
		 if(msg.length > 0 )
		 {
			 var r = confirm(msg + "\n, bạn vẫn muốn tiếp tuc?");
			 if(r == false)
				 return;
		 }
		 else
		 {
			 var r = confirm("Hệ thống sẽ khóa sổ tháng hiện tại, mọi giao dịch trong tháng sẽ được đóng lại, bạn vẫn muốn tiếp tục?");
			 if(r == false)
				 return;
		 }
		 
		 document.forms['erpCngn'].action.value= 'khoasokho';
	     document.forms["erpCngn"].submit();
	 }
	
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 function capnhatdln()
	 {
	 	document.forms['erpCngn'].action.value= 'dln';
	 	document.forms['erpCngn'].submit();
	 }
	 function submitview()
	 { 
		
		 
				 document.forms['erpCngn'].action.value='submitview';
			     document.forms['erpCngn'].submit();	
	 }
	 
	  function xoa_ketchuyendulieu()
	 { 
		
		 
				 document.forms['erpCngn'].action.value='xoa_ketchuyendulieu';
			     document.forms['erpCngn'].submit();	
	 }
	  
	  function ketchuyendulieu()
	 { 
		
		 
				 document.forms['erpCngn'].action.value='ketchuyendulieu';
			     document.forms['erpCngn'].submit();	
	 }
	  	function khoasoketoan()
		 { 
			 
					 document.forms['erpCngn'].action.value='khoasoketoan';
				     document.forms['erpCngn'].submit();	
		 }
	  
	  
	  
	 
	 </SCRIPT>
	 
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpKhoasoketoanSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="id_" value="<%=obj.getId()%>" >
<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
             Quản lý kế toán > Khóa sổ kế toán > Khóa sổ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle" > Thông báo</legend>
			<textarea rows="2"  style="width: 100%; ; font-weight:bold">
				<%=obj.getMsg()%> </textarea>
		</fieldset>
	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Khóa sổ kế toán</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    
                    <tr class="plainlabel">
								<td width="15%" class="plainlabel" valign="middle">Năm </td>
								<td>
								<select name="nam" id="nam" onchange="submitview()"  style="width :70px" ">
									<option value=0> </option>  
									<%
									  
									 Calendar c2 = Calendar.getInstance();
										int t=c2.get(Calendar.YEAR) +3;
										
									
  										int n;
  										for(n=2010;n<t;n++){
  										if(obj.getNam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=n%> > <%=n%></option> 
 											<%
 										}
 									
 										
 									 }
 									%>
 									</select>
								</td>
							</tr>
							<tr class="plainlabel">
								<td width="20%" class="plainlabel" valign="middle">Tháng </td>
								<td>
								<select name="thang"  onchange="submitview()" id="thang"  style="width :70px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										
  									  if(obj.getThang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=k%> > <%=k%></option> 
 											<%
 										}
 									
 									  }
 									%>
									</select>
								</td>
								</tr>
								
                    
                    <tr>
                        <td colspan="2" class="plainlabel">
                        	 
                            		<a class="button2" href="javascript:khoasoketoan()">
                                	<img style="top: -4px;" src="../images/button.png" alt="">Khóa sổ kế toán</a>&nbsp;&nbsp;&nbsp;&nbsp;
                               
                        </td>
                    </tr>        					
                </TABLE>    
                
                <hr />

<!-- ########################### TABS KHÓA SỔ THÁNG ########################### -->
         
						<ul  class="tabs"  id="tabnew">		
								<li class="current" > <a href="#tab1">Xác nhận đồng ý </a></li>	 
								<li class="current" > <a href="#tab2">Cảnh báo</a></li>
								<li class="current" > <a href="#tab3">Kết chuyển</a></li>
								<li class="current" > <a href="#tab4">Kiểm tra báo cáo</a></li>
								<li class="current" > <a href="#tab5">Phân bổ chi phí</a></li>
							   
						 </ul>    
		 
		    
		    <!-- BẮT ĐẦU TAB1-->
		   <div class="panes">
		   <div id="tab1" class ="tab_content" style=" height: 500px">
		   	   <TABLE width="100%" cellpadding="5px" cellspacing="1px" class="plainlabel" >
                	<TR class="plainlabel">
                	<th>
                	Chọn
                	</th>
                	<th>
                	Thông tin xác nhận
                	</th>
                	</tr > <%  
										String mang[] = new String[]{"0","1","2","3","4","5","6","7","8"};
									
										String mangten[] = new String[]{"Kiểm tra số dư đầu kỳ các Tài khoản trên cân đối phát sinh so với cuối kỳ trước (so với file excel lưu hoặc bản hardcopy)",
													"Khóa sổ kinh doanh",
													"Kiểm tra sổ phụ ngân hàng so với số dư Tài khoản",
													"Kết chuyển VAT đầu vào/đầu ra",
													"Trích trước các khoản chi phí",
													"Trích lập các quỹ đầu tư 1 và quỹ đầu tư 2",
													"Phân bổ chi phí trả trước",
													"Khấu hao tài sản cố định",
													"Khóa sổ kho",
												};
										for(int i=0;i<mang.length ;i++){
												%>
												<tr class="plainlabel">
													<td>
														<%if(obj.getCheckXacnhan().indexOf(mang[i]) >0) {%>
														<input type ="checkbox" checked="checked" name="check_xacnhan" value="<%=mang[i]%>">
														<%}else {%>
														<input type ="checkbox" name="check_xacnhan" value="<%=mang[i]%>">
														<%} %>
													 </td>
													 <td>
													 <%=mangten[i] %>
													 </td>
													  
												</tr>
												<%
										}
									%>
						 
                	</TABLE>
            </div>
            
			<div id="tab2" class ="tab_content" style=" height: 500px">
			   <TABLE width="100%" cellpadding="5px" cellspacing="1px" >
                	<TR class="plainlabel">
                	<TD width = "20%">Bước 2: Cảnh báo kiểm tra dữ liệu nền  </TD>
                	
        <% 	//ResultSet chungtuRs = obj.getKiemtraDLN();
        	ResultSet chungtuRs;
            List<IKiemTraDLN> kiemtradln = obj.getKiemtraDLNList();
           	if(kiemtradln == null || kiemtradln.size() <= 0){	%>
        			<TD>Kết quả: Đã kiểm tra</TD> </TR>
        		</TABLE>
        			
       <%	}else{ %>    
       				<TD><input type="text" style="width: 100%" value="Vui lòng kiểm tra lại tình trạng hoạt động của dữ liệu nền và bổ sung nếu cần"  readonly="readonly" >
       				</TD>
       				<td>
       				 
       				</td>
       			</TR>
       			
       			
       			<tr class="plainlabel">
       			<td colspan="5">
       				<%if(obj.getCheckDuLieuNen().equals("1")) {%>
       				<input type="checkbox"  name="xacnhan_dulieunen" value="1" checked="checked">
       				<%}else{ %>
       				<input type="checkbox"  name="xacnhan_dulieunen" value="1" >
       				<%} %> 
       				 Chấp nhận cho khóa sổ kế toán
       			</td>
       			</tr>
       			 
       			</TABLE>
       			<TABLE width="100%" cellpadding="5px" cellspacing="1px">
       				<TR class = "tbheader">  		 	

                			<th align="center" rowspan = 2 width = "5%">STT</th>
                			<th align="center"  colspan = 2 >Dữ liệu nền</th>
                			<th align="center" rowspan = 2 width = "20%">Trạng thái</th>
                			<th align="center" rowspan = 2 width = "20%">Trạng thái mới</th>
                	</TR>
               		<TR class="tbheader">
                			<th align="center" width = "20%">Mã</th>
                			<th align="center" width = "25%">Tên</th>
                	</TR>
                		
                <% 	
                	String loai = "", loai_bk = "";
                	int i = 1, pos = 0;
                	
                		while(pos < kiemtradln.size())
                		{
                			IKiemTraDLN kt = kiemtradln.get(pos);
                			loai = kt.getLoai();
                			if(!loai.equals(loai_bk)){	
                				loai_bk = kt.getLoai();
                				i = 1;
                			%>
                    <TR>
                        		<td>&nbsp;</td>
                    			<td colspan = 4 class = "tbdarkrow">
                    				<B>
                    				<%= kt.getLoai() %>
                    				</B>
                    			</td>
                	</TR>	
                	
                	<TR>
                			<td align = "center" class = "tblightrow"><%= i++ %></td>
                			<td>
                				<input type="hidden" name="id" value="<%= kt.getId() %>" readonly="readonly"> 
                				<input type="hidden" name="loai" value="<%= kt.getLoai() %>" readonly="readonly">
                				<input type="text" style="width: 100%" name="ma" value="<%= kt.getMa() %>" readonly="readonly"> 
                			</td>
                			<td>
                				<input type="text" style="width: 100%" name="ten" value="<%= kt.getTen() %>" readonly="readonly" > 
                			</td>
                			<td align = "center" class = "tblightrow">
                				<input type="hidden" name="trangthai" value="<%= 0 %>" readonly="readonly" >
                				Ngưng hoạt động
                			</td>
                			<td align = "center" class = "tblightrow">
                				<%if(kt.getTrangthaiNew().trim().equals("1")){ %>
				  				<input name="trangthainew<%=i%>" checked="checked" value="1"  type="checkbox" /> Hoạt động 
				  				<%}else{ %>
				  				<input name="trangthainew<%=i%>" value="1"  type="checkbox" />Hoạt động 
				  				<%} %>
                			</td>
                	</TR>
                		
                <%			}else{
                			%>
                			
                	<TR>
                			<td align = "center" class = "tblightrow"><%= i++ %></td>
                			<td>
                				<input type="hidden" name="id" value="<%= kt.getId() %>" readonly="readonly">
                				<input type="hidden" name="loai" value="<%= kt.getLoai() %>" readonly="readonly">  
                				<input type="text" style="width: 100%" name="ma" value="<%= kt.getMa() %>" readonly="readonly"> 
                			</td>
                			<td>
                				<input type="text" style="width: 100%" name="ten" value="<%= kt.getTen() %>" readonly="readonly" > 
                			</td>
                			<td align = "center" class = "tblightrow">
                				<input type="hidden" name="trangthai" value="<%= 0 %>" readonly="readonly" >
                				Ngưng hoạt động
                			</td>
                			<td align = "center" class = "tblightrow">
                				<%if(kt.getTrangthaiNew().trim().equals("1")){ %>
				  				<input name="trangthainew" checked="checked" value="1"  type="checkbox" /> Hoạt động 
				  				<%}else{ %>
				  				<input name="trangthainew" value="1"  type="checkbox" />Hoạt động 
				  				<%} %>
                			</td>
                	</TR>
                			
                	<%  	}
                			pos++;
                		}
               }%>
  
               
			</TABLE>
			
            </div>
        
         <!-- KẾT THÚC TAB1-->
         	
		 <!-- BẮT ĐẦU TAB2-->
		   <div id="tab3" class ="tab_content" style=" height: 500px">
		   	<table width="100%" class="plainlabel" >
		   	<tr> 
		   		<th width="50%">
		   			Thực hiện kết chuyển dữ liệu kế toán cuối kỳ
		   		</th>
		   		
		   		<th width="50%">
		   		</th>
		   	</tr>
		   		<tr>
		   			<td>
		   				<a class="button2" href="javascript:ketchuyendulieu()">
                        <img style="top: -4px;" src="../images/button.png" alt="">Kết chuyển dữ liệu</a>&nbsp;&nbsp;&nbsp;&nbsp;
		   			</td>
		   			</tr>
		   			<tr>
		   			
		   			<td>
		   				 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table class = "tabletitle" width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
										  
											<th width="5%">Số hiệu tài khoản</th>
											<th width="5%">Nợ</th>
											<th width ="5%">Có</th>											
											<th width ="5%">Giá trị</th>
															
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									ResultSet RsTs=obj.getRsKetchuyenketoan();
									if(RsTs!=null)
									while (RsTs.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
									
										<td align="center"><%= RsTs.getString("SOHIEUTAIKHOAN")%> </td>
										<td align="left"><%= RsTs.getString("no")%> </td>
										<td align="left"><%= RsTs.getString("co")%>   </td>
										 <td align="right"><%=formatter.format(RsTs.getDouble("GIATRI"))%>   </td>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
		   			</td>
		   			
		   		</tr>
		   		
		   	</table>
            </div>
            <div id="tab4" class ="tab_content" style=" height: 500px">
            <TABLE width="100%" cellpadding="5px" cellspacing="1px" >
                	<TR class="plainlabel">
                	<TD width = "20%">Bước 4: Kiểm tra các báo cáo </TD>
               
       				<TD><input type="text" style="width: 100%" value="Phân tích chi phí theo khoản mục, Bảng cân đối phát sinh, Cân đối kế toán, Kết quả kinh doanh"  readonly="readonly" >
       				</TD>
       				<td>
       				 
       				</td>
       			</TR>
       			
       			<tr class="plainlabel">
       			<td colspan="5">
       				<%if(obj.getCheckXacnhan().indexOf("20")>=0) {%>
       				<input type="checkbox"  name="xacnhan_kiemtrabaocao" value="20" checked="checked">
       				<%}else{ %>
       				<input type="checkbox"  name="xacnhan_kiemtrabaocao" value="1" >
       				<%} %> 
       				 Chấp nhận cho khóa sổ kế toán
       			</td>
       			</tr>
       			
            </div>
            
            <div id="tab5" class ="tab_content" style=" height: 500px">
            </div>
            
         	</div>
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html>
