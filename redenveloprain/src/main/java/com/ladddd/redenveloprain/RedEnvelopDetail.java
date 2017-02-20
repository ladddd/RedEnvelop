package com.ladddd.redenveloprain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenweida on 16-12-11.
 */
public class RedEnvelopDetail implements Parcelable {
    public String title;
    public int count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.count);
    }

    public RedEnvelopDetail() {
    }

    protected RedEnvelopDetail(Parcel in) {
        this.title = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<RedEnvelopDetail> CREATOR = new Creator<RedEnvelopDetail>() {
        @Override
        public RedEnvelopDetail createFromParcel(Parcel source) {
            return new RedEnvelopDetail(source);
        }

        @Override
        public RedEnvelopDetail[] newArray(int size) {
            return new RedEnvelopDetail[size];
        }
    };
}
