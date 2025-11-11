package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CardDAO;
import model.CardDTO;
import model.SetDAO;
import model.SetDTO;

/**
 *
 * @author bi
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    /**
     * Xử lý việc tải tất cả card cho trang chủ (nếu cần).
     */
    private void processGetAllCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CardDAO cardDAO = new CardDAO();
            List<CardDTO> listCard = cardDAO.getAllCard();
            request.setAttribute("listC", listCard);

            SetDAO setDAO = new SetDAO();
            List<SetDTO> listSet = setDAO.getAllSet();
            request.setAttribute("listCater", listSet);

            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at processGetAllCard: " + e.toString());
        }
    }

    /**
     * Xử lý việc lọc card theo Set (Category) cho trang chủ.
     */
    private void processGetAllCardBySet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String setID = request.getParameter("setID");
            CardDAO cardDAO = new CardDAO();
            List<CardDTO> listCard = cardDAO.getCardsBySetID(setID);
            request.setAttribute("listC", listCard);

            SetDAO setDAO = new SetDAO();
            List<SetDTO> listSet = setDAO.getAllSet();
            request.setAttribute("listCater", listSet);

            CardDTO staffPick = cardDAO.getMostExpensiveCard();
            request.setAttribute("p", staffPick);
            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at processGetAllCardBySet: " + e.toString());
        }
    }

    private void processViewDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String destinationPage = "viewCard.jsp";

        try {
            String cardID = request.getParameter("cardID");
            CardDAO cardDAO = new CardDAO();
            SetDAO setDAO = new SetDAO();

            CardDTO cardDetail = cardDAO.getCardByID(cardID);

            SetDTO cardSet = null;
            if (cardDetail != null) {
                cardSet = setDAO.getSetByID(cardDetail.getSetID());
            } else {
                System.out.println("Card not found with ID: " + cardID);
            }

            List<SetDTO> listSet = setDAO.getAllSet();
            CardDTO staffPick = cardDAO.getMostExpensiveCard();

            request.setAttribute("p", cardDetail);
            request.setAttribute("cardSet", cardSet);
            request.setAttribute("listCater", listSet);
            request.setAttribute("last", staffPick);

            request.getRequestDispatcher(destinationPage).forward(request, response);

        } catch (Exception e) {
            log("Error at processViewDetail: " + e.toString());
        }
    }

    private void processSearchCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchValue = request.getParameter("txtSearchValue");

            CardDAO cardDAO = new CardDAO();
            List<CardDTO> listCard = cardDAO.searchCardByName(searchValue);
            request.setAttribute("listC", listCard);

            SetDAO setDAO = new SetDAO();
            List<SetDTO> listSet = setDAO.getAllSet();
            request.setAttribute("listCater", listSet);

            CardDTO staffPick = cardDAO.getMostExpensiveCard();
            request.setAttribute("p", staffPick);

            request.setAttribute("txtSearchValue", searchValue);

            request.getRequestDispatcher("home.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at processSearchCard: " + e.toString());
        }
    }


    private void processManageProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CardDAO cardDAO = new CardDAO();
            SetDAO setDAO = new SetDAO();

            List<CardDTO> listCard = cardDAO.getAllCard();

            List<SetDTO> listSet = setDAO.getAllSet();

            // 3. Đặt thuộc tính để gửi sang JSP
            request.setAttribute("listC", listCard);   // Danh sách card
            request.setAttribute("listCater", listSet); // Danh sách set

            // 4. Chuyển đến trang manageProduct.jsp
            request.getRequestDispatcher("manageProduct.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at processManageProduct: " + e.toString());
        }
    }


    private void processAddCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=manageProduct";

        try {
            String cardID = request.getParameter("cardID"); 
            String cardName = request.getParameter("cardName");
            String rarity = request.getParameter("rarity");
            double price = Double.parseDouble(request.getParameter("price"));
            String imageUrl = request.getParameter("imageUrl");
            int setID = Integer.parseInt(request.getParameter("setID"));

        
            CardDTO newCard = new CardDTO(cardID, cardName, rarity, price, imageUrl, setID); // <-- ĐÃ SỬA

            
            CardDAO dao = new CardDAO();
            boolean success = dao.addCard(newCard);

            if (success) {
                request.setAttribute("MESSAGE", "Card added successfully!");
            } else {
                request.setAttribute("ERROR", "Failed to add card. Card code might exist.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid input for Price or SetID.");
            log("Error at processAddCard (NumberFormat): " + e.toString());
        } catch (Exception e) {
            request.setAttribute("ERROR", "An unexpected error occurred.");
            log("Error at processAddCard: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }

    private void processDeleteCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "MainController?txtAction=manageProduct";

        try {
            String cardID = request.getParameter("cardID");

            // 2. Gọi DAO để xóa
            CardDAO dao = new CardDAO();
            boolean success = dao.deleteCard(cardID);

            if (success) {
                request.getSession().setAttribute("MESSAGE", "Card '" + cardID + "' deleted successfully!");
            } else {
                request.getSession().setAttribute("ERROR", "Failed to delete card.");
            }

        } catch (Exception e) {
            request.getSession().setAttribute("ERROR", "An unexpected error occurred: " + e.getMessage());
            log("Error at processDeleteCard: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }

    private void processCallUpdateCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "updateCard.jsp"; // Trang sẽ chuyển đến

        try {
            // 1. Lấy cardID từ bên manageProduct.jsp)
            String cardID = request.getParameter("cardID");

            CardDAO cardDAO = new CardDAO();
            SetDAO setDAO = new SetDAO();

            // 2. Lấy thông tin của lá bài cần sửa
            CardDTO cardToUpdate = cardDAO.getCardByID(cardID);

            List<SetDTO> listSet = setDAO.getAllSet();

            // 4. Đặt thuộc tính lên request
            request.setAttribute("cardToUpdate", cardToUpdate);
            request.setAttribute("listCater", listSet);

        } catch (Exception e) {
            log("Error at processCallUpdateCard: " + e.toString());
            url = "MainController?txtAction=manageProduct"; // quay ve trang quang li
            request.getSession().setAttribute("ERROR", "Failed to load card details for update.");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void processUpdateCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Trang sẽ quay về sau khi update
        String url = "MainController?txtAction=manageProduct";

        try {
            String cardID = request.getParameter("cardID"); // Lấy từ input (readonly)
            String cardName = request.getParameter("cardName");
            String rarity = request.getParameter("rarity");
            double price = Double.parseDouble(request.getParameter("price"));
            String imageUrl = request.getParameter("imageUrl");
            int setID = Integer.parseInt(request.getParameter("setID"));

            CardDTO updatedCard = new CardDTO(cardID, cardName, rarity, price, imageUrl, setID);

            // 3. Gọi DAO để thực hiện UPDATE
            CardDAO dao = new CardDAO();
            boolean success = dao.updateCard(updatedCard);

            if (success) {
                request.getSession().setAttribute("MESSAGE", "Card '" + cardID + "' updated successfully!");
            } else {
                request.getSession().setAttribute("ERROR", "Failed to update card.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("ERROR", "Invalid input for Price or SetID.");
            log("Error at processUpdateCard (NumberFormat): " + e.toString());
        } catch (Exception e) {
            request.getSession().setAttribute("ERROR", "An unexpected error occurred.");
            log("Error at processUpdateCard: " + e.toString());
        } finally {

            response.sendRedirect(url);
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String txtAction = request.getParameter("txtAction");
        // Các action Xóa/Sửa sẽ được thêm vào đây
        if (txtAction == null) {
            txtAction = "login"; // Hoặc "home" tùy vào logic của bạn
        }
        if (txtAction.equals("categoryProduct")) {
            processGetAllCardBySet(request, response);
        } else if (txtAction.equals("viewDetail")) {
            processViewDetail(request, response);
        } else if (txtAction.equals("searchCard")) {
            processSearchCard(request, response);
        } else if (txtAction.equals("manageProduct")) {
            processManageProduct(request, response);
        } else if (txtAction.equals("addCard")) {
            processAddCard(request, response);
        } else if (txtAction.equals("callManageProduct")) {
            processManageProduct(request, response);

        } else if (txtAction.equals("deleteCard")) {
            processDeleteCard(request, response);
        } else if (txtAction.equals("callUpdateCard")) {
            processCallUpdateCard(request, response);

        } else if (txtAction.equals("updateCard")) {
            processUpdateCard(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
