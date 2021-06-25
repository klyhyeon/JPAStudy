package learn.jpa;

import learn.jpa.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
//            Crew crew = new Crew(RoleType.USER, RoleType.ADMIN);
//            em.persist(crew);
//
//            Instant instant = Instant.now();
//            TemporalTestEntity temporalTestEntity = new TemporalTestEntity(Date.from(instant), Date.from(instant), Date.from(instant));
//            em.persist(temporalTestEntity);
//
//            Byte[] bytes = {123,1,16};
//            LobTestEntity lobTestEntity = new LobTestEntity("이것은 문자열", bytes);
//            em.persist(lobTestEntity);

//            MemberDirect memberDirect = new MemberDirect(13L);
//            em.persist(memberDirect);
//
//            MemberIdentity memberIdentity = new MemberIdentity();
//            em.persist(memberIdentity);
//
//            MemberIdentity memberIdentity2 = new MemberIdentity();
//            em.persist(memberIdentity2);

//            MemberSequence memberSequence1 = new MemberSequence();
//            MemberSequence memberSequence2 = new MemberSequence();
//            MemberSequence memberSequence3 = new MemberSequence();
//
//            em.persist(memberSequence1);
//            em.persist(memberSequence2);
//            em.persist(memberSequence3);

            MemberTable memberTable1 = new MemberTable();
            MemberTable memberTable2 = new MemberTable();
            MemberTable memberTable3 = new MemberTable();

            em.persist(memberTable1);
            em.persist(memberTable2);
            em.persist(memberTable3);
        }
    }
}
