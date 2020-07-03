package dto;

import java.io.Serializable;

public class DataDto implements Serializable {
	// 이름
	private String name;
	// 입력날짜
	private String wdate;
	// 소비/지출
	private String iokind;
	// 액수
	private int amount;
	// 입력한 내용
	private String content;
	
	public DataDto() {
		
	}

	public DataDto(String name, String wdate, String iokind, int amount, String content) {
		super();
		this.name = name;
		this.wdate = wdate;
		this.iokind = iokind;
		this.amount = amount;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getIokind() {
		return iokind;
	}

	public void setIokind(String iokind) {
		this.iokind = iokind;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "이름 = " + name + ", 등록일= " + wdate + "\n" + 
			   "수입/지출= " + iokind + " 금액= " + amount + "\n" + "메모= "
				+ content;
	}
	
	
}
