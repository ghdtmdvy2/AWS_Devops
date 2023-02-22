package com.example.driveanalysis.order.controller;

import com.example.driveanalysis.user.dto.UserContext;
import com.example.driveanalysis.base.exception.ActorCanNotSeeOrderException;
import com.example.driveanalysis.base.exception.OrderIdNotMatchedException;
import com.example.driveanalysis.order.entity.ProductOrder;
import com.example.driveanalysis.order.service.ProductOrderService;
import com.example.driveanalysis.user.entity.SiteUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class ProductOrderController {
    private final ProductOrderService productOrderService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final String SECRET_KEY;
    ProductOrderController(@Value("${custom.tossPayments.secretKey}") String SECRET_KEY, ProductOrderService productOrderService, ObjectMapper objectMapper) {
        this.productOrderService = productOrderService;
        this.SECRET_KEY = SECRET_KEY;
        this.objectMapper = objectMapper;
    }
    @GetMapping("/")
    public String showOrder(@AuthenticationPrincipal UserContext userContext, Model model){
        List<ProductOrder> productOrders = productOrderService.findProductOrders(userContext.getId());
        model.addAttribute("productOrders",productOrders);
        return "order/order_list";
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal UserContext userContext, @PathVariable long id, Model model) {
        ProductOrder order = productOrderService.findProductOrder(id);
        SiteUser actor = userContext.getUser();
        if (!order.getOrderer().getId().equals(actor.getId())) {
            throw new ActorCanNotSeeOrderException("해당 사용자의 주문지가 아닙니다.");
        }
        model.addAttribute("order", order);

        return "order/order_detail";
    }
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@AuthenticationPrincipal UserContext userContext) {
        SiteUser user = userContext.getUser();
        ProductOrder order = productOrderService.createFromCartProductOrder(user);

        return "redirect:/order/%d".formatted(order.getId());
    }

    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }
    @RequestMapping("/{id}/success")
    public String confirmPayment(
            @PathVariable long id,
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            @AuthenticationPrincipal UserContext userContext,
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
            userContext.principalSetProductPaid();
            productOrderService.payTossPayments(order);
            return "redirect:/order/";
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "order/fail";
        }
    }
}
