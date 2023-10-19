import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String timezone = request.getParameter("timezone");
        if (timezone == null) {

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            if (timezone == null) {
                timezone = "UTC";
            }
        } else {
            Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
            timezoneCookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(timezoneCookie);
        }
        String currentTime = getCurrentTimeInTimezone(timezone);
        request.setAttribute("currentDate", currentTime);
        RequestDispatcher dispatcher = request.getRequestDispatcher("time-template.html");
        dispatcher.forward(request, response);
    }
    private String getCurrentTimeInTimezone(String timezone) {
        return "2022-01-05 12:05:01 " + timezone;
    }
}


