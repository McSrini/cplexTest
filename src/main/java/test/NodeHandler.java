/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import java.io.File;

/**
 *
 * @author tamvadss
 */
public class NodeHandler  extends IloCplex.NodeCallback{
    
    public int numNodes = 0 ;
    public int numBestLpRelNodes = 0;
 
    protected void main() throws IloException {
        int bestIndex = 0;
        double bestLPRval =  Double.MAX_VALUE;
        long activeCount = getNremainingNodes64();
        for  ( int index = 0;  activeCount > index; index ++) {
            if (    getObjValue(index)  <bestLPRval) {
                bestLPRval=  getObjValue(index) ;
                bestIndex =    index;
            }
        }
        
        numNodes ++;
        if (0==bestIndex) numBestLpRelNodes++;
        selectNode (bestIndex) ;
        if (numNodes%1000 ==0) System.out.println("Nodehandeler : Num noedes "+  numNodes  + " and of these best lp relax were " + numBestLpRelNodes);
        if (isHaltFilePresent()) abort();
    }
    
    private static boolean isHaltFilePresent (){
        File file = new File("F:\\temporary files here\\haltfile.txt");
        return file.exists();
    }
}
