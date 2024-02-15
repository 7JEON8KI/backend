package com.hyundai.domain.payment.controller;

import com.hyundai.domain.orders.dto.OrderSaveDTO;
import com.hyundai.domain.payment.service.PaymentServiceImpl;
import com.hyundai.domain.payment.service.RefundServiceImpl;
import com.hyundai.global.config.ApplicationProperties;
import com.hyundai.global.message.ResponseMessage;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    private final RefundServiceImpl refundService;

    private IamportClient iamportClient;

    private final ApplicationProperties applicationProperties;

    private String apiKey;
    private String secretKey;

    @PostConstruct
    public void init() {
        this.apiKey = applicationProperties.getIMP_API_KEY();
        this.secretKey = applicationProperties.getIMP_SECRET_KEY();
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }
    // 요청으로 받은 주문 상품들을 저장
    @PostMapping("/payment")
    public ResponseEntity paymentComplete(@RequestBody List<OrderSaveDTO> orderSaveDtos) throws IOException {
        String orderNumber = orderSaveDtos.get(0).getOrderNumber();
        log.info("결제 요청 : 주문 번호 {}", orderSaveDtos.get(0).getReceiverName());
        try {
            paymentService.saveOrder(1001L, orderSaveDtos);
            log.info("결제 성공 : 주문 번호 {}", orderNumber);
            return ResponseMessage.SuccessResponse("결제 성공 : 주문 번호 ", orderNumber);
        } catch (RuntimeException e) {
            log.info("주문 상품 환불 진행 : 주문 번호 {}", orderNumber); // 만약 저장시에 예외가 발생하면 주문한 상품을 결제 취소
            String token = refundService.getToken(apiKey, secretKey);
            refundService.refundRequest(token, orderNumber, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // imp_uid(결제 고유 ID) 값을 받아 결제 상세 내역을 조회하는 함수
    // imp_uid 값은 발급받은 "가맹정 식별 코드"이고 클라이언트 측에서 요청할 때 넣어줌
    // 이 imp_uid 값으로 iamport에 검증 요청을 보내고, 해당 거래의 상세 정보를 조회하고 반환
    @PostMapping("/payment/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        return payment;
    }
}
