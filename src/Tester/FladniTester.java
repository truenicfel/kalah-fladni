/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tester;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class FladniTester implements Runnable {

    public int result;
    
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FladniTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hello I am a Thread!");
        
    }
    
}
