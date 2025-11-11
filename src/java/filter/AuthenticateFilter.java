package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bi
 */
// Các trang này sẽ được bảo vệ
@WebFilter(filterName = "AuthenticateFilter", urlPatterns = {"/home.jsp", "/Cart.jsp", "/MainController"})
public class AuthenticateFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    public AuthenticateFilter() {
    }

    /**
     * Phương thức doFilter chính, kết hợp cả xác thực và chống cache.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        request.setCharacterEncoding("UTF-8");

        // Ép kiểu
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Kiểm tra đăng nhập
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Lấy hành động (action) từ request
        // Đây là chìa khóa để biết người dùng đang LÀM GÌ
        String action = httpRequest.getParameter("txtAction");

        // Trường hợp 1: Người dùng ĐÃ đăng nhập
        if (isLoggedIn) {
            // Thêm header chống cache
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);

            // Cho đi tiếp
            chain.doFilter(request, response);

            // Trường hợp 2: Người dùng CHƯA đăng nhập
        } else {
            // Kiểm tra xem họ có đang CỐ ĐĂNG NHẬP không
            // (Giả sử action đăng nhập của bạn là "login")
          
            if ("login".equals(action) || "signup".equals(action)) {

                // Nếu đúng là đang "login", hãy cho request đi tiếp
                // để Servlet xử lý việc đăng nhập
                chain.doFilter(request, response);

            } else {
                // Nếu họ làm hành động khác (ví dụ: "search", "update"...)
                // mà chưa đăng nhập, thì ĐUỔI về trang login
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            }
        }
    }

    // --- CÁC PHƯƠNG THỨC KHÁC GIỮ NGUYÊN ---
    // (Tôi đã xóa doBeforeProcessing và doAfterProcessing
    // vì chúng ta đã gộp logic vào doFilter cho rõ ràng)
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticateFilter:Initializing filter");
            }
        }
    }

    // ... (Các phương thức còn lại như getStackTrace, log... giữ nguyên như file cũ của bạn) ...
    // ... (Phần code này bạn tự giữ lại nhé) ...
    // (Phần code từ toString() trở xuống)
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticateFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticateFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
