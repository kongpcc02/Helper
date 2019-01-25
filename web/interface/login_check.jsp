<%-- 
    Document   : login_check
    Created on : 10 พ.ค. 2553, 13:16:23
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Login.LOGIN" %>
<%@page  import="com.Helper.System" %>
<%
//-----รับค่า POST-----//
            String VAR_USER_NAME = request.getParameter("txtUsername");
            String VAR_USER_PASS = request.getParameter("txtPassword");
//-----INSTANCE CLASS-----//
            LOGIN CLS_LOGIN = new LOGIN();
            System sys = new System();
//-----CALL LOGIN-----//
            CLS_LOGIN.call_login(VAR_USER_NAME, VAR_USER_PASS,
                    sys.getCONFIG_SYSID(),
                    request.getRemoteAddr());

//-----JASON-----//
            out.print("[");
            out.print("{");
            out.print("\"RESULT_CODE\":\"" + CLS_LOGIN.RESULT_CODE + "\",");
            out.print("\"RESULT_TEXT\":\"" + CLS_LOGIN.RESULT_TEXT + "\"");
            out.print("}");
            out.print("]");

//-----SESSION-----//
            if (CLS_LOGIN.RESULT_CODE == 0) {
                session.setAttribute("SESS_USER_ID", CLS_LOGIN.USER_ID);
                session.setAttribute("SESS_USER_DESC", CLS_LOGIN.USER_DESC);
                session.setAttribute("SESS_USER_DEPART", CLS_LOGIN.USER_DEPART);

            }//end if
%>
