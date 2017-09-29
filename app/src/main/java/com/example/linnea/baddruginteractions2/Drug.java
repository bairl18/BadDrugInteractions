package com.example.linnea.baddruginteractions2;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class Drug {

    private int id;
    private String name;
    private String address;
    public Drug()
    {
    }
    public Drug(int id,String name,String address)
    {
        this.id=id;
        this.name=name;
        this.address=address;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
}
