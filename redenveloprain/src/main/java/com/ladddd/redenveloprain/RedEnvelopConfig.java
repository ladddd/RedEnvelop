package com.ladddd.redenveloprain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ladddd on 2017/2/20.
 */
public class RedEnvelopConfig implements Parcelable {
    public int totalCount;
    public int durationSeconds;
    public float money;
    public String displayResult;
    public List<RedEnvelopDetail> other;

    public RedEnvelopConfig() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalCount);
        dest.writeInt(this.durationSeconds);
        dest.writeFloat(this.money);
        dest.writeString(this.displayResult);
        dest.writeTypedList(this.other);
    }

    protected RedEnvelopConfig(Parcel in) {
        this.totalCount = in.readInt();
        this.durationSeconds = in.readInt();
        this.money = in.readFloat();
        this.displayResult = in.readString();
        this.other = in.createTypedArrayList(RedEnvelopDetail.CREATOR);
    }

    public static final Creator<RedEnvelopConfig> CREATOR = new Creator<RedEnvelopConfig>() {
        @Override
        public RedEnvelopConfig createFromParcel(Parcel source) {
            return new RedEnvelopConfig(source);
        }

        @Override
        public RedEnvelopConfig[] newArray(int size) {
            return new RedEnvelopConfig[size];
        }
    };
}
