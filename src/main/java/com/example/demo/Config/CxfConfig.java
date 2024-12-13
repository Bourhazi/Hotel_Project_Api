package com.example.demo.Config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import com.example.demo.Controllers.ReservationSoapContr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final ReservationSoapContr reservationSoapController;

    public CxfConfig(Bus bus, ReservationSoapContr reservationSoapController) {
        this.bus = bus;
        this.reservationSoapController = reservationSoapController;
    }

    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoapController);
        endpoint.publish("/ws");
        return endpoint;
    }

}
