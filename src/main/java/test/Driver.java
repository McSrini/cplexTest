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
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

/**
 * 
 * rd-rplusc-21.mps  solves in 1 hour 
 * msc98-ip solves in 150 minutes
 * glass4 solves in 106 minutes
 * 
 * @author tamvadss
 */


public class Driver {
    
    
    private static Logger logger=Logger.getLogger(Driver.class);
     

    public static void main(String[] args) throws Exception {
        
       // File file = new File("F:\\temporary files here\\cplexTest.log"   ); 
        //PrintStream printStream = new PrintStream(new FileOutputStream(file)); 
        //System.setOut(printStream);
        
          logger.setLevel(Level.DEBUG);
        PatternLayout layout = new PatternLayout("%5p  %d  %F  %L  %m%n");     
        try {
            
            logger.addAppender(new  RollingFileAppender(layout,"F:\\temporary files here\\cplexTest.log"));
            logger.setAdditivity(false);
        } catch (Exception ex) {
            ///
        }     
        
        
        // TODO Auto-generated method stub
        
         Instant startTime =   Instant.now();
         
         logger.info("Solving model at " + LocalDateTime.now()) ;
       
       
        
        IloCplex cplex= new IloCplex();   
        
        //cplex.importModel("F:\\temporary files here\\a1c1s1.mps");
        String filename = "F:\\temporary files here\\glass4.mps";
        cplex.importModel( filename );
        
          logger.info(" cplex file name " +  filename );
          
         BranchHandler branchHandler = new BranchHandler(          );
        NodeHandler nh = new NodeHandler();
        
        cplex.use(branchHandler);
        cplex.use(nh);
        
        
        //cplex.setParam(IloCplex.Param.MIP.Strategy.Backtrack, 0.0 );

        cplex.setParam(IloCplex.Param.MIP.Strategy.File, 0 );
          
          cplex.setParam(IloCplex.Param.TimeLimit, 120); 
          while (!isHaltFilePresent () && !cplex.getStatus().equals( IloCplex.Status.Optimal)) {
              int bef =  nh.numNodes;
        cplex.solve();
        int aft =  nh.numNodes;
         logger.info("LP rel is "+ branchHandler.objVal + " best soln is "+ cplex.getObjValue());
         logger.info("# nodes is "+ (aft-bef));
          }
        
       
       
         
        logger.info("Num branches "+ branchHandler.numbrances);
        //logger.info("Num noedes "+  nh.numNodes  + " and of these best lp relax were " +nh.numBestLpRelNodes);
        
         
         logger.info("Solving model COMPLETED in minutes " + Duration.between(Instant.now(), startTime).toMillis()/60000.0) ;
         
        if (cplex.getStatus().equals(Status.Optimal)|| cplex.getStatus().equals(Status.Feasible))  
            logger.info("Test cplex Solution" +  cplex.getObjValue());

    }

    private static boolean isHaltFilePresent (){
        File file = new File("F:\\temporary files here\\haltfile.txt");
         
        return file.exists();
    }

}
