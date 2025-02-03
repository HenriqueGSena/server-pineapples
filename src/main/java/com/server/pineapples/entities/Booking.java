package com.server.pineapples.entities;

public class Booking {

    private String reserva;
    private String valorPago;

    public Booking() {
    }

    public Booking(String reserva, String valorPago) {
        this.reserva = reserva;
        this.valorPago = valorPago;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }
}
