package learn.jpa.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
import java.util.List;

@DataJpaTest
public class ProductTest {

    @PersistenceUnit
    EntityManagerFactory emf;

    EntityManager em;
    EntityTransaction transaction;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @AfterEach
    public void close() {
        em.close();
        emf.close();
    }

    @Test
    public void save() {

        transaction.begin();

        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

//        Product newProduct = em.find(Product.class, productA.getId());
//        Assertions.assertEquals("productB", newProduct.getId());
        Member member1 = new Member();
        member1.setId("member1");
        member1.setName("회원1");
        member1.getProducts().add(productA);
        em.persist(member1);

        //회원상품 저장
//        MemberProduct memberProduct = new MemberProduct();
//        memberProduct.setMember(member1);
//        memberProduct.setProduct(productA);
//        memberProduct.setOrderAmount(2);
//
//        em.persist(memberProduct);

        Order order = new Order();
        order.setMember(member1);
        order.setProduct(productA);
        order.setOrderAmount(2);
        em.persist(order);

    }

    @Test
    public void find() {

        transaction.begin();

//        Member member = em.find(Member.class, 1L);
//        List<Product> products = member.getProducts();
//        for (Product p : products) {
//            System.out.println("productName: " + p.getName());
//        }

        //복합키 사용
        //기본키값 생성
//        MemberProductId memberProductId = new MemberProductId();
//        memberProductId.setMember("member1");
//        memberProductId.setProduct("productA");
//
//        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);
//
//        Member member = memberProduct.getMember();
//        Product product = memberProduct.getProduct();
//
//        System.out.println("member = " + member.getName());
//        System.out.println("product = " + product.getName());
//        System.out.println("orderAmount = " + memberProduct.getOrderAmount());

        //비식별자 사용
        Long orderId = 1L;
        Order order = em.find(Order.class, orderId);

        Member member = order.getMember();
        Product product = order.getProduct();

        System.out.println("member = " + member.getName());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount = " + order.getOrderAmount());


    }
}
