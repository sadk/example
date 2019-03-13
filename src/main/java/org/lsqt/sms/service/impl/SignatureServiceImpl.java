package org.lsqt.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sms.model.Request;
import org.lsqt.sms.model.Signature;
import org.lsqt.sms.model.SignatureQuery;
import org.lsqt.sms.service.SignatureService;
import org.lsqt.sms.util.ConfigPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignatureServiceImpl implements SignatureService {

    private static final Logger log = LoggerFactory.getLogger(SignatureServiceImpl.class);

    @Inject
    private Db db;

    private String adv = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.adv");
    private String src = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.src");

    @Override
    public Page<Signature> queryForPage(SignatureQuery query) {
        return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Signature.class, query);
    }

    @Override
    public String saveOrUpdate(Signature form) {
        String msg = null;
        Map<String, Object> params = new HashMap<>();
        Request req = Request.newInstance();

        if (form.getSignatureId() == null || form.getSignatureId() == 0) {

            params.put("adv", adv);
            params.put("src", src);
            params.put("name", form.getName());

            String content = req.api("/query").serivceId("sms-create-sign").params(params).execute();
            log.info("response content: ", content);
            JSONObject response = JSON.parseObject(content).getJSONObject("Response");
            if (response.containsKey("Error")) {
                return response.getJSONObject("Error").getString("Message");
            }
            JSONObject data = response.getJSONObject("data");
            msg = data.getString("msg");
            if (data.getIntValue("ret") == 0) {
                int signatureId = data.getIntValue("data");
                db.executeUpdate("insert into sms_signature_info (signature_id, name, status, use_status, create_time) values (?, ?, ?, ?, now())", signatureId, form.getName(), 1, 0);
                log.debug("response signature id: {0}", signatureId);
            }
        } else {
            long[] signatureIds = {form.getSignatureId()};
            params.put("sign_id", signatureIds);
            String content = req.api("/query").serivceId("sms-query-sign").params(params).execute();
            JSONObject response = JSONObject.parseObject(content).getJSONObject("Response");
            if(response.containsKey("Error")) {
                return response.getJSONObject("Error").getString("Message");
            }
            JSONObject data = response.getJSONObject("data");
            msg = data.getString("msg");
            if (data.getIntValue("ret") == 0) {
                int status = data.getJSONArray("data").getJSONObject(0).getIntValue("status");
                log.debug("sign id: {0}, status: {1}", form.getId(), status);
                db.executeUpdate("update sms_signature_info set status = ?, update_time = now() where id = ?", status, form.getId());
            }
        }

        return msg;
    }

    @Override
    public String delete(Long[] signatureIds) {
        if (signatureIds == null || signatureIds.length == 0) {
            return "签名id不可为空";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sign_id", signatureIds);

        Request req = Request.newInstance();
        String content = req.api("/query").serivceId("sms-delete-sign").params(params).execute();
        log.debug("delete result: {0}", content);
        JSONObject response = JSONObject.parseObject(content).getJSONObject("Response");
        if(response.containsKey("Error")) {
            return response.getJSONObject("Error").getString("Message");
        }
        JSONObject data = response.getJSONObject("data");
        if (data.getIntValue("ret") == 0) {
            //db.deleteById(Signature.class, Arrays.asList(ids).toArray());
            JSONArray jsonArray = data.getJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int signId = jsonObject.getIntValue("sign_id");
            db.executeUpdate("delete from sms_signature_info where signature_id = ?", signId);
        }

        return data.getString("msg");
    }
}
