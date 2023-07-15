package io.github.aquerr.rentacar.domain.user.model;

public enum RentacarAuthority
{
    VIEW_CAR_LOCATION("VIEW_CAR_LOCATION"),
    EDIT_CARS("EDIT_CARS"),;

    private final String authority;

    RentacarAuthority(String authority)
    {
        this.authority = authority;
    }

    public String getAuthority()
    {
        return authority;
    }
}
