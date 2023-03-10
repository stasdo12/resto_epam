package com.epam.donetc.restaurant.controller.command.get;

import com.epam.donetc.restaurant.database.entity.Dish;
import com.epam.donetc.restaurant.exeption.AppException;
import com.epam.donetc.restaurant.exeption.DBException;
import com.epam.donetc.restaurant.service.DishService;
import com.epam.donetc.restaurant.controller.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ClientMenuCommand implements ICommand {
    private int page = 1;

    DishService dishService = new DishService();

    Logger log = LogManager.getLogger(ClientMenuCommand.class);

    /**
     * Called from the doGet method in the front controller. Gets the required path and passes attributes from the session
     * request
     *
     * @param req to get the message attribute from the session and put it into the request
     * @return the user's menu page after trying to display the page
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(
                    req.getParameter("page"));
        }
        String category = req.getParameter("category");
        String sortBy = req.getParameter("sortBy");
        if (sortBy == null) sortBy = "category";
        HttpSession session = req.getSession();
        try {
            List<Dish> dishes;

            if (category == null || category.isEmpty() || category.equalsIgnoreCase("All")) {
                int recordsPerPage = 10;
                dishes = dishService.getDishesOnePage(
                        (page - 1) * recordsPerPage,
                        recordsPerPage);
                int noOfRecords = dishService.getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0
                        / recordsPerPage);
                req.setAttribute("noOfPages", noOfPages);
                req.setAttribute("currentPage", page);
            } else {
                dishes = dishService.getDishesByCategory(category);
            }
            log.debug("dishes size before sorting == " + dishes.size());
            dishes = dishService.sortBy(dishes, sortBy);
            log.debug("dishes were sorted");

            log.trace("current page == " + page);
            log.debug("dishes size before getDishOnPage == " + dishes.size());
            req.setAttribute("category", category);
            req.setAttribute("dishes", dishes);
        } catch (DBException ex) {
            log.error("In Client menu servlet doGet() ", ex);
            throw new AppException(ex);
        }
        return "/WEB-INF/jsp/client-menu";
    }
}
