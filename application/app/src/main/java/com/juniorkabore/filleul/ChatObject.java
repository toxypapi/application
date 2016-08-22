package com.juniorkabore.filleul;



/**
 * Created by Dell on 3/19/2015.
 */
public class ChatObject {

    String message;
   // ArrayList<String> message;
    String type;
    private String strFBId;
    private String strTime;

    private String strSenderId;

    private String strReceiverId;

    public ChatObject() {
    }

    public ChatObject(String message,String type) {
        this.message = message;
        this.type   = type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getStrFBId() {
        return strFBId;
    }

    public void setStrFBId(String strFBId) {
        this.strFBId = strFBId;
    }
    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrSenderId() {
        return strSenderId;
    }

    public void setStrSenderId(String strSenderId) {
        this.strSenderId = strSenderId;
    }

    public String getStrReceiverId() {
        return strReceiverId;
    }

    public void setStrReceiverId(String strReceiverId) {
        this.strReceiverId = strReceiverId;
    }


}
