package io.github.aquerr.rentacar.domain.reservation.model;

public enum ReservationStatus
{
    DRAFT("DRAFT"),
    PENDING_PAYMENT("PENDING_PAYMENT"),
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),
    CAR_DELIVERED("CAR_DELIVERED"),
    COMPLETED("COMPLETED");

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
