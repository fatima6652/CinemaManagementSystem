/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Model;

/**
 *
 * @author fatima
 */
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private String movieName;
    private String show;
    private int numOfSeats;
    private int MovieId;
    private int Showid;
    private double unitPrice;
    
    public double getPrice(){
        return numOfSeats* unitPrice;
    }
    
    
    
 

}
