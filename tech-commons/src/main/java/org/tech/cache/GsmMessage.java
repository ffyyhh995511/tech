package org.tech.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 4代锁通讯私有协议
 * 
 * @author fangyunhe
 *
 */
public class GsmMessage {
	private static final Logger log = Logger.getLogger(GsmMessage.class);
	
	/**
	 * 6 个字节
	 */
	private String macAddress;

	/**
	 * 2个字节
	 */
	private short seqId;

	/**
	 * 2个字节
	 */
	private short totalLength;

	/**
	 * 2个字节
	 */
	private short length;

	/**
	 * 2个字节2
	 */
	private short romVersion;

	/**
	 * 1个字节
	 */
	private byte isPlainText;

	/**
	 * 7个字节
	 */
	private String reserved;

	/**
	 * 2个字节
	 */
	private short crc;

	private Map<String, String> payload = new HashMap<String, String>();
	
	/**
	 * OTA分包升级的数据
	 */
	private byte[] otaBin;
	
	/**
	 * OTA分包序号
	 */
	private short otaPartIndex;
	
	/**
	 * OTA分包总长度
	 */
	private short otaPartTotalLength;
	
	/**
	 * 硬件版本号
	 */
	private short hardVersion;
	
	/**
	 * 软件版本号
	 */
	private short softVersion;
	
	/**
	 * OTA升级末尾的crc16
	 */
	private short otaCrc;
	
	/**
	 * 1:有下包
	 * 0：1包
	 */
	private byte hasNext;

	
	/**
	 * 放到mq做一个唯一值，方便update服务器返回的请求记录
	 */
	private String endRideId;
	
	public GsmMessage() {
		
	}
	
	public GsmMessage(byte[] data) {
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public short getSeqId() {
		return seqId;
	}

	public void setSeqId(short seqId) {
		this.seqId = seqId;
	}

	public short getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(short totalLength) {
		this.totalLength = totalLength;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public short getRomVersion() {
		return romVersion;
	}

	public void setRomVersion(short romVersion) {
		this.romVersion = romVersion;
	}

	public byte getIsPlainText() {
		return isPlainText;
	}

	public void setIsPlainText(byte isPlainText) {
		this.isPlainText = isPlainText;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public short getCrc() {
		return crc;
	}

	public void setCrc(short crc) {
		this.crc = crc;
	}

	public Map<String, String> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, String> payload) {
		this.payload = payload;
	}
	

	public byte[] getOtaBin() {
		return otaBin;
	}

	public void setOtaBin(byte[] otaBin) {
		this.otaBin = otaBin;
	}

	public short getOtaPartIndex() {
		return otaPartIndex;
	}

	public void setOtaPartIndex(short otaPartIndex) {
		this.otaPartIndex = otaPartIndex;
	}

	public short getOtaPartTotalLength() {
		return otaPartTotalLength;
	}

	public void setOtaPartTotalLength(short otaPartTotalLength) {
		this.otaPartTotalLength = otaPartTotalLength;
	}
	

	public short getHardVersion() {
		return hardVersion;
	}

	public void setHardVersion(short hardVersion) {
		this.hardVersion = hardVersion;
	}

	public short getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(short softVersion) {
		this.softVersion = softVersion;
	}
	

	public short getOtaCrc() {
		return otaCrc;
	}

	public void setOtaCrc(short otaCrc) {
		this.otaCrc = otaCrc;
	}

	public String getEndRideId() {
		return endRideId;
	}

	public void setEndRideId(String endRideId) {
		this.endRideId = endRideId;
	}

	public byte getHasNext() {
		return hasNext;
	}

	public void setHasNext(byte hasNext) {
		this.hasNext = hasNext;
	}

	public String toString() {
        return "GsmMessage [macAddress=" + macAddress + ", seqID=" + seqId + ", totalLength=" + totalLength + ", length=" + length
                + ", romVersion=" + romVersion + ", isPlainText=" + isPlainText + ", reserved=" + reserved + ", cRC=" + crc + ", payload= "+ payload.toString() +"]";
    }

}
