import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String timezoneParam = request.getParameter("timezone");

        if (isValidTimezone(timezoneParam)) {
            chain.doFilter(request, response);
        } else {

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = httpResponse.getWriter();
            out.println("Invalid timezone");
        }
    }

    private boolean isValidTimezone(String timezone) {
        if (timezone == null || timezone.isEmpty()) {
            return true;
        }
        return Arrays.asList(TimeZone.getAvailableIDs()).contains("GMT" + timezone);
    }
}

