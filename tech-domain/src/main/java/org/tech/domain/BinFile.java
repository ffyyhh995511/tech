package org.tech.domain;

import java.util.Date;

public class BinFile {
    private Integer id;

    private String newHardwareNum;

    private String newSoftwareNum;

    private String oldHardwareNum;

    private String oldSoftwareNum;

    private String mark;

    private Date createTime;

    private String title;

    private Date updateTime;

    private byte[] binData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewHardwareNum() {
        return newHardwareNum;
    }

    public void setNewHardwareNum(String newHardwareNum) {
        this.newHardwareNum = newHardwareNum == null ? null : newHardwareNum.trim();
    }

    public String getNewSoftwareNum() {
        return newSoftwareNum;
    }

    public void setNewSoftwareNum(String newSoftwareNum) {
        this.newSoftwareNum = newSoftwareNum == null ? null : newSoftwareNum.trim();
    }

    public String getOldHardwareNum() {
        return oldHardwareNum;
    }

    public void setOldHardwareNum(String oldHardwareNum) {
        this.oldHardwareNum = oldHardwareNum == null ? null : oldHardwareNum.trim();
    }

    public String getOldSoftwareNum() {
        return oldSoftwareNum;
    }

    public void setOldSoftwareNum(String oldSoftwareNum) {
        this.oldSoftwareNum = oldSoftwareNum == null ? null : oldSoftwareNum.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }
}