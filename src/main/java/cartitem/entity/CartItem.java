package cartitem.entity;

import com.ll.exam.sbb.base.config.BaseTimeEntity;
import com.ll.exam.sbb.emotion.entity.Emotion;
import com.ll.exam.sbb.product.entity.Product;
import com.ll.exam.sbb.user.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CartItem extends BaseTimeEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // 한 유저의 장바구니가 담는 게 여러개
    @ManyToOne
    private SiteUser buyer;

    // 장바구니 하나에 상품 여러개
    @ManyToOne
    private Product product;

    private int amount;
}
