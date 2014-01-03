package org.onetwo.plugins.email;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.log.MyLoggerFactory;
import org.onetwo.common.utils.Assert;
import org.onetwo.common.utils.FileUtils;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class JavaMailServiceImpl implements JavaMailService {

	private final Logger logger = MyLoggerFactory.getLogger(this.getClass());
	
	private static final String DEFAULT_ENCODING = "utf-8";
	
	private JavaMailSender javaMailSender;
	private Configuration configuration;
	private String encoding = DEFAULT_ENCODING;
	


	@Override
	public void send(MailInfo mailInfo) throws MessagingException {
		try {
			if(mailInfo.isMimeMail()){
				sendMimeMail(mailInfo);
			}else{
				sendTextMail(mailInfo);
			}
		} catch (Exception e) {
//			logger.error("发送邮件失败："+e.getMessage(), e);
			throw new MessagingException("发送邮件失败："+e.getMessage());
		}
	}

	protected void sendMimeMail(MailInfo mailInfo) throws Exception {

		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

		helper.setFrom(mailInfo.getFrom());
		helper.setTo(mailInfo.getTo());
		helper.setSubject(mailInfo.getSubject());

		String content = getContent(mailInfo);
		helper.setText(content, true);

		if(mailInfo.getAttachments()!=null){
			for(File attachment : mailInfo.getAttachments()){
				String fileName = FileUtils.getFileName(attachment.getName());
				helper.addAttachment(fileName, attachment);
			}
		}

		javaMailSender.send(msg);
		logger.info("HTML版邮件已发送至{}", StringUtils.join(mailInfo.getTo(), ","));
		
	}
	
	protected void sendTextMail(MailInfo mailInfo) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(mailInfo.getFrom());
		msg.setTo(mailInfo.getTo());
		msg.setSubject(mailInfo.getSubject());

		String content = getContent(mailInfo);
		msg.setText(content);

		javaMailSender.send(msg);
		if (logger.isInfoEnabled()) {
			logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
		}
	}
	
	private String getContent(MailInfo mailInfo) throws Exception{
		String content = "";
		if(mailInfo.isTemplate()){
			content = generateContent(mailInfo.getContent(), mailInfo.getTemplateContext());
		}else{
			content = mailInfo.getContent();
		}
		return content;
	}

	private String generateContent(String templatePath, Map<String, Object> context) throws Exception {
		Assert.notNull(configuration);
		Template template = this.configuration.getTemplate(templatePath, encoding);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
	}
	
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	

}