/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.application.plugin.crude.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kobo
 */
@MappedSuperclass
public class Traceable {
    
    @Column(name="tr_ver")
    public Integer version;
    
    @Column(name="tr_val")
    public Integer valenza;
    
    @Column(name="tr_ute")
    public String userId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="tr_dini")
    public Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="tr_dfin")
    public Date updateDate;
   
    
}
