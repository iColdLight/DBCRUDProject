package net.coldlight.dbcrudapp.model;

import java.util.Objects;

public class Speciality {
    private Long id;
    private String specialityName;
    private Status status;

    public Speciality() {
    }

    public Speciality(String specialityName) {
        this.specialityName = specialityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speciality that = (Speciality) o;
        return Objects.equals(id, that.id) && Objects.equals(specialityName, that.specialityName) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specialityName, status);
    }
}
