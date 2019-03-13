package org.lsqt.sms.controller;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.model.Result;
import org.lsqt.sms.model.Templ;
import org.lsqt.sms.model.TemplQuery;
import org.lsqt.sms.service.TemplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 * 功能描述: 短信 文案 管理
 *
 * @param:
 * @return:
 */
@Controller(mapping = {"/sms/templ"})
public class TemplController {

    private static final Logger log = LoggerFactory.getLogger(TemplController.class);
    @Inject
    private TemplService service;

    @RequestMapping(mapping = "/list")
    public Page<Templ> queryForList(TemplQuery query) {
        return service.queryForPage(query);
    }

    @RequestMapping(mapping = "/saveOrUpdate")
    public Result<Map<String, Object>> saveOrUpdate(Templ form) {
        String resultMsg = service.saveOrUpdate(form);
        if (StringUtil.isBlank(resultMsg)) {
            return Result.ok("更新状态成功!");
        }
        return Result.fail(resultMsg);
    }

    @RequestMapping(mapping = "/deleteByIds")
    public Result<Map<String, Object>> deleteByIds(String ids, String templIds) {

        String resultMsg = service.delete(split2Array(ids), split2Array(templIds));
        if (StringUtil.isBlank(resultMsg)) {
            return Result.ok("删除成功!");
        }
        return Result.fail(resultMsg);
    }

    private Long[] split2Array(String ids) {
        String[] strs = ids.split(",");
        Long[] longs = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            longs[i] = Long.parseLong(strs[i]);
        }
        return longs;
    }
}
