package javaassessment2;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class dataProcessor {
    
    private static final double pi = 3.14;
    private static final double psi = 6.39485;
    private static final double zeta = 3.2;
    
    public static void main(String[] args){
        
        String[] inputData;
        /**
         * Hard coded the filename, but it can easily be changed to user prompt 
         * or command line parameter.
         */
        String fileName = "inputData.txt"; 
        
        try {
            File file = new File(fileName);
            Scanner in = new Scanner(file);
            
            if (!in.hasNextInt()){
                System.out.println("Error: No data group count found.");
                System.out.println("Unable to continue, exiting.");
                System.exit(0);
            }
            int groupCount = in.nextInt();
            in.nextLine();
            
            /**
             * Each String will be a different data group. These Strings will be
             * parsed by a Scanner later
             */
            inputData = new String[groupCount];
            int currentGroup = 0;
            
            /**
             * Each Line is placed in a String for the data group. The * is 
             * removed, and each line added to an existing group is separated by a comma.
             **/
            while(in.hasNextLine() && currentGroup < groupCount){
                
                String temp = in.nextLine();
                if (temp.charAt(0) != '*' && !(currentGroup < 0)){
                    inputData[currentGroup-1] += " ," + temp;
                }
                else {
                    inputData[currentGroup] = temp.substring(1, temp.length());
                    currentGroup++;
                }
                
            }
            in.close();
            
            DecimalFormat df = new DecimalFormat("#.000");
            for (int i = 0; i < groupCount; i++){
                
//                System.out.println("^  " + inputData[i]);
                
                Scanner data = new Scanner(inputData[i]);
                int recCount = data.nextInt();
//                System.out.println(recCount);
                int parCount = data.nextInt();
//                System.out.println(parCount);
                data.close();
                //Check if there is the correct number of records.
                int recCheck = StringUtils.countMatches(inputData[i],",");
                if (recCheck != recCount){
                    inputData[i] = "Error: Record Count incorrect.";
                    System.out.println("Group " + i + " " + inputData[i]);
                }
                else{
                    ArrayList<String> dataRecords;
                    dataRecords = new ArrayList<>(Arrays.asList(inputData[i].split(",")));
                    dataRecords.remove(0); 
                    int recRead = 0;
                    //Check parameter count
                    for (String record : dataRecords){
//                      System.out.print(i + ": ");
//                      System.out.println(record);
                        String[] parameter = record.split(" ");
//                        System.out.println("Group " +i+" length: "+parameter.length);
                        if (parameter.length != parCount){
                            inputData[i] = "Error: Paramater Count is incorrect";
                            System.out.println("Group " + i + " " + inputData[i]);
                            break;
                        }
                        recRead++;
                    }
                    
                    boolean firstRead = true; //To only print the group # the first time;
                    boolean firstRec = false;
                    //Do calculation stuff here:
                    List<Double> resultList = new ArrayList();
                    for (String record : dataRecords){
                        Scanner recordRead = new Scanner(record);
                        if (inputData[i].equals("Error: Paramater Count is incorrect")){
                            break;
                        }
                        if (firstRead){
                            System.out.println("Group" + i + ":");
                            resultList.clear();
                            firstRec = false;
                        }
                        String[] parameter = record.split("");
//                        if (!firstRec){
//                            firstRec = true;
//                        }
                        if (parCount == 2){
                            int t1 = recordRead.nextInt();
                            int t2 = recordRead.nextInt();
                            //AVG of parameters:
                            double parAvg = ((double)(t1) + (double)(t2))/2;
                            System.out.println("AVG= " + df.format(parAvg));
                            //FORM of each:
                            resultList.add(Calc1(t1, t2));
                            recRead++;
                        }
                        else if (parCount == 3){
                            //Assign the 3 variables
                            int t1, t2, t3;
                            t1 = t2 = t3 = 0;
                            int tempCount = 0;
                            while(recordRead.hasNext()){
                                if (tempCount == 0){
                                    //Assign t1
                                    if (recordRead.hasNextInt()){
                                        t1 = recordRead.nextInt();
                                    } else if (recordRead.hasNext()){
                                        String letter = recordRead.next().toLowerCase();
                                        switch(letter){
                                            case "a":
                                                t1 = 3;
                                                break;
                                            case "b":
                                                t1 = 4;
                                                break;
                                            case "c":
                                                t1 = 6;
                                                break;
                                            default:
                                                t3 = 3;
                                        }
                                    }
                                    tempCount++;
                                }
                                else if (tempCount == 1){
                                    //Assign t2
                                    if (recordRead.hasNextInt()){
                                        t2 = recordRead.nextInt();
                                    } else if (recordRead.hasNext()){
                                        String letter = recordRead.next().toLowerCase();
                                        switch(letter){
                                            case "a":
                                                t2 = 3;
                                                break;
                                            case "b":
                                                t2 = 4;
                                                break;
                                            case "c":
                                                t2 = 6;
                                                break;
                                            default:
                                                t3 = 3;
                                        }
                                    }
                                    tempCount++;
                                }
                                else if (tempCount == 2){
                                    //Assign t3
                                    if (recordRead.hasNextInt()){
                                        t3 = recordRead.nextInt();
                                    } else if (recordRead.hasNext()){
                                        String letter = recordRead.next().toLowerCase();
                                        switch(letter){
                                            case "a":
                                                t3 = 3;
                                                break;
                                            case "b":
                                                t3 = 4;
                                                break;
                                            case "c":
                                                t3 = 6;
                                                break;
                                            default:
                                                t3 = 3;
                                        }
                                    }
                                    tempCount++;
                                }
                            }
                            
                            double avgPar = (double)(t1+t2+t3)/3;
                            System.out.println("AVG 3= " + df.format(avgPar));
                            resultList.add(Calc2(t1, t2, t3));
                            recRead++;
                        }
                        firstRead = false;
                    }
                    
                    if (!resultList.isEmpty()){
                        double sum = 0.0;
                        for (double result : resultList){
                            sum += result;
                        }
                        if (parCount == 2){
                            System.out.println("FORM Avg= " 
                                    + df.format(sum/resultList.size()));
                        }
                        else if (parCount == 3){
                            System.out.println("FORM 3 Avg= " 
                                    + df.format(sum/resultList.size()));
                        }
                        System.out.println("\nRecord Reads: " + recRead +"\n");
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " not found.");
        }
    }
    
    /**
     * This is the calculation for 2 parameters.
     * @param x - First number in each data record
     * @param y - Second number in each Data record
     * @return - Calculated result from formula as double.
     */
    static double Calc1(int x, int y){
        DecimalFormat df = new DecimalFormat("#.000");
        double result = pi*((double)(x)) + psi*(Math.pow((double)(y),3)+1.34);
        System.out.println("FORM= " + df.format(result));
        return result;
    }
    
    /**
     * Calculation for 3 parameters.
     */
    static double Calc2(int x, int y, int z){
        DecimalFormat df = new DecimalFormat("#.000");
        double result = pi*((double)(x)) + psi*(Math.pow((double)(y),3)+1.34)
                + Math.pow(zeta,z)/((double)(y));
        System.out.println("FORM 3= " + df.format(result));
        return result;
    }
}
