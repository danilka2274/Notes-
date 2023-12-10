package ru.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int imageIndex;
    private String noteName;
    private String noteName2;


    public Note(int imageIndex, String noteName, String noteName2){
        this.imageIndex = imageIndex;
        this.noteName = noteName;
        this.noteName2 = noteName2;
    }

    protected Note(Parcel in) {
        imageIndex = in.readInt();
        noteName = in.readString();
        noteName2 = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageIndex);
        dest.writeString(noteName);
        dest.writeString(noteName2);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public String getNoteName2() {
        return noteName2;
    }

}
