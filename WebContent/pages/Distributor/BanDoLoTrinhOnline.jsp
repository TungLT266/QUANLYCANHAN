<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "java.util.Hashtable" %> 
<% String nnId = (String)session.getAttribute("nnId"); %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.traphaco.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("BandoTTSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IBandott bdBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = bdBean.getVungRs(); %>
<% ResultSet nppRs = bdBean.getNppRs(); %>
<% ResultSet ddkd = bdBean.getDdkdRs(); %>
<% ResultSet nhanvien = bdBean.getNvRs(); %>
<% ResultSet ttRs = bdBean.getTtRs(); %>

<% 
	ResultSet khList = bdBean.getKhChuaViengThamRs(); // ben nay la lo trinh cua dai dien kinh danh
	String[] latconditon = bdBean.getLatcondition();
	String[] longconditon = bdBean.getLongconditon();
	
	NumberFormat formater = new DecimalFormat("#,###,###");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Traphaco - Project</TITLE>
	<style type="text/css">
    .style1 {background-color:#ffffff;font-weight:bold;border:4px #006699 solid; font-size:15px; font-style: bold; }
    </style>
	<script src="//maps.google.com/maps?file=api&amp;v=3&amp;key=AIzaSyDUs-Mowbr3M3jRoVxy30B9uBAlEFgbKMQ" type="text/javascript"></script>
    
    <script type="text/javascript">
    function ELabel(point, html, classname, pixelOffset, percentOpacity, overlap) {
        // Mandatory parameters
        this.point = point;
        this.html = html;
        
        // Optional parameters
        this.classname = classname||"";
        this.pixelOffset = pixelOffset||new GSize(0,0);
        if (percentOpacity) {
          if(percentOpacity<0){percentOpacity=0;}
          if(percentOpacity>100){percentOpacity=100;}
        }        
        this.percentOpacity = percentOpacity;
        this.overlap=overlap||false;
        this.hidden = false;
      } 
      
      ELabel.prototype = new GOverlay();

      ELabel.prototype.initialize = function(map) {
        var div = document.createElement("div");
        div.style.position = "absolute";
        div.innerHTML = '<div class="' + this.classname + '">' + this.html + '</div>' ;
        map.getPane(G_MAP_FLOAT_SHADOW_PANE).appendChild(div);
        this.map_ = map;
        this.div_ = div;
        if (this.percentOpacity) {        
          if(typeof(div.style.filter)=='string'){div.style.filter='alpha(opacity:'+this.percentOpacity+')';}
          if(typeof(div.style.KHTMLOpacity)=='string'){div.style.KHTMLOpacity=this.percentOpacity/100;}
          if(typeof(div.style.MozOpacity)=='string'){div.style.MozOpacity=this.percentOpacity/100;}
          if(typeof(div.style.opacity)=='string'){div.style.opacity=this.percentOpacity/100;}
        }
        if (this.overlap) {
          var z = GOverlay.getZIndex(this.point.lat());
          this.div_.style.zIndex = z;
        }
        if (this.hidden) {
          this.hide();
        }
      }

      ELabel.prototype.remove = function() {
        this.div_.parentNode.removeChild(this.div_);
      }

      ELabel.prototype.copy = function() {
        return new ELabel(this.point, this.html, this.classname, this.pixelOffset, this.percentOpacity, this.overlap);
      }

      ELabel.prototype.redraw = function(force) {
        var p = this.map_.fromLatLngToDivPixel(this.point);
        var h = parseInt(this.div_.clientHeight);
        this.div_.style.left = (p.x + this.pixelOffset.width) + "px";
        this.div_.style.top = (p.y +this.pixelOffset.height - h) + "px";
      }

      ELabel.prototype.show = function() {
        if (this.div_) {
          this.div_.style.display="";
          this.redraw();
        }
        this.hidden = false;
      }
      
      ELabel.prototype.hide = function() {
        if (this.div_) {
          this.div_.style.display="none";
        }
        this.hidden = true;
      }
      
      ELabel.prototype.isHidden = function() {
        return this.hidden;
      }
      
      ELabel.prototype.supportsHide = function() {
        return true;
      }

      ELabel.prototype.setContents = function(html) {
        this.html = html;
        this.div_.innerHTML = '<div class="' + this.classname + '">' + this.html + '</div>' ;
        this.redraw(true);
      }
      
      ELabel.prototype.setPoint = function(point) {
        this.point = point;
        if (this.overlap) {
          var z = GOverlay.getZIndex(this.point.lat());
          this.div_.style.zIndex = z;
        }
        this.redraw(true);
      }
      
      ELabel.prototype.setOpacity = function(percentOpacity) {
        if (percentOpacity) {
          if(percentOpacity<0){percentOpacity=0;}
          if(percentOpacity>100){percentOpacity=100;}
        }        
        this.percentOpacity = percentOpacity;
        if (this.percentOpacity) {        
          if(typeof(this.div_.style.filter)=='string'){this.div_.style.filter='alpha(opacity:'+this.percentOpacity+')';}
          if(typeof(this.div_.style.KHTMLOpacity)=='string'){this.div_.style.KHTMLOpacity=this.percentOpacity/100;}
          if(typeof(this.div_.style.MozOpacity)=='string'){this.div_.style.MozOpacity=this.percentOpacity/100;}
          if(typeof(this.div_.style.opacity)=='string'){this.div_.style.opacity=this.percentOpacity/100;}
        }
      }

      ELabel.prototype.getPoint = function() {
        return this.point;
      } 
    var map = null;
    var geocoder = null;
    var center = null;
    
	var khs = new Array();
	var duongdiList = new Array();
	
	var khLatLons = [];
	var khMarkers = [];
	
	var RANDOM_NUMER = 10; 

    function initialize() 
    {
      if (GBrowserIsCompatible())
      {
        map = new GMap2(document.getElementById("map_canvas")); 
        geocoder = new GClientGeocoder();
        center = new GLatLng(10.79912,106.680259);
        map.setCenter(center, 14);
        map.setUIToDefault();
        
        // Create our "tiny" marker icon
        var blueIcon = new GIcon(G_DEFAULT_ICON);
        blueIcon.image = "../images/maps_human.png";
        
        //blueIcon.image = "../upload/Penguins.jpg";
		blueIcon.iconSize = new GSize(39, 39);
        
		// Set up our GMarkerOptions object
		markerOptions = { icon:blueIcon };
		
		var ko_doanh_so = new GIcon(G_DEFAULT_ICON);
		ko_doanh_so.image = "../images/ko_doanh_so.png";
		
		//ko_doanh_so.image = "../upload/Hydrangeas.jpg";
		ko_doanh_so.iconSize = new GSize(39, 39);
        
		// Set up our GMarkerOptions object
		marker_ko_ds = { icon:ko_doanh_so };
		

		
		var khSiIcon = new GIcon(G_DEFAULT_ICON);
		khSiIcon.image = "../images/thang_truoc_co.png";
		
		var khSiIcon2 = new GIcon(G_DEFAULT_ICON);
		khSiIcon2.image = "../images/khSiCDS.png";
		
		
		
		var blueIcon2 = new GIcon(G_DEFAULT_ICON);
	    blueIcon2.image = "http://gmaps-samples.googlecode.com/svn/trunk/markers/blue/blank.png";
	     
	     //blueIcon2.image = "../upload/Hydrangeas.jpg";
			
		// Set up our GMarkerOptions object
		markerOptions2 = { icon:blueIcon2 };
	    
	    var yellowIcon = new GIcon(G_DEFAULT_ICON);
	    yellowIcon.image = "../images/greenflag.png";
		markerOptions3 = { icon: yellowIcon };
		

		//Lấy data khách hàng
		<%
		if(khList != null)
		{
			int count = 0;
			int img_stt = 1;
			
			while(khList.next())
			{
		%>
				
	    khs.push({
	    	stt: parseFloat('<%= khList.getString("stt") == null  ? "1" : khList.getString("stt").trim().replaceAll("'","") %>'),
	    	isVT: parseFloat('<%= khList.getString("isVT") == null  ? "0" : khList.getString("isVT").trim().replaceAll("'","") %>'),
	    	thongtinkh: '<%= khList.getString("thongtinkh") == null ? " " : khList.getString("thongtinkh").trim().replaceAll("'","") %>', 	    	 
	    	lat: parseFloat('<%= khList.getString("lat") == null  ? "0" : khList.getString("lat").trim().replaceAll("'","") %>'), 
	    	lon: parseFloat('<%= khList.getString("long") == null  ? "0" : khList.getString("long").trim().replaceAll("'","") %>'),
	    	khoangcach: parseFloat('<%= khList.getString("khoangcach") == null  ? "0" : khList.getString("khoangcach").trim().replaceAll("'","") %>'),
	   		thoidiem: '<%= khList.getString("thoidiem") == null ? " " : khList.getString("thoidiem").trim().replaceAll("'","") %>'
	   <%--  	img_url: '../upload/<%= khList.getString("anhcuahang")%>' --%>
	    });
		          
			<% count++;  img_stt++;
				if(img_stt > 11)
					img_stt = 1;
			}
		}
		%>
		

			if(khs.length <=0)
			{
				<% if(bdBean.getAction().trim().length() >1 ){ %>
					alert('Hien tai chua ghi nhan duoc toa do');
				<%}%>
				
				return;
			}
		
		//Xử lý
		var z;
		
		var khLats = [];
		var khLons = [];
		
		
		center = new GLatLng(khs[0].lat,khs[0].lon);
        map.setCenter(center, 14);
		//Khách hàng
		var run = 0;
		for(z = 0; z < khs.length; z++) 
		{
			var kh = khs[z];
			if( 1==1 )
			{	run = run + 1;
				//Chỉnh các tọa độ trùng
				var latIndex = khLats.indexOf(kh.lat);
				var lonIndex = khLons.indexOf(kh.lon);
				if( latIndex >= 0 && lonIndex >= 0 && latIndex === lonIndex) {
					var ranLat = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
					var ranLon = (Math.random()*RANDOM_NUMER-RANDOM_NUMER/2)/100000;
					kh.lat += ranLat;
					kh.lon += ranLon;
				}
				khLats.push(kh.lat);
				khLons.push(kh.lon);
				
				var latlon = new GLatLng(kh.lat, kh.lon);
				var marker;
				  if(z == 0|| z == khs.length - 1) {
					marker = new GMarker(latlon,  markerOptions2);
				} else  if( kh.isVT >0) {
					marker = new GMarker(latlon,   marker_ko_ds);
				}else
					{
						marker = new GMarker(latlon,   markerOptions3);
					}  
				var marker1 =  new ELabel(latlon,run, "style1", new GSize(-20,0), 100 );

				GEvent.addListener(marker, "click", (
					function(kh, latlon) { 
						return function () 
						{
							//var info = "<b>Khách hàng: </b>"+kh.ten + (kh.si == '1' ? " (KH Sỉ) " : "") + "<br /><b>Số điện thoại: </b>"+kh.sdt+"<br /><b>Doanh số TB 3 tháng: </b>"+kh.dsThangTruoc+"<br /><b>Doanh số tháng này: </b>" + kh.dsTrongThang;
							
							var info = 	"<table style='width: 400px' cellpadding='2' cellspacing='1' > " +
								(kh.isVT>0?
										"	<tr> " +
										"		<td style='width: 100px;font-weight:100;' > " +
										(kh.isVT >0 ? "			<b>Khach hang: </b> ":"<b>__</b>") +
										"		</td> " +
										"		<td style='width: 300px;font-weight:300;' > " +
										(kh.isVT >0 ? kh.thongtinkh:"")  +
										"		</td> " +
										"	</tr> " 
										:""
								)+
								"	<tr> " +
								"		<td style='width: 100px;font-weight:100;' > " +
								"			<b>Khoảng cách: </b> " +
								"		</td> " +
								"		<td style='width: 300px;font-weight:300;' > " +
								(Math.round(kh.khoangcach)/1000)+" km " +
								"		</td> " +
								"	</tr> "  +
								"	<tr> " +
								"		<td style='width: 100px;font-weight:100;' > " +
								"			<b>Thời điểm: </b> " +
								"		</td> " +
								"		<td style='width: 300px;font-weight:300;' > " +
								kh.thoidiem +
								"		</td> " +
								"	</tr> "  +
							
							"</table> ";
							
							map.openInfoWindowHtml(latlon, info);
						};
					}
				)(kh, latlon));
				map.addOverlay(marker);
				map.addOverlay(marker1);
				
			}					
		}
  			
		for(z = 0; z < khs.length-1; z++) 
		{
			var kh = khs[z];
			var kh1 = khs[z + 1];
			var polyline = new GPolyline([
  	                                    new GLatLng( kh.lat,kh.lon ),
  	                                    new GLatLng( kh1.lat,kh1.lon )
  	                                  ], "#FF0000", 5);
  	                                  map.addOverlay(polyline);
		}
		
		/* var flightPlanCoordinates = [
		                             new google.maps.LatLng(khs[0].lat, khs[0].lon),
		                             new google.maps.LatLng(khs[1].lat, khs[1].lon),
		                             new google.maps.LatLng(-18.142599, 178.431),
		                             new google.maps.LatLng(-27.46758, 153.027892)
		                           ];
		                           var flightPath = new google.maps.Polyline({
		                             path: flightPlanCoordinates,
		                             geodesic: true,
		                             strokeColor: '#FF0000',
		                             strokeOpacity: 1.0,
		                             strokeWeight: 2
		                           });

		                           flightPath.setMap(map); */

		
		
     <%--  <% if( latconditon != null ) 
      {
    	  for(int i = 0; i < latconditon.length - 1; i++ ) 
    	  {
    		  String lat_long = latconditon[i] + ", " + longconditon[i];
    		  String lat_long2 = latconditon[i + 1] + ", " + longconditon[i + 1];
    		  %>
    		  
    		  var polyline = new GPolyline([
    	                                    new GLatLng(<%= lat_long %>),
    	                                    new GLatLng(<%= lat_long2 %>)
    	                                  ], "#FF0000", 5);
    	                                  map.addOverlay(polyline);
    		  
    	  <% } %>
      /* var polyline = new GPolyline([
                                    new GLatLng(10.8169844, 106.6872128),
                                    new GLatLng(10.8165455, 106.6876032)
                                  ], "#66FF33", 5);
                                  map.addOverlay(polyline); */
     <% } %>  --%>
      }
    }
    
    function showAddress(address) 
    {
    	 geocoder.getLatLng(
    	    address,
    	    function(point) 
    	    {
    	      if (!point) 
    	      {
    	        alert("Không thể tìn thấy địa chỉ:" + address);
    	      } 
    	      else 
    	      {
	    	        map.setCenter(point, 14);
	    	        var marker = new GMarker(point);
	    	        map.addOverlay(marker);
	
	    	        // As this is user-generated content, we display it as
	    	        // text rather than HTML to reduce XSS vulnerabilities.
	    	        marker.openInfoWindow(document.createTextNode("Địa chỉ: " + address));
	    	        document.getElementById("trungtam").value = point.lat() + "," + point.lng();
    	      }
    	    }
    	  );
    }
    
    </script>

    
    
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
  	
  	<script type="text/javascript">
	  	function submitform()
		{  
		    document.forms["bdForm"].submit();
		}
	  	
	  	function search()
	  	{
	  		
	  		document.forms['bdForm'].action.value= 'viewBd';
	  		document.forms['bdForm'].submit();
	  		
	  	}
	  	function searchTT()
	  	{
	  		
	  		document.forms['bdForm'].action.value= 'viewBdTT';
	  		document.forms['bdForm'].submit();
	  		
	  	}

	  	
  	</script>
  	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
	
<script>
$(document).ready(function()
{
	$(".select2").select2();
	
});
</script>

<style type="text/css">
	input
	{
		padding: 2px 0px;
	}
</style>
  	
  	
</head>

<body onload="initialize()" onunload="GUnload()">
<form name="bdForm" method="post" action="../../BandoTTSvl">
<input type="hidden" name="view" value='lotrinhOnlineNPP'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="trungtam" id="trungtam" value=''>

<div id="main" style="width:99.5%; padding-left:2px">

	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:40%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
    
    <div style="width:100%;" align="left">
    <fieldset>
    	<legend class="legendtitle">Thông tin lộ trình online</legend>
        <TABLE width="100%" cellpadding="4" cellspacing="0" style="background-color: #C5E8CD;">	   
            <TR>
                <TD class="plainlabel" width="150px" >Ngày </TD>
                <TD class="plainlabel" width="200px" >
                    <input type="text" size="11" class="days" 
                          id="ngay" name="ngay" value="<%= bdBean.getDate() %>" maxlength="10" readonly onchange="submitform();"  />
                </TD>
               
                
                
              
				
                <TD class="plainlabel" width="120px"></TD>
                <TD class="plainlabel"  >
                </TD>
                <TD class="plainlabel" >Chi nhánh/ Đối tác </TD>
                <TD class="plainlabel">
                    <select name="npp"  onChange = "submitform();" id="nppId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(nppRs != null){
								  try{ while(nppRs.next()){ 
					    			if(nppRs.getString("pk_seq").equals(bdBean.getNppId())){ %>
					      				<option value='<%= nppRs.getString("pk_seq")%>' selected><%= nppRs.getString("ten") %></option>
					      			<%}else{ %>
					     				<option value='<%= nppRs.getString("pk_seq")%>'><%= nppRs.getString("ten") %></option>
					     	<%}} nppRs.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
                

                
            </TR>
            <TR>
               <TD class="plainlabel" >Trình dược viên </TD>
                <TD class="plainlabel">
                    <select name="ddkd"  onChange = "submitform();" id="ddkdId"  style="width:200px" class="select2" >
                            <option value="" selected></option>
                            <% if(ddkd != null){
								  try{ while(ddkd.next()){ 
					    			if(ddkd.getString("ddkdId").equals(bdBean.getDdkdId())){ %>
					      				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
					     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
               
                
                 
              
            </TR>
            <TR>
                
               
                
	
				
                
				
            </TR>
            <TR >                                                                          
         	<td class="plainlabel">Giải thích </td>
			  <TD class="plainlabel" colspan="6"  >
                     <img src="../images/ko_doanh_so.png" width="30" height="30" alt=""> Viếng thăm Khách Hàng &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
                   <!--  <img src="../images/maps_human.png" width="30" height="30" alt=""> KH có doanh số &nbsp;&nbsp;&nbsp;&nbsp; -->
				
			
                	<img src="../images/greenflag.png" alt="" width="16"> Vị trí đã đi qua &nbsp;&nbsp;&nbsp;&nbsp;
		        
              
          		<img src="http://gmaps-samples.googlecode.com/svn/trunk/markers/blue/blank.png" alt="" width="16"> Bắt đầu/Hiện tại &nbsp;&nbsp;&nbsp;&nbsp;
           
               </TD>
			                 
            </TR>

			
            <tr>
			<TD class="plainlabel" ><a class="button2" href="javascript:search();"> 
				
				 <img style="margin-bottom: -10px;" src="../images/button.png" alt=""> Lộ trình TDV  </a>
						  </TD>
			
			
            
			
            
            
        </TABLE>
         
     	<hr>
    
	<div id="map_canvas" style="width: 100%; height: 650px; position:static; margin-left:auto; margin-right:auto">
    </div>
    
    </fieldset></div>
 </div>
 
 </form>
</body>
</html>

<% 
if(khList != null)
	khList.close();
if(nppRs != null)
	nppRs.close();
if(ddkd != null)
	ddkd.close();
if(vungRs != null)
	vungRs.close();
if(ttRs != null)
	ttRs.close();
bdBean.DBclose(); 

%>

<%}%>
