package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.util.StringUtils;

public class SMSClass {
	public static void main(String[] args) throws InterruptedException, IOException {
		SMSClass object = new SMSClass();
		object.waitMethod();
	}

	private synchronized void waitMethod() throws InterruptedException, IOException {
		while(true) {
			try {
				System.out.println("always running program ==> " + Calendar.getInstance().getTime());
				String finalUrl="https://esatsang.itas.in/auth/login?next=%2F";	
				URL obj = new URL(finalUrl);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + responseCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				String response1=response.toString();
				System.out.println(response1);
				if(!response1.contains("Login is only allowed after the transmission starts."))
				{
					String finalUrl2="https://esatsang.itas.in/9nUCp0IbDlFoaudioout/live.m3u8";	
					URL obj2 = new URL(finalUrl2);
					HttpsURLConnection con2 = (HttpsURLConnection) obj2.openConnection();
					//con.setRequestMethod("GET");
					int responseCode2 = con2.getResponseCode();
					System.out.println("inner Response Code "+responseCode2);
					if(responseCode2!=404) {
						BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJJ2YKDFFGSX6CJOQ", "Y+pOQVIsH82R9GBKr4SpTmt7+U1VUFRJ9DZtE1dr");
						String SMSMessage = "Radhasoami, Login Enabled For E-Satsang Radhasoami";
						String[] mobileList = {
								"+919087772871","+919452290492","+918318160991","+919176745704","+919962388429","+919087772871"};
						for(String mobile:mobileList) {
							AmazonSNS snsClient = AmazonSNSClient
									.builder()
									.withRegion(Regions.AP_SOUTH_1)
									.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
									.build();							
							Map<String, MessageAttributeValue> smsAttributes = 
									new HashMap<String, MessageAttributeValue>();
							smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
									.withStringValue("Transactional") //Sets the type to promotional.
									.withDataType("String"));
							//smsAttributes.put("AWS.SNS.SMS.SenderID",new MessageAttributeValue().withStringValue("RS123").withDataType("String"));
							PublishResult result =snsClient.publish(new PublishRequest().withMessage(SMSMessage).withPhoneNumber(mobile).withMessageAttributes(smsAttributes));
							System.out.println(result);
						}


						String[] mobileList2= {"+919841586929","+13108966180","+919566145196","+919314436433","+919087772871"};
						for(String mobile:mobileList2)
						{
							AmazonSNS snsClient = AmazonSNSClient
									.builder()
									.withRegion(Regions.US_WEST_2)
									.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
									.build();							
							Map<String, MessageAttributeValue> smsAttributes = 
									new HashMap<String, MessageAttributeValue>();
							smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
									.withStringValue("Transactional") //Sets the type to promotional.
									.withDataType("String"));
							//smsAttributes.put("AWS.SNS.SMS.SenderID",new MessageAttributeValue().withStringValue("RS123").withDataType("String"));
							PublishResult result =snsClient.publish(new PublishRequest().withMessage(SMSMessage).withPhoneNumber(mobile).withMessageAttributes(smsAttributes));
							System.out.println(result);
							//this.wait(10000);
						}
						/*Runtime runtime = Runtime.getRuntime();
					BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
					Process proc = runtime.exec("shutdown -s -t "+(Long.parseLong("120")));
					System.exit(0);*/
						this.wait(7200000);
					}
					else {
						System.out.println("inner not");
					}
				}
				else
				{
					System.out.println("outer not");
				}
				try {
					this.wait(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			catch (Exception e)
			{
				System.out.println(e.getLocalizedMessage());
				this.wait(10000);
			}
		}
	}
}
