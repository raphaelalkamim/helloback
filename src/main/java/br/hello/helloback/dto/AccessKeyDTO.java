package br.hello.helloback.dto;

public class AccessKeyDTO {
    String accessCode;
    UserDTO user;
    UnitDTO unit;

    public String getAccessCode() {
        return accessCode;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }



}
