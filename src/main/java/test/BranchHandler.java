/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;

/**
 *
 * @author tamvadss
 */
public class BranchHandler  extends IloCplex.BranchCallback{
    
    
 
    protected void main() throws IloException {
          if ( getNbranches()> 0 ){  
              //set the lp relax value into the kids
              for (int childNum = 0 ;childNum<getNbranches();  childNum++) {    
                   makeBranch(childNum, getObjValue() );
              }
          }
    }
    
}
