package lab.wallet;
public class SafeIntegration {
    // CONTROL: java/sql-injection,cwe89-jdbc-sqli
    public java.sql.ResultSet query(java.sql.Connection connection, String input) throws Exception {
        java.sql.PreparedStatement query=connection.prepareStatement("SELECT value FROM settings WHERE name=?");
        query.setString(1,input);
        return query.executeQuery();
    }
    // CONTROL: java/xss
    public void html(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        response.getWriter().write(org.springframework.web.util.HtmlUtils.htmlEscape(request.getParameter("title")));
    }
    // CONTROL: java/insecure-cookie,java/sensitive-cookie-not-httponly,cwe1004-cookie-missing-httponly-servlets
    public void cookie(jakarta.servlet.http.HttpServletResponse response) {
        jakarta.servlet.http.Cookie cookie=new jakarta.servlet.http.Cookie("auth_token","opaque-identifier");
        cookie.setSecure(true);cookie.setHttpOnly(true);cookie.setAttribute("SameSite","Strict");response.addCookie(cookie);
    }
}
