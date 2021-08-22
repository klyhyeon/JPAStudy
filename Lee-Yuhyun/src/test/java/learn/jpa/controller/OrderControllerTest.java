//package learn.jpa.controller;
//
//import learn.jpa.model.ch10.Member;
//import learn.jpa.model.ch10.Orders;
//import learn.jpa.model.ch10.Team;
//import learn.jpa.repository.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class OrderControllerTest {
//
//    private OrderController orderController;
//    private OrderRepository orderRepository;
//
//    @Autowired
//    public OrderControllerTest(OrderController orderController, OrderRepository orderRepository) {
//        this.orderController = orderController;
//        this.orderRepository = orderRepository;
//    }
//
//    @BeforeEach
//    void setUp() {
//        Member member = Member.builder()
//                .age(30)
//                .username("YUHYEON")
//                .team(Team.builder().name("team").build()).build();
//        Orders orders = Orders.builder()
//                        .member(member).build();
//        orderRepository.save(orders);
//    }
//
//    @Test
//    @DisplayName("지연로딩 exception 테스트")
//    void lazyFetchExceptionTest() {
//        orderController.view(1L);
//    }
//}