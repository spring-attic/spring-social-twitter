package org.springframework.social.twitter.api.impl;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Created by Oyku Gencay (oyku at gencay.net) on 12.08.2014.
 *
 */
public class Coordinates extends TwitterObject{

    private String type;
    private Double longitude;
    private Double latitude;

    public Coordinates() {
    }

    public Coordinates(String type, Double longitude, Double latitude) {
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        return result;
    }

}
