/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api;

/**
 * Represents <code>geo_code</code> parameter used for advanced search
 */
public class GeoCode {

	private double latitude;
	private double longitude;
	private int radius;
	private Unit unit;

	/**
	 * Creates GeoCode object with the default unit of measure is Unit.KILOMETER
	 *
	 * @param latitude the location's latitude
	 * @param longitude the location's longitude
	 * @param radius the radius of the area to cover by this location
	 */
	public GeoCode(double latitude, double longitude, int radius) {
		this(latitude, longitude, radius, Unit.KILOMETER);
	}

	public GeoCode(double latitude, double longitude, int radius, Unit unit) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.unit = unit;
	}

	@Override
	public String toString() {
		return new StringBuilder()
						.append(this.latitude).append(",")
						.append(this.longitude).append(",")
						.append(this.radius)
						.append(this.unit.toString())
						.toString();
	}

	public enum Unit {
		KILOMETER("km"), MILE("mi");

		private String unit;

		private Unit(String unit) {
			this.unit = unit;
		}

		@Override
		public String toString() {
			return this.unit;
		}
	}

}
