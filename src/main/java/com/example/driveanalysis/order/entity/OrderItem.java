package com.example.driveanalysis.order.entity;

import com.example.driveanalysis.base.config.BaseTimeEntity;
import com.example.driveanalysis.product.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
// OrderItem 과 CartItem 은 똑같은 entity 라고 봐도 무방하다.
// 왜냐하면 장바구니와 주문의 연결 관계를 끊기를 위한 entity 이기 때문이다.
// 왜 이런 구조를 만드냐면 장바구니는 쉽게 생성 및 삭제가 이루어지지만 주문 같은 경우 실제 결제가 이루어지는 부분이기 때문에 둘의 관계를 가지면 너무 위험하다.
// 그래서 OrderItem 이라는 테이블을 새로 만들어서 장바구니와 똑같은 용도로 사용하면 된다.
public class OrderItem extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    private ProductOrder productOrder;

    private LocalDateTime payDate;

    @ManyToOne
    private Product product;

    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가
    private int pgFee; // 결제대행사 수수료
    private int payPrice; // 결제금액
    private int refundPrice; // 환불금액
    private boolean isPaid; // 결제여부

    public OrderItem(Product product) {
        this.product = product;
    }

    public OrderItem() {

    }
}
