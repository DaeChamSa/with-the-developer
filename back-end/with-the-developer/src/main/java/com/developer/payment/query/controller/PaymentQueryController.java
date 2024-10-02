package com.developer.payment.query.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "payment", description = "결제 API")
@RestController
@RequestMapping("/payment")
public class PaymentQueryController {


}
