package com.lipeng.portalpay.feign;

import com.lipeng.pay.service.pay.AliPayMobileCallBackSyncService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: lipeng 910138
 * @Date: 2019/11/15 10:16
 */
@FeignClient("app-pay")
public interface AliMobilePayCallBackFeign extends AliPayMobileCallBackSyncService {

}