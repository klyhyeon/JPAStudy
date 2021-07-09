package learn.jpa.model.ch08;


import learn.jpa.model.Member;

public class MemberProxy extends Member {

    Member target = null;

    public String getName() {

        if (target == null) {

            //2. 초기화 요청
            //3. DB 조회
            //4. 실제 엔티티 생성 및 참조 보관
//            this.target = ;
        }

        return target.getName();
    }
}
