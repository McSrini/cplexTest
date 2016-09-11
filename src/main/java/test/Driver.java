package test;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.Status;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.log4j.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
 

public class Driver {
    
    
    private static Logger logger=Logger.getLogger(Driver.class);
     

    public static void main(String[] args) throws Exception {
        
        File file = new File("F:\\temporary files here\\cplexTest.log"   ); 
        PrintStream printStream = new PrintStream(new FileOutputStream(file)); 
        //System.setOut(printStream);
        
        
        // TODO Auto-generated method stub
        
         Instant startTime =   Instant.now();
         
         System.out.println("Solving model at " + LocalDateTime.now()) ;
       
       
        
        IloCplex cplex= new IloCplex();   
        
        cplex.importModel("F:\\temporary files here\\msc98-ip.mps");
        
          System.out.println("Test cplex Status before solve" +  cplex.getStatus());
          
        // BranchHandler branchHandler = new BranchHandler(          );
        NodeHandler nh = new NodeHandler();
        
       // cplex.use(branchHandler);
        cplex.use(nh);
        
        cplex.setParam(IloCplex.Param.MIP.Strategy.File, 0 );
          
        // cplex.setParam(IloCplex.Param.TimeLimit, 180); 
        cplex.solve();
        
       
       
         
        //System.out.println("Num branches "+ branchHandler.numBranches);
        System.out.println("Num noedes "+  nh.numNodes  + " and of these best lp relax were " +nh.numBestLpRelNodes);
        
         
         System.out.println("Solving model COMPLETED in minutes " + Duration.between(Instant.now(), startTime).toMillis()/60000.0) ;
         
        if (cplex.getStatus().equals(Status.Optimal)|| cplex.getStatus().equals(Status.Feasible))  System.out.println("Test cplex Solution" +  cplex.getObjValue());

    }


}
