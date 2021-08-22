package learn.jpa.controller;

import learn.jpa.model.ch10.Member;
import learn.jpa.model.ch10.Orders;
import learn.jpa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public String view(Long orderId) {
        Orders orders = orderService.findById(orderId);
        Member member = orders.getMember(); //지연 로딩 시 예외 발생
        return null;
    }
}
