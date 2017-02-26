package com.decode.dtumessenger.Models;

/**
 * Created by aanyajindal on 26/02/17.
 */

public class Contact {

    String name, phpto_uri;

    public Contact() {
    }

    public Contact(String name, String phpto_uri) {
        this.name = name;
        this.phpto_uri = phpto_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhpto_uri() {
        return phpto_uri;
    }

    public void setPhpto_uri(String phpto_uri) {
        this.phpto_uri = phpto_uri;
    }
}
