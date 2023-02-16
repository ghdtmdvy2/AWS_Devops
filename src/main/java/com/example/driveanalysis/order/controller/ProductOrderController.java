package com.example.driveanalysis.order.controller;

import com.example.driveanalysis.base.config.UserContext;
import com.example.driveanalysis.base.exception.ActorCanNotSeeOrderException;
import com.example.driveanalysis.base.exception.OrderIdNotMatchedException;
import com.example.driveanalysis.base.util.Ut;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class ProductOrderController {
    private final ProductOrderService productOrderService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    @Value("${tosspayments.secretkey}")
    private final String SECRET_KEY = "";

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal UserContext memberContext, @PathVariable long id, Model model) {
        ProductOrder order = productOrderService.findProductOrder(id);
        SiteUser actor = memberContext.getUser();
        long restCash = memberContext.getRestCash();
        if (!order.getOrderer().getId().equals(actor.getId())) {
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
    @RequestMapping("/{id}/success")
    public String confirmPayment(
            @PathVariable long id,
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            Model model
    ) throws Exception {

        ProductOrder order = productOrderService.findProductOrder(id);

        long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

        // 백엔드 단에서 주문 번호를 체크하여 문제(변조)가 없는치 체크하는 부분
        if ( id != orderIdInputed ) {
            throw new OrderIdNotMatchedException("OrderIdNotMatchedException");
        }
        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            productOrderService.payTossPayments(order);
            return "redirect:/product/list";
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "order/fail";
        }
    }
}