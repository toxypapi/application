package com.juniorkabore.filleul;

import com.google.gson.annotations.SerializedName;

/**
 * Created by juniorkabore on 06/09/15.
 */
public class LogOutData {




    @SerializedName("errNum")

    private int errNum;
    @SerializedName("errFlag")
    private int errFlag;
    @SerializedName("errMsg")
    private String errMsg;




    public int getErrNum() {
        return errNum;
    }
    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }
    public int getErrFlag() {
        return errFlag;
    }
    public void setErrFlag(int errFlag) {
        this.errFlag = errFlag;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }











}
