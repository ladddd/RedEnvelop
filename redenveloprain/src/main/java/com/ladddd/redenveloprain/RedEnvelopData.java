package com.ladddd.redenveloprain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ladddd on 2017/2/20.
 */
public class RedEnvelopData implements Parcelable {
    //活动状态1未开始；2进行中；3：下线；4：已结束
    public static final int CSTATE_UNAVAILABLE = 1;
    public static final int CSTATE_AVAILABLE = 2;
    public static final int CSTATE_CLOSED = 3;
    public static final int CSTATE_TIMEOVER = 4;
    //提交状态  0 未提交；1已提交
    public static final int USTATE_AVAILABLE = 0;
    public static final int USTATE_SUBMITTED = 1;

    public String campaignId;
    public String campaignType;
    public String campaignMessage;
    public int campaignStatus = -1;
    public int brokerSubmitStatus = -1;
    public String startTime;
    public String serverTime;
    public RedEnvelopConfig data;

    public RedEnvelopData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.campaignId);
        dest.writeString(this.campaignType);
        dest.writeString(this.campaignMessage);
        dest.writeInt(this.campaignStatus);
        dest.writeInt(this.brokerSubmitStatus);
        dest.writeString(this.startTime);
        dest.writeString(this.serverTime);
        dest.writeParcelable(this.data, flags);
    }

    protected RedEnvelopData(Parcel in) {
        this.campaignId = in.readString();
        this.campaignType = in.readString();
        this.campaignMessage = in.readString();
        this.campaignStatus = in.readInt();
        this.brokerSubmitStatus = in.readInt();
        this.startTime = in.readString();
        this.serverTime = in.readString();
        this.data = in.readParcelable(RedEnvelopConfig.class.getClassLoader());
    }

    public static final Creator<RedEnvelopData> CREATOR = new Creator<RedEnvelopData>() {
        @Override
        public RedEnvelopData createFromParcel(Parcel source) {
            return new RedEnvelopData(source);
        }

        @Override
        public RedEnvelopData[] newArray(int size) {
            return new RedEnvelopData[size];
        }
    };
}
