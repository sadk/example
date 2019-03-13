package org.lsqt.sms.service;

import org.lsqt.components.db.Page;
import org.lsqt.sms.model.Signature;
import org.lsqt.sms.model.SignatureQuery;

public interface SignatureService {

    Page<Signature> queryForPage(SignatureQuery query);

    String saveOrUpdate(Signature form);

    String delete(Long[] signatureIds);

}
