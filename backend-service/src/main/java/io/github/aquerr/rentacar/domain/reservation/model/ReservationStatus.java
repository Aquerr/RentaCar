package io.github.aquerr.rentacar.domain.reservation.model;

public enum ReservationStatus
{
    DRAFT("DRAFT"),
    PENDING_PAYMENT("PENDING_PAYMENT"),
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),
    VEHICLE_DELIVERED("VEHICLE_DELIVERED"),
    COMPLETED("COMPLETED"),
    VEHICLE_NOT_AVAILABLE("VEHICLE_NOT_AVAILABLE"),
    CANCELLED("CANCELLED");

    private final String status;

    ReservationStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
}
