package learn.jpa.controller;

import learn.jpa.model.ch10.Member;
import learn.jpa.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    private HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public void hello() throws Exception {
        //트랜잭션이 종료된 상태기 때문에 member 엔티티는 준영속 상태
        Member member = helloService.logic();
    }
}
