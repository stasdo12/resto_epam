package com.epam.donetc.restaurant.controller.command.get;

import com.epam.donetc.restaurant.database.entity.Receipt;
import com.epam.donetc.restaurant.database.entity.User;
import com.epam.donetc.restaurant.service.ReceiptService;
import com.epam.donetc.restaurant.controller.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ClientOrdersCommand implements ICommand {

    private int page = 1;
    ReceiptService receiptService = new ReceiptService();
    Logger log = LogManager.getLogger(ClientOrdersCommand.class);

    /**
     * Called from the doGet method in the front controller. Gets the required path and passes attributes from the session
     * request
     *
     * @param req to get the message attribute from the session and put it into the request
     * @return the user's orders page after trying to display the page
     */

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(
                    req.getParameter("page"));
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Receipt> receipts;
        int recordsPerPage = 10;
        receipts = receiptService.getAllReceiptByUserIdPagination(user.getId(),
                (page - 1) * recordsPerPage, recordsPerPage);
        log.trace(receipts.size());
        int noOfRecords = receiptService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        session.setAttribute("receipts", receipts);
        return "/WEB-INF/jsp/client-orders";

    }
}
