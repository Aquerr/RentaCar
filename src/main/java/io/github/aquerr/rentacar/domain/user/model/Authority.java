package io.github.aquerr.rentacar.domain.user.model;

public enum Authority
{
    VIEW_CAR_LOCATION("VIEW_CAR_LOCATION"),
    EDIT_CARS("EDIT_CARS");

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
