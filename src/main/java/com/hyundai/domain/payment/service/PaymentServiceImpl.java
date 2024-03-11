package com.hyundai.domain.payment.service;

import com.hyundai.domain.cart.dto.request.CartProductRequestDto;
import com.hyundai.domain.cart.service.CartService;
import com.hyundai.domain.orderProduct.entity.OrderProduct;
import com.hyundai.domain.orders.dto.OrderSaveDTO;
import com.hyundai.domain.orders.dto.OrdersRequestDTO;
import com.hyundai.domain.orders.entity.Orders;
import com.hyundai.domain.payment.entity.enums.PayStatus;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.OrderMapper;
import com.hyundai.global.mapper.OrderProductMapper;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Log4j2
public class PaymentServiceImpl {
    private final OrderMapper orderMapper;
    private final OrderProductMapper orderProductMapper;
    private final ProductMapper productMapper;
    private final CartService cartService;

    @Transactional
    public void saveOrder(String memberId, OrdersRequestDTO ordersRequestDTO) {
        List<OrderSaveDTO> orderSaveDtos = ordersRequestDTO.getOrders();

        OrderSaveDTO saveDto = orderSaveDtos.get(0);
        Orders orders = Orders.builder()
                .memberId(memberId)
                .orderNumber(saveDto.getOrderNumber())
                .receiverName(saveDto.getReceiverName())
                .phoneNumber(saveDto.getPhoneNumber())
                .zipcode(saveDto.getZipcode())
                .address(saveDto.getAddress())
                .orderRequired(saveDto.getOrderRequired())
                .orderStatus(PayStatus.SUCCESS.getStatus())
                .paymentMethod(saveDto.getPaymentMethod())
                .build();
        orderMapper.insertOrder(orders);

        for (OrderSaveDTO dto : orderSaveDtos) {
            log.info(dto.getProductId());

            int curStock = productMapper.selectProductStock(dto.getProductId());
            int orderStock = dto.getOrderCount();

            if (curStock - orderStock < 0) {
                throw new GlobalException(GlobalErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
            }

            OrderProduct orderProduct = OrderProduct.builder()
                    .orderId(orders.getOrderId())
                    .productId(dto.getProductId())
                    .orderProductPrice(dto.getOrderPrice())
                    .orderProductCount(dto.getOrderCount())
                    .orderProductDiscount(dto.getOrderDiscount())
                    .productImage(dto.getProductImage())
                    .build();
            orderProductMapper.insertOrderProduct(orderProduct);
            productMapper.updateProductStock(dto.getProductId(), dto.getOrderCount());
            CartProductRequestDto cartProductRequestDto = new CartProductRequestDto(Math.toIntExact(dto.getProductId()));
            cartService.saveOrDeleteCart(memberId, cartProductRequestDto);
        }
    }
}
