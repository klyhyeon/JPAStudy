package learn.jpa.service;

import learn.jpa.model.Member;
import learn.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member findById() {
        Member member = memberRepository.findById(1L)
            .orElseThrow();
        return member;
    }

    @Transactional
    public void doSomeThing() {

    }
}
