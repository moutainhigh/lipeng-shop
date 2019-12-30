package com.lipeng.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.lipeng.base.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lipeng 910138
 * @Date: 2019/12/30 16:13
 */
@Controller
public interface PayService {

    @GetMapping("/queryPayment")
    BaseResponse<JSONObject> queryF2F(@RequestParam("paymentId") String paymentId);

    @GetMapping("/refund")
    BaseResponse<JSONObject> refund(@RequestParam("id") Long id);

    @GetMapping("/refund/query")
    BaseResponse<JSONObject> refundQuery(@RequestParam("id") Long id);

    @GetMapping("/cancel")
    BaseResponse<JSONObject> cancel(@RequestParam("id") Long id);

}