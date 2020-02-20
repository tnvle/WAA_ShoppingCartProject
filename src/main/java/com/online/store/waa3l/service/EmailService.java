package com.online.store.waa3l.service;

import java.util.List;

public interface EmailService {

	void sendEmail(String content, String subject, List<String> emailTo);

}
