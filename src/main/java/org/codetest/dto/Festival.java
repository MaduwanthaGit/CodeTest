package org.example.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Festival {
    public String name;
    public ArrayList<Band> bands;
}
