
package com.sft.hrmsadmin.pojo_calsses.attendance_grace_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable
{

    @SerializedName("month_start")
    @Expose
    private String monthStart;
    @SerializedName("month_end")
    @Expose
    private String monthEnd;
    @SerializedName("year_start")
    @Expose
    private String yearStart;
    @SerializedName("year_end")
    @Expose
    private String yearEnd;
    @SerializedName("total_month_grace")
    @Expose
    private Integer totalMonthGrace;
    @SerializedName("availed_grace")
    @Expose
    private Integer availedGrace;
    @SerializedName("grace_balance")
    @Expose
    private Integer graceBalance;
    @SerializedName("availed_cl")
    @Expose
    private Double availedCl;
    @SerializedName("availed_el")
    @Expose
    private Double availedEl;
    @SerializedName("availed_sl")
    @Expose
    private Integer availedSl;
    @SerializedName("availed_ab")
    @Expose
    private Double availedAb;
    @SerializedName("total_availed_leave")
    @Expose
    private Double totalAvailedLeave;
    @SerializedName("granted_cl")
    @Expose
    private Double grantedCl;
    @SerializedName("cl_balance")
    @Expose
    private Double clBalance;
    @SerializedName("granted_el")
    @Expose
    private Double grantedEl;
    @SerializedName("el_balance")
    @Expose
    private Double elBalance;
    @SerializedName("granted_sl")
    @Expose
    private Double grantedSl;
    @SerializedName("sl_balance")
    @Expose
    private Double slBalance;
    @SerializedName("total_granted_leave")
    @Expose
    private Double totalGrantedLeave;
    @SerializedName("total_leave_balances")
    @Expose
    private Double totalLeaveBalances;
    private final static long serialVersionUID = -3827944955796363606L;

    public String getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(String monthStart) {
        this.monthStart = monthStart;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(String monthEnd) {
        this.monthEnd = monthEnd;
    }

    public String getYearStart() {
        return yearStart;
    }

    public void setYearStart(String yearStart) {
        this.yearStart = yearStart;
    }

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }

    public Integer getTotalMonthGrace() {
        return totalMonthGrace;
    }

    public void setTotalMonthGrace(Integer totalMonthGrace) {
        this.totalMonthGrace = totalMonthGrace;
    }

    public Integer getAvailedGrace() {
        return availedGrace;
    }

    public void setAvailedGrace(Integer availedGrace) {
        this.availedGrace = availedGrace;
    }

    public Integer getGraceBalance() {
        return graceBalance;
    }

    public void setGraceBalance(Integer graceBalance) {
        this.graceBalance = graceBalance;
    }

    public Double getAvailedCl() {
        return availedCl;
    }

    public void setAvailedCl(Double availedCl) {
        this.availedCl = availedCl;
    }

    public Double getAvailedEl() {
        return availedEl;
    }

    public void setAvailedEl(Double availedEl) {
        this.availedEl = availedEl;
    }

    public Integer getAvailedSl() {
        return availedSl;
    }

    public void setAvailedSl(Integer availedSl) {
        this.availedSl = availedSl;
    }

    public Double getAvailedAb() {
        return availedAb;
    }

    public void setAvailedAb(Double availedAb) {
        this.availedAb = availedAb;
    }

    public Double getTotalAvailedLeave() {
        return totalAvailedLeave;
    }

    public void setTotalAvailedLeave(Double totalAvailedLeave) {
        this.totalAvailedLeave = totalAvailedLeave;
    }

    public Double getGrantedCl() {
        return grantedCl;
    }

    public void setGrantedCl(Double grantedCl) {
        this.grantedCl = grantedCl;
    }

    public Double getClBalance() {
        return clBalance;
    }

    public void setClBalance(Double clBalance) {
        this.clBalance = clBalance;
    }

    public Double getGrantedEl() {
        return grantedEl;
    }

    public void setGrantedEl(Double grantedEl) {
        this.grantedEl = grantedEl;
    }

    public Double getElBalance() {
        return elBalance;
    }

    public void setElBalance(Double elBalance) {
        this.elBalance = elBalance;
    }

    public Double getGrantedSl() {
        return grantedSl;
    }

    public void setGrantedSl(Double grantedSl) {
        this.grantedSl = grantedSl;
    }

    public Double getSlBalance() {
        return slBalance;
    }

    public void setSlBalance(Double slBalance) {
        this.slBalance = slBalance;
    }

    public Double getTotalGrantedLeave() {
        return totalGrantedLeave;
    }

    public void setTotalGrantedLeave(Double totalGrantedLeave) {
        this.totalGrantedLeave = totalGrantedLeave;
    }

    public Double getTotalLeaveBalances() {
        return totalLeaveBalances;
    }

    public void setTotalLeaveBalances(Double totalLeaveBalances) {
        this.totalLeaveBalances = totalLeaveBalances;
    }

}
