/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import java.util.List;

import org.springframework.social.twitter.api.GeoOperations;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.PlacePrototype;
import org.springframework.social.twitter.api.PlaceType;
import org.springframework.social.twitter.api.SimilarPlaces;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GeoTemplate extends AbstractTwitterOperations implements GeoOperations {

    private final RestTemplate restTemplate;

    public GeoTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public Place getPlace(String placeId) {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.GEO_ID)
                        .withArgument("place_id", placeId)
                        .build(),
                Place.class);
    }

    @Override
    public List<Place> reverseGeoCode(double latitude, double longitude) {
        return reverseGeoCode(latitude, longitude, null, null);
    }

    @Override
    public List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy) {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.GEO_REVERSE_GEOCODE)
                        .withArgument(buildGeoParameters(latitude, longitude, granularity, accuracy, null))
                        .build(),
                PlacesList.class
                ).getList();
    }

    @Override
    public List<Place> search(double latitude, double longitude) {
        return search(latitude, longitude, null, null, null);
    }

    @Override
    public List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.GEO_SEARCH)
                        .withArgument(buildGeoParameters(latitude, longitude, granularity, accuracy, query))
                        .build(),
                PlacesList.class
                ).getList();
    }

    @Override
    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name) {
        return findSimilarPlaces(latitude, longitude, name, null, null);
    }

    @Override
    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name, String streetAddress, String containedWithin) {
        requireUserAuthorization();

        SimilarPlacesResponse response = restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.GEO_SIMILAR_PLACES)
                        .withArgument(buildPlaceParameters(latitude, longitude, name, streetAddress, containedWithin))
                        .build(),
                SimilarPlacesResponse.class);

        return new SimilarPlaces(
                response.getPlaces(),
                new PlacePrototype(response.getToken(), latitude, longitude, name, streetAddress, containedWithin));
    }

    @Override
    public Place createPlace(PlacePrototype placePrototype) {
        requireUserAuthorization();

        MultiValueMap<String, String> bodyData =
                buildPlaceParameters(placePrototype.getLatitude(), placePrototype.getLongitude(), placePrototype.getName(),
                        placePrototype.getStreetAddress(), placePrototype.getContainedWithin());
        bodyData.set("token", placePrototype.getCreateToken());

        return restTemplate.postForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.GEO_PLACE)
                        .build(),
                bodyData,
                Place.class);
    }

    // private helpers

    private MultiValueMap<String, String> buildGeoParameters(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set("lat", String.valueOf(latitude));
        parameters.set("long", String.valueOf(longitude));
        if (granularity != null) {
            parameters.set("granularity", granularity.equals(PlaceType.POINT_OF_INTEREST) ? "poi" : granularity.toString().toLowerCase());
        }
        if (accuracy != null) {
            parameters.set("accuracy", accuracy);
        }
        if (query != null) {
            parameters.set("query", query);
        }
        return parameters;
    }

    private MultiValueMap<String, String> buildPlaceParameters(double latitude, double longitude, String name, String streetAddress,
            String containedWithin) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set("lat", String.valueOf(latitude));
        parameters.set("long", String.valueOf(longitude));
        parameters.set("name", name);
        if (streetAddress != null) {
            parameters.set("attribute:street_address", streetAddress);
        }
        if (containedWithin != null) {
            parameters.set("contained_within", containedWithin);
        }
        return parameters;
    }

}
