/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Threads{
    private int A;
    private int B;

    /** Constructor de la clase CountThread*/ 
    public CountThread(int A, int b){
        this.A = A;
        this.B = B;
    }

    @Override
    public void run(){
        for(int i = A, i <= B; i+){
            System.out.println("numero : ")
        }
    }
}
