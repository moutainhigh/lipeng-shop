package com.lipeng.web.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.lipeng.base.BaseResponse;
import com.lipeng.base.BaseWebController;
import com.lipeng.core.bean.MeiteBeanUtils;
import com.lipeng.core.utils.RandomValidateCodeUtil;
import com.lipeng.member.dto.UserInpDTO;
import com.lipeng.web.member.controller.req.vo.RegisterVo;
import com.lipeng.web.member.feign.MemberRegisterServiceFeign;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController extends BaseWebController {

    private static final String MB_REGISTER_FTL = "member/register";
    /**
     * 跳转到登陆页面页面
     */
    private static final String MB_LOGIN_FTL = "member/login";

    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;

    /**
     * 跳转到注册页面
     */
    @GetMapping("/register")

    public String getRegister() {
        return MB_REGISTER_FTL;
    }

    /**
     * 跳转到注册页面
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerVo") @Validated RegisterVo registerVo,
            BindingResult bindingResult, Model model, HttpSession httpSession) {
        // 1.接受表单参数 (验证码) 创建对象接受参数 vo do dto
        if (bindingResult.hasErrors()) {
            // 如果参数有错误的话
            // 获取第一个错误!
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return MB_REGISTER_FTL;
        }
        // 2.判断图形验证码是否正确
        //String graphicCode = registerVo.getGraphicCode();
        //Boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        //if (!checkVerify) {
        //    setErrorMsg(model, "图形验证码不正确!");
        //    return MB_REGISTER_FTL;
        //}
        //kapcha验证码校验
        String kaptchaReceived = registerVo.getGraphicCode();
        String kaptchaExpected = (String) httpSession.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaReceived == null || !kaptchaReceived.equals(kaptchaExpected)) {
            setErrorMsg(model, "图形验证码不正确!");
            return MB_LOGIN_FTL;
        }
        // 3.调用会员服务接口实现注册 将前端提交vo 转换dto
        UserInpDTO userInpDTO = MeiteBeanUtils.voToDto(registerVo, UserInpDTO.class);
        BaseResponse<JSONObject> register = memberRegisterServiceFeign
                .register(userInpDTO, registerVo.getRegistCode());
        if (!isSuccess(register)) {
            setErrorMsg(model, register.getMsg());
            return MB_REGISTER_FTL;
        }

        // 4.跳转到登陆页面
        return MB_LOGIN_FTL;
    }

}
