package com.incture.lch.util;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class GetReferenceData {

	public String execute(String type) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DATE));
		if (month.length() != 2) {
			month = "0" + month;
		}
		if (day.length() != 2) {
			day = "0" + day;
		}
		return type + year.substring(2, 4) + month + day;
	}

	
	public String executeAdhoc(String type) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DATE));
		if (month.length() != 2) {
			month = "0" + month;
		}
		if (day.length() != 2) {
			day = "0" + day;
		}
		return type + year.substring(2, 4) + month + day;
	}

	public String executePremiumRequestId(String type) {
		/*Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DATE));
		if (month.length() != 2) {
			month = "0" + month;
		}
		if (day.length() != 2) {
			day = "0" + day;
		}
		return type + year.substring(2, 4) + month + day;*/
		return type+"1000";
	}
	public String executeDmfrt(String type) {
		// Calendar now = Calendar.getInstance();
		// String year = now.get(Calendar.YEAR) + "";
		// String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		// String day = String.valueOf(now.get(Calendar.DATE));
		// if (month.length() != 2) {
		// month = "0" + month;
		// }
		// if (day.length() != 2) {
		// day = "0" + day;
		// }
		return type;
	}

	public String executeFo(String type) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DATE));
		String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (month.length() != 2) {
			month = "0" + month;
		}
		if (day.length() != 2) {
			day = "0" + day;
		}
		if (hour.length() != 2) {
			hour = "0" + hour;
		}
		return type + year.substring(2, 4) + month + day + hour;
	}



	public String getNextSeqNumberAdhoc(String referenceCode, int noOfDigits, SessionFactory sessionFactory) {
		return SequenceNumberGenAdhoc.getInstance().getNextSeqNumber(referenceCode, noOfDigits,
				sessionFactory.getCurrentSession());
	}
	
	public String getNextSeqNumberRequestId(String referenceCode, int noOfDigits, SessionFactory sessionFactory) {
		return SequenceNumberGenRequestId.getInstance().getNextSeqNumber(referenceCode, noOfDigits,
				sessionFactory.getCurrentSession());
	}


}
