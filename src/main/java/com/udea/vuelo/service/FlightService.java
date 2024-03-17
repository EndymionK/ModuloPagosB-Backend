package com.udea.vuelo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.vuelo.model.Flight;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlightService {
    //Ruta del archivo
    private final String filePath = "classpath:flights.json";

    //Método de la lógica de búsqueda de vuelos
    public List<List<Flight>> searchFlightsByDate(LocalDate startDate, LocalDate endDate) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json");

            if(inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(flight -> isDateInRange(flight.getDepartureDate(), startDate, endDate))
                                .collect(Collectors.toList()));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON ", e);
        }
    }

    private boolean isDateInRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        // Verifica si la fecha está en el rango correcto
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    public List<List<Flight>> searchFlightsByPrice(int startPrice, int endPrice) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json");

            if(inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(flight -> isPriceInRange(flight.getPrice(), startPrice, endPrice))
                                .collect(Collectors.toList()));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON ", e);
        }
    }

    private boolean isPriceInRange(int priceToCheck, int startPrice, int endPrice) {
        // Verifica si el precio está en el rango correcto
        return priceToCheck >= startPrice && priceToCheck <= endPrice;
    }

    public List<List<Flight>> searchFlightsByRoute(String origin, String destination) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json");

            if(inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(flight -> isRouteInRoutes(flight.getOrigin(),flight.getDestination(),origin, destination))
                                .collect(Collectors.toList()));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON ", e);
        }
    }

    private boolean isRouteInRoutes(String origintocheck,String destinationtocheck, String origin, String destination) {
        // Verifica si el precio está en el rango correcto
        return Objects.equals(origintocheck, origin) && Objects.equals(destinationtocheck, destination);
    }

    public List<List<Flight>> searchFlightsByAirline(String airline) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json");

            if(inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                return Arrays.asList(
                        Arrays.stream(flights)
                                .filter(flight -> isAirlineInFlights(flight.getAirline(),airline))
                                .collect(Collectors.toList()));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON ", e);
        }
    }

    private boolean isAirlineInFlights(String airlinetocheck, String airline ) {
        // Verifica si el precio está en el rango correcto
        return Objects.equals(airlinetocheck, airline);
    }

    public int searchPriceById(int id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("flights.json");

            if (inputStream != null) {
                Flight[] flights = objectMapper.readValue(inputStream, Flight[].class);
                for (Flight flight : flights) {
                    if (flight.getId() == id) {
                        return flight.getPrice();
                    }
                }
            }
            return -1; // Indica que no se encontró el vuelo con el ID dado
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON ", e);
        }
    }




}
