package learn.jpa.service;

import learn.jpa.model.ch10.Orders;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderFacade {

    private OrderService orderService;

    @Autowired
    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public Orders findById(Long id) {
        Orders order = orderService.findById(id);
        //프록시 객체를 강제로 초기화 합니다.
        order.getMember().getUsername();
        return order;
    }
}
