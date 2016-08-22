package com.juniorkabore.filleul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by juniorkabore on 07/09/15.
 */
public class ChatMessageData {




    @SerializedName("chat")
    private List<ChatMessageList> listChat;



    @SerializedName("errMsg")
    private String errMsg;

    @SerializedName("errFlag")
    private int errFlag;

    @SerializedName("errNum")
    private int errNum;

    public List<ChatMessageList> getListChat() {
        return listChat;
    }

    public void setListChat(List<ChatMessageList> listChat) {
        this.listChat = listChat;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(int errFlag) {
        this.errFlag = errFlag;
    }

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }





}
