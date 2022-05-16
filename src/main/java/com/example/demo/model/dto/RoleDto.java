package com.example.demo.model.dto;

public class RoleDto {

    private Long id;
    private String roleName;

    public RoleDto(){}

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
