package learn.jpa.controller;

import learn.jpa.model.Member;
import learn.jpa.model.Team;
import learn.jpa.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public Member findById() {
        Member member = memberService.findById();
        member.setAge(40);
        memberService.doSomeThing();
        return null;
    }
}
