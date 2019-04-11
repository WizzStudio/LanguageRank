package com.wizzstudio.languagerank.handle;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.util.ResultUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandle {

//    @ExceptionHandler(IOException.class)
//    @ResponseBody
//    public ResponseEntity IOExceptionHandle(Exception e) {
//        return ResultUtil.error(-1,"上传图片失败");
//    }

}
