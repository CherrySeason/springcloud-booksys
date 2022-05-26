package com.example.provider.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "borrow_table")
public class Record implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8624456922724724540L;

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private Borrowid borrowid;
	@Column(name = "borrowtime")
    private Timestamp borrowtime;
    @Column(name = "returntime",nullable=true)
	private Timestamp returntime;
    @Column(name = "shouldtime")
	private Timestamp shouldtime;
    @Column(name = "fine")
	private float fine;
//    @ManyToOne
//    @JoinColumn(name = "userID")
//    private User user;
//    @ManyToOne
//    @JoinColumn(name = "bookID")
//    private Book book;

//	public Record(){};

    public Borrowid getBorrowid() {
		return borrowid;
	}
    
    public void setBorrowid(Borrowid borrowid) {
		this.borrowid=borrowid;
	}
    @Temporal(TemporalType.TIMESTAMP)
	public Timestamp getBorrowtime() {
		return borrowtime;
	}
	public void setBorrowtime(Timestamp borrowtime) {
		this.borrowtime = borrowtime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getReturntime() {
		return returntime;
	}
	public void setReturntime(Timestamp returntime) {
		this.returntime = returntime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getShouldtime() {
		return shouldtime;
	}
	public void setShouldTime(Timestamp shouldTime) {
		this.shouldtime = shouldTime;
	}
	public float getFine() {
		return fine;
	}
	public void setFine(float fine) {
		this.fine = fine;
	}
	
	
}
