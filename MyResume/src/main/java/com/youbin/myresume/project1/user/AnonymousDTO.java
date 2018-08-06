package com.youbin.myresume.project1.user;

public class AnonymousDTO {
	private int chatID;
	private String chatName;
	private String chatContent;
	private String chatTime;
	
	public int getChatID() {
		return chatID;
	}
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	public String getChatName() {
		return chatName.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public String getChatContent() {
		return chatContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
		
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getChatTime() {
		int time = Integer.parseInt(chatTime.substring(11, 13));
		String timeType = "오전";
		if(time > 12) {
			timeType = "오후";
			time -= 12;
		}else if(time == 12) {
			timeType = "오후";
		}
		return chatTime.substring(0, 11) + " " + timeType + " " + time + ":" + chatTime.substring(14, 16);
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
}
