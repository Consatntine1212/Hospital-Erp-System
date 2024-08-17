package com.HospitalErp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "notice_details")
public class Notice {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "notice_id")
	private Long noticeId;

	@Column(name = "notice_summary")
	private String noticeSummary;

	@Column(name = "notice_details")
	private String noticeDetails;

	@Column(name = "notic_beg_dt")
	private LocalDate noticeBegDt;
	
	@Column(name = "notic_end_dt")
	private LocalDate noticeEndDt;
	
	@Column(name = "create_dt")
	private LocalDate createDt;
	
	@Column(name = "update_dt")
	private LocalDate updateDt;

	// Getters and setters

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeSummary() {
		return noticeSummary;
	}

	public void setNoticeSummary(String noticeSummary) {
		this.noticeSummary = noticeSummary;
	}

	public String getNoticeDetails() {
		return noticeDetails;
	}

	public void setNoticeDetails(String noticeDetails) {
		this.noticeDetails = noticeDetails;
	}

	public LocalDate getNoticeBegDt() {
		return noticeBegDt;
	}

	public void setNoticeBegDt(LocalDate noticeBegDt) {
		this.noticeBegDt = noticeBegDt;
	}

	public LocalDate getNoticeEndDt() {
		return noticeEndDt;
	}

	public void setNoticeEndDt(LocalDate noticeEndDt) {
		this.noticeEndDt = noticeEndDt;
	}

	public LocalDate getCreateDt() {
		return createDt;
	}

	public void setCreateDt(LocalDate createDt) {
		this.createDt = createDt;
	}

	public LocalDate getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(LocalDate updateDt) {
		this.updateDt = updateDt;
	}
}
