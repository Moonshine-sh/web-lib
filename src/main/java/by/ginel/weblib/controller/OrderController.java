package by.ginel.weblib.controller;


import by.ginel.weblib.dto.BookGetDto;
import by.ginel.weblib.dto.OrderGetDto;
import by.ginel.weblib.dto.OrderUpdateDto;
import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.OrderStatus;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.OrderService;
import by.ginel.weblib.service.api.PersonService;
import jdk.jfr.Percentage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PersonService personService;

    @GetMapping("/order")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView placeOrder(HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
        if (cart == null || cart.size() == 0) {
            session.setAttribute("error", "Cart is empty please go to catalog");
            return new ModelAndView("redirect:/mistake");
        }
        orderService.placeOrder(getUserFromContext(), cart);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getOrders(HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<OrderGetDto> orders = orderService.getAllByPersonId(getUserFromContext().getId());
        if (orders == null || orders.size() == 0) {
            session.setAttribute("error", "you dont have active orders");
            return new ModelAndView("redirect:/mistake");
        }
        return new ModelAndView("orders").addObject("orders", orders);
    }

    @GetMapping("/orderslist")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getOrdersList(HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<OrderGetDto> orders = orderService.getAll();
        if (orders == null || orders.size() == 0) {
            session.setAttribute("error", "There are no active orders");
            return new ModelAndView("redirect:/mistake");
        }
        return new ModelAndView("orders_list").addObject("orders", orders);
    }

    @GetMapping("/order/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailOrder(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<OrderGetDto> orders = orderService.getAllByUser(getUserFromContext());
        if (orders == null || orders.size() == 0) {
            session.setAttribute("error", "you dont have active orders");
            return new ModelAndView("redirect:/mistake");
        }
        List<BookGetDto> books = orderService.getBooksFromOrder(id, orders);
        if (Objects.nonNull(books))
            return new ModelAndView("order").addObject("books", books);
        else {
            session.setAttribute("error", "there is no order with such id");
            return new ModelAndView("redirect:/mistake");
        }
    }


    @GetMapping("/order/{id}/remove")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeOrder(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        List<OrderGetDto> orders = orderService.getAllByPersonId(getUserFromContext().getId());
        if (orders == null || orders.size() == 0) {
            session.setAttribute("error", "you dont have active orders");
            return new ModelAndView("redirect:/mistake");
        }
        if (orderService.removeOrder(id, orders))
            return new ModelAndView("redirect:/orders");
        else {
            session.setAttribute("error", "there is no order with such id");
            return new ModelAndView("redirect:/mistake");
        }
    }


    @GetMapping("/order/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editOrder(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        try {
            OrderGetDto order = orderService.getById(id);
            OrderStatus[] orderStatuses = OrderStatus.class.getEnumConstants();
            return new ModelAndView("edit_order")
                    .addObject("statuses", orderStatuses)
                    .addObject("order", order);
        } catch (NullPointerException e) {
            session.setAttribute("error", "There is no order with such id");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @PostMapping("/order/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView updateOrder(@ModelAttribute OrderUpdateDto order) {

        orderService.updateStatus(order);
        return new ModelAndView("redirect:/orderslist");
    }

    private PersonGetDto getUserFromContext() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personService.findByLogin(user.getUsername());
    }
}
