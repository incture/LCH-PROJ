package com.incture.lch.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.PremiumFreightChargeDetailsDto;
import com.incture.lch.entity.PremiumFreightChargeDetails;

@Repository
public class PremiumFreightChargeDetailsDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public PremiumFreightChargeDetailsDto exportPremiumFreightChargeDetailsDto(
			PremiumFreightChargeDetails pChargeDetails) {
		PremiumFreightChargeDetailsDto dto = new PremiumFreightChargeDetailsDto();
		dto.setOrderId(pChargeDetails.getorderId());
		// Origin Details
		dto.setOriginName(pChargeDetails.getOriginName());
		dto.setOriginCity(pChargeDetails.getOriginCity());
		dto.setOriginState(pChargeDetails.getOriginState());
		dto.setOriginCountry(pChargeDetails.getOriginCountry());
		dto.setOriginZip(pChargeDetails.getOriginZip());
		dto.setOriginAddress(pChargeDetails.getOriginAddress());
		// Destination Details
		dto.setDestinationAdress(pChargeDetails.getDestinationAdress());
		dto.setDestinationName(pChargeDetails.getDestinationName());
		dto.setDestinationCity(pChargeDetails.getDestinationCity());
		dto.setDestinationState(pChargeDetails.getDestinationState());
		dto.setDestinationCountry(pChargeDetails.getDestinationCountry());
		dto.setDestinationZip(pChargeDetails.getDestinationZip());
		// Carrier Details
		dto.setBpNumber(pChargeDetails.getBpNumber());
		dto.setCarrierDetails(pChargeDetails.getCarrierDetails());
		dto.setCarrierMode(pChargeDetails.getCarrierDetails());
		dto.setCarrierScac(pChargeDetails.getCarrierScac());
		dto.setCarrierRatePerKM(pChargeDetails.getCarrierRatePerKM());
		dto.setCharge(pChargeDetails.getCharge());

		dto.setReasonCode(pChargeDetails.getReasonCode());
		dto.setStatus(pChargeDetails.getStatus());
		dto.setPlannerEmail(pChargeDetails.getPlannerEmail());
		dto.setComment(pChargeDetails.getComment());

		return dto;
	}

	public PremiumFreightChargeDetails importPremiumFreightChargeDetailsDto(
			PremiumFreightChargeDetailsDto pChargeDetails) {
		PremiumFreightChargeDetails dto = new PremiumFreightChargeDetails();
		dto.setorderId(pChargeDetails.getOrderId());
		// Origin Details
		dto.setOriginName(pChargeDetails.getOriginName());
		dto.setOriginCity(pChargeDetails.getOriginCity());
		dto.setOriginState(pChargeDetails.getOriginState());
		dto.setOriginCountry(pChargeDetails.getOriginCountry());
		dto.setOriginZip(pChargeDetails.getOriginZip());
		dto.setOriginAddress(pChargeDetails.getOriginAddress());
		// Destination Details
		dto.setDestinationAdress(pChargeDetails.getDestinationAdress());
		dto.setDestinationName(pChargeDetails.getDestinationName());
		dto.setDestinationCity(pChargeDetails.getDestinationCity());
		dto.setDestinationState(pChargeDetails.getDestinationState());
		dto.setDestinationCountry(pChargeDetails.getDestinationCountry());
		dto.setDestinationZip(pChargeDetails.getDestinationZip());
		// Carrier Details
		dto.setBpNumber(pChargeDetails.getBpNumber());
		dto.setCarrierDetails(pChargeDetails.getCarrierDetails());
		dto.setCarrierMode(pChargeDetails.getCarrierDetails());
		dto.setCarrierScac(pChargeDetails.getCarrierScac());
		dto.setCarrierRatePerKM(pChargeDetails.getCarrierRatePerKM());
		dto.setCharge(pChargeDetails.getCharge());

		dto.setReasonCode(pChargeDetails.getReasonCode());
		dto.setStatus(pChargeDetails.getStatus());
		dto.setPlannerEmail(pChargeDetails.getPlannerEmail());
		dto.setComment(pChargeDetails.getComment());

		return dto;
	}

	public void saveOrUpdatePremiumFreightChargeDetails(PremiumFreightChargeDetailsDto dto) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(importPremiumFreightChargeDetailsDto(dto));
		session.flush();
		session.clear();
		tx.commit();
		session.close();
	}

}
