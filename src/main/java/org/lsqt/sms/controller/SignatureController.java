package org.lsqt.sms.controller;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.sms.model.Result;
import org.lsqt.sms.model.Signature;
import org.lsqt.sms.model.SignatureQuery;
import org.lsqt.sms.service.SignatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.lsqt.sms.util.CommonUtils.resp;
import static org.lsqt.sms.util.CommonUtils.split2Array;


/**
 * @Description //签名
 * @Author Administrator
 * @Date 17:25 2018/12/11
 **/
@Controller(mapping={"/signature"})
public class SignatureController {

    private static final Logger log = LoggerFactory.getLogger(SignatureController.class);

    @Inject
    private SignatureService signatureService;

    @RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
    public Result<Map<String,Object>> saveOrUpdate(Signature form) {
        return resp(signatureService.saveOrUpdate(form));
    }

    @RequestMapping(mapping = {"/list", "/m/list"})
    public Page<Signature> queryForPage(SignatureQuery query) {
        return signatureService.queryForPage(query);
    }

    @RequestMapping(mapping = {"/delete", "/m/delete"})
    public Result<Map<String,Object>> delete(String signIds) {
        return resp(signatureService.delete(split2Array(signIds)));
    }


}
