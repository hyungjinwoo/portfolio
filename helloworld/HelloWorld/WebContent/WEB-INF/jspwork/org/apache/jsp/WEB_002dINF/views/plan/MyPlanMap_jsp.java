/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.40
 * Generated at: 2019-06-12 00:28:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import org.json.simple.JSONArray;

public final class MyPlanMap_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("org.json.simple.JSONArray");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    \r\n");
      out.write("  ");

/*      float lat=(Float)request.getAttribute("lat");
    float lng=(Float)request.getAttribute("lng"); */
    
    JSONArray array = (JSONArray)request.getAttribute("jsonArray");
    //List<String> list = (List<String>)request.getAttribute("jsonCommentArray");
    JSONArray list = (JSONArray)request.getAttribute("jsonCommentArray");
  
      out.write("\r\n");
      out.write("  \r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>  \r\n");
      out.write("<head>    \r\n");
      out.write("<title>위도 경도 찾기</title>\r\n");
      out.write("   <style>\r\n");
      out.write("      #map {\r\n");
      out.write("        height: 100%;\r\n");
      out.write("      }\r\n");
      out.write("      \r\n");
      out.write("      /* Optional: Makes the sample page fill the window. */\r\n");
      out.write("      html, body {\r\n");
      out.write("        height: 100%;\r\n");
      out.write("        margin: 0;\r\n");
      out.write("        padding: 0;\r\n");
      out.write("      }\r\n");
      out.write("    </style>\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("    <script>\r\n");
      out.write("    \r\n");
      out.write("    $(function(){\r\n");
      out.write("    \tinitMap();\r\n");
      out.write("    });\r\n");
      out.write("    \r\n");
      out.write("    var locations = ");
      out.print(array);
      out.write(";\r\n");
      out.write("    var lists = ");
      out.print(list);
      out.write(";\r\n");
      out.write("    console.log(locations);\r\n");
      out.write("    console.log(lists);\r\n");
      out.write("    \r\n");
      out.write("    var map;\r\n");
      out.write("    var geocoder;\r\n");
      out.write("    var address;\r\n");
      out.write("    \r\n");
      out.write("    function initMap() {\r\n");
      out.write("            map = new google.maps.Map(document.getElementById('map'), {\r\n");
      out.write("                                              zoom: 1,\r\n");
      out.write("                                              center: locations[0],\r\n");
      out.write("                                              mapTypeId: 'hybrid'\r\n");
      out.write("                                            }\r\n");
      out.write("                                        );\r\n");
      out.write("           var markers = locations.map(function(location, i) {\r\n");
      out.write("              return new google.maps.Marker({\r\n");
      out.write("                position: location,\r\n");
      out.write("                animation: google.maps.Animation.DROP\r\n");
      out.write("              });\r\n");
      out.write("            });\r\n");
      out.write("            $.each(markers, function(index,item){\r\n");
      out.write("                \r\n");
      out.write("                markers[index].addListener('dblclick', function() {\r\n");
      out.write("                    markers[index].setMap(null);  //마우스 더블 클릭시에 마커제거\r\n");
      out.write("                });\r\n");
      out.write("                \r\n");
      out.write("                markers[index].addListener('click', function() {\r\n");
      out.write("                    markers[index].setAnimation(google.maps.Animation.BOUNCE);\r\n");
      out.write("                    map.setCenter(markers[index].getPosition()); //마커을 기준으로 가운데로 지도을 옮김\r\n");
      out.write("                    map.setZoom(20);\r\n");
      out.write("                    \r\n");
      out.write("                    if(lists[index].comment!=\"\"){\r\n");
      out.write("\t                    new google.maps.InfoWindow({\r\n");
      out.write("\t                         content: lists[index].comment\r\n");
      out.write("\t                       }).open(map, markers[index]);\r\n");
      out.write("                    } \r\n");
      out.write("                    \r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("            var markerCluster = new MarkerClusterer(map, markers,\r\n");
      out.write("                {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});\r\n");
      out.write("      }// end initMap()\r\n");
      out.write("      \r\n");
      out.write("    </script>\r\n");
      out.write("    \r\n");
      out.write("    </head>\r\n");
      out.write("    \r\n");
      out.write("    <body>\r\n");
      out.write("    <div id=\"map\"></div>\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("  </body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}