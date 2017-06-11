package com.jaime.airports.interfaces;

import com.jaime.airports.pojo.Airport;

/**
 * Created by jaime on 04/06/2017.
 */

public interface AddAirportPresenter {
    boolean isThisCodeTaken(String code);
    void requestToAddAirport(Airport airport);
    void requestToUpdateAirport(Airport airport);
}
