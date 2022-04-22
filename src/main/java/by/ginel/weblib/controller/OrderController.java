package by.ginel.weblib.controller;


import by.ginel.weblib.dto.*;
import by.ginel.weblib.entity.CartBook;
import by.ginel.weblib.entity.OrderStatus;
import by.ginel.weblib.entity.PersonRole;
import by.ginel.weblib.service.api.BookService;
import by.ginel.weblib.service.api.OrderBookService;
import by.ginel.weblib.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderBookService orderBookService;

    @GetMapping("/order")
    public ModelAndView placeOrder(HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null){
            List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
            if (cart == null || cart.size()==0){
                session.setAttribute("error","Cart is empty please go to catalog");
                return new ModelAndView("redirect:/mistake");
            }

            OrderCreateDto order = OrderCreateDto.builder()
                    .personId(person.getId())
                    .status(OrderStatus.BOOKED.toString())
                    .build();
            OrderGetDto newOrder = orderService.save(order);

            cart.stream()
                    .map(item -> OrderBookCreateDto.builder()
                        .orderId(newOrder.getId())
                        .quantity(item.getQuantity())
                        .bookId(item.getBookId())
                        .build()
                    )
                    .collect(Collectors.toList())
                    .forEach(orderBook -> orderBookService.save(orderBook));

            return new ModelAndView("redirect:/home");
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/orders")
    public ModelAndView getOrders(HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null){
            List<OrderGetDto> orders = orderService.getAllByPersonId(person.getId());
            if (orders == null || orders.size()==0){
                session.setAttribute("error","you dont have active orders");
                return new ModelAndView("redirect:/mistake");
            }
            return new ModelAndView("orders")
                    .addObject("orders",orders)
                    .addObject("person",session.getAttribute("person"));
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/orderslist")
    public ModelAndView getOrdersList(HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null && person.getRole() == PersonRole.ADMIN.toString()){
            List<OrderGetDto> orders = orderService.getAll();
            if (orders == null || orders.size()==0){
                session.setAttribute("error","There are no active orders");
                return new ModelAndView("redirect:/mistake");
            }
            return new ModelAndView("orders_list")
                    .addObject("orders",orders)
                    .addObject("person",session.getAttribute("person"));
        }
        else{
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/order/{id}")
    public ModelAndView detailOrder(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null){

            List<OrderGetDto> orders = new ArrayList<>();
            if(person.getRole() == PersonRole.ADMIN.toString()) {
                orders = orderService.getAll();
            }
            else{
                orders = orderService.getAllByPersonId(person.getId());
            }
            if (orders == null || orders.size()==0){
                session.setAttribute("error","you dont have active orders");
                return new ModelAndView("redirect:/mistake");
            }

            for (OrderGetDto order:orders) {
                if (order.getId()==id){
                    List<BookGetDto> books = orderBookService.getAllByOrderId(id).stream()
                            .map(item -> bookService.getById(item.getBookId()))
                            .collect(Collectors.toList());
                    return new ModelAndView("order")
                            .addObject("books",books)
                            .addObject("person",session.getAttribute("person"));
                }
            }
            session.setAttribute("error", "there is no order with such id");
            return new ModelAndView("redirect:/mistake");
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/order/{id}/remove")
    public ModelAndView removeOrder(@PathVariable Long id, HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null){
            List<OrderGetDto> orders = orderService.getAllByPersonId(person.getId());
            if (orders == null || orders.size()==0){
                session.setAttribute("error","you dont have active orders");
                return new ModelAndView("redirect:/mistake");
            }

            for (OrderGetDto order:orders) {
                if (order.getId()==id && order.getStatus() == "BOOKED"){
                    orderService.delete(id);
                    return new ModelAndView("redirect:/orders");
                }
            }
            session.setAttribute("error", "there is no order with such id");
            return new ModelAndView("redirect:/mistake");
        }
        else{
            session.setAttribute("error", "Please login before accessing this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @GetMapping("/order/{id}/edit")
    public ModelAndView editOrder(@PathVariable Long id, HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if(person != null && person.getRole() == PersonRole.ADMIN.toString()){

            try {

                OrderGetDto order = orderService.getById(id);
                return new ModelAndView("edit_order")
                        .addObject("order",order)
                        .addObject("person",request.getSession().getAttribute("person"));
            } catch (NullPointerException e) {
                session.setAttribute("error", "There is no order with such id");
                return new ModelAndView("redirect:/mistake");
            }
        }
        else{
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }

    @PostMapping("/order/{id}/update")
    public ModelAndView updateOrder(@ModelAttribute OrderUpdateDto order, HttpServletRequest request){

        HttpSession session = request.getSession();
        PersonGetDto person = (PersonGetDto) session.getAttribute("person");
        if (person != null && person.getRole() == PersonRole.ADMIN.toString()) {

            orderService.updateStatus(order);
            return new ModelAndView("redirect:/orderslist");
        }
        else {
            session.setAttribute("error", "You cant access this feature");
            return new ModelAndView("redirect:/mistake");
        }
    }
}
