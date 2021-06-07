package com.incture.lch.service.implementation;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardMailDto;
import com.incture.lch.entity.CarrierDetails;
import com.incture.lch.repository.YardManagementHistoryRepository;
import com.incture.lch.service.YardMailService;

@Service
@Transactional
public class YardMailServiceImplementation implements YardMailService{
	@Autowired
	private YardManagementHistoryRepository repo;

	@Override
	public ResponseDto sendEmailToCarrier(List<YardMailDto> dtos) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.debug", "true");
		props.put("mail.user", "urmil.sarit@incture.com");
		props.put("mail.password", "goingtoALLEN@26");
		// SSL handshake issue
		props.put("mail.smtp.socketFactory.fallback", "false");
		// props.put("mail.smtp.ssl.enable", "true");
		ResponseDto responseDto = new ResponseDto();
		for(YardMailDto dto:dtos)
		{
		String bpNumber = dto.getCarrierId();

		List<CarrierDetails> carrierDetails = repo.sendCarrierEmail(bpNumber);
		/*for (CarrierDetails c : carrierDetails) {*/
			String carrierEmail = carrierDetails.get(0).getCarrierEmail();

			System.err.println("Email of the Carrier::::" + carrierEmail);
			InternetAddress recipientAddress = new InternetAddress();
			recipientAddress = new InternetAddress(carrierEmail);

			// Get the Session object.
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("urmil.sarit@incture.com", "goingtoALLEN@26");
				}
			});

			session.setDebug(true);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("urmil.sarit@incture.com"));
			message.setSubject("The Trailer has left the Yard");

			// set recipient -> for single input, //addrecepients -> for
			// multiple inputs as in array
			//message.addRecipients(Message.RecipientType.CC, ccAddress);
			message.addRecipient(Message.RecipientType.TO, recipientAddress);

			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();

			  String body="<img src=\"cid:image\">" 
    		
	    		+"<html> <body><font color =\"blue\"> <h3>Yard Management</font></h3>"
	    		+ "<br>The trailer; TRAILER NUMBER: "+dto.getTrailerNo()+ " has succesfully left the Yard;"
	    		
	    		+ "<br><font color=\"blue\"><h4>Yard Location :-"+dto.getLocation()+"</font></h4>"
	    		+ "Thank You for the Service, Regards";
	    		
			// Setting the text body in the mail
			//messageBodyPart.setContent("Hello there "+"The order has left the Yard ", "text/html");
			  messageBodyPart.setContent(body, "text/html");
			multipart.addBodyPart(messageBodyPart);

	        try {
	            messageBodyPart = new MimeBodyPart();
	            InputStream imageStream = YardMailServiceImplementation.class.getResourceAsStream("/lch/src/main/resources/Inctureblue.png");
	             System.out.println("IMAGE STREAM::"+imageStream);
	             imageStream = YardMailServiceImplementation.class.getClassLoader().getResourceAsStream("Inctureblue.png");
	             DataSource fds = new ByteArrayDataSource(IOUtils.toByteArray(imageStream), "image/png");
	             messageBodyPart.setDataHandler(new DataHandler(fds));
	             messageBodyPart.setHeader("Content-ID","<image>");
	             multipart.addBodyPart(messageBodyPart);
	             message.setContent(multipart);
	             
	             Transport.send(message);
//	             System.err.println("MESSAGE"+ message);
//	             System.out.println("Email sent Successfully");
	            
	             responseDto.setCode("1");
	             responseDto.setStatus("SUCCESS");
	             responseDto.setMessage("Mail Sent Succesfully");
	        }
	        catch (Exception e) 
	        {
	        	responseDto.setCode("0");
	        	responseDto.setStatus("FAILURE");
	        	responseDto.setMessage("Exception"+e.getMessage());
	            System.err.println("EXCEPTION VCAUGHT HERE:::::"+e.getStackTrace());
	            e.printStackTrace();
	            
	        }
			

		
		}
		return responseDto;
	}

}
