package com.example.driveanalysis.order.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.base.exception.ActorCanNotSeeOrderException;
import com.example.driveanalysis.base.util.Ut;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal UserContext memberContext, @PathVariable long id, Model model) {
        ProductOrder order = productOrderService.findProductOrder(id);
        SiteUser actor = memberContext.getUser();
        long restCash = memberContext.getRestCash();
        if (order.getOrderer().getId() != actor.getId()) {
            throw new ActorCanNotSeeOrderException("해당 사용자의 주문지가 아닙니다.");
        }
        model.addAttribute("actorRestCash", restCash);
        model.addAttribute("order", order);

        return "order/order_detail";
    }
    @PostMapping("/makeOrder")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@AuthenticationPrincipal UserContext userContext) {
        SiteUser user = userContext.getUser();
        ProductOrder order = productOrderService.createFromCartProductOrder(user);

        return "redirect:/order/%d".formatted(order.getId());
    }
}
