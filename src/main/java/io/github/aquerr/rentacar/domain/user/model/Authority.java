package io.github.aquerr.rentacar.domain.user.model;

public enum Authority
{
    VIEW_CAR_LOCATION("VIEW_CAR_LOCATION"),
    VIEW_ADMIN_PANEL("VIEW_ADMIN_PANEL"),
    ADD_VEHICLE("ADD_VEHICLE");

    private final String authority;

    Authority(String authority)
    {
        this.authority = authority;
    }

    public String getAuthority()
    {
        return authority;
    }
}
