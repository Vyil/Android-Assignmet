package com.example.djim.marsrover.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Djim on 13-3-2018.
 */

public class RoverCollection implements Serializable {

    private String id;
    private String cameraName;
    private String imageURL;

    public RoverCollection(String id, String cameraName, String imageURL){
        this.id=id;
        this.cameraName=cameraName;
        this.imageURL=imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
