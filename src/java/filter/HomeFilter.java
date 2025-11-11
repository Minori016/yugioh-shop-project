/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.CardDTO;
import model.CardDAO;
import model.SetDTO;
import model.SetDAO;

/**
 *
 * @author bi
 */
@WebFilter(filterName = "HomeFilter", urlPatterns = {"/home.jsp"})
public class HomeFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public HomeFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("HomeFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("HomeFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        // 1. Ép kiểu request để có thể làm việc với thuộc tính (attribute)
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 2. Kiểm tra xem "danh_sach_the" đã được Servlet (lúc login) chuẩn bị chưa
        Object cardList = httpRequest.getAttribute("listC");

        // 3. Nếu CHƯA CÓ (cardList == null), nghĩa là người dùng vào thẳng home.jsp
        if (cardList == null) {
            if (debug) {
                log("HomeFilter: 'listC' is null. Fetching from database...");
            }

            try {
                // === PHẦN NÀY BẠN PHẢI SỬA CHO ĐÚNG VỚI DỰ ÁN CỦA BẠN ===

                // Ví dụ: Khởi tạo DAO của bạn
                // CardDAO dao = new CardDAO(); 
                CardDAO dao = new CardDAO();
                SetDAO setDAO = new SetDAO();
                // Ví dụ: Gọi hàm lấy tất cả thẻ
                // List<Card> list = dao.getAllCards();
                List<CardDTO> list = dao.getAllCard();
                java.util.List<model.SetDTO> listCater = setDAO.getAllSet();
                CardDTO staffPick = dao.getMostExpensiveCard();
                // Ví dụ: Đặt danh sách vào request
                // httpRequest.setAttribute("danh_sach_the", list);
                httpRequest.setAttribute("listC", list);
                httpRequest.setAttribute("listCater", listCater);
                httpRequest.setAttribute("p", staffPick);
                // ========================================================
                log("HomeFilter: Fetched and set 'listC'.");

            } catch (Exception e) {
                log("HomeFilter: Error fetching card list: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (debug) {
                log("HomeFilter: 'listC' already exists. Skipping.");
            }
        }

        // 4. Cho phép yêu cầu (request) đi tiếp đến trang home.jsp
        // Lúc này, 'danh_sach_the' đã được đảm bảo là CÓ
        chain.doFilter(request, response);

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("HomeFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("HomeFilter()");
        }
        StringBuffer sb = new StringBuffer("HomeFilter(");
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
