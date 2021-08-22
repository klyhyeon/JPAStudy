package learn.jpa.service;

import learn.jpa.model.ch10.Member;
import learn.jpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class HelloService {

    private MemberRepository memberRepository;

    @Autowired
    public HelloService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //트랜잭션 시작
    @Transactional
    public Member logic() throws Exception {
        //member는 Transactional이 시작된 상태기 때문에 영속 상태임
        Member member = memberRepository.findById(1L).orElseThrow(Exception::new);
        return member;
    }
    //트랜잭션 종료

}
