package com.emilie.ClimbingWebApp.domain;

import java.util.Objects;


public class Login {
    private String email;
    private String password;
    private String name;

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login=(Login) o;
        return Objects.equals( email, login.email ) &&
                Objects.equals( password, login.password ) &&
                Objects.equals( name, login.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( email, password, name );
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
