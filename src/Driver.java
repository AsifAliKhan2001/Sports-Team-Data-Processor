
//--------------------------------------------------------------------
// Assignment 2
// Part: 1 2 3
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
// --------------------------------------------------------------------
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

/**
 * The Driver class handles processing and viewing CSV files related to sports teams.
 * provides methods to process input files, write data to output files, and view data stored in binary Files
 */
public class Driver {
	/**
     * Processes the CSV files mentioned in "part1 input file names.txt".
     * It reads the CSV files, check if its syntaxically correct, and writes them to their output files based on the sport type. 
     * syntaxically incorrect are written to the "syntax_error_file.txt".
     */
	public static void do_part1() {
		  BufferedReader br = null; //declare BufferedReader br to read from filePath which is part 1 and is expected to conain other CSV files to be processed
	      String filePath = "part1 input file names.txt";
	        try {
		            br = new BufferedReader(new FileReader(filePath));//opens file using bufferedReader and read first like which is the 3 of csv files
		            int numFiles = Integer.parseInt(br.readLine());//parse that into an integer “numFiles"

		            //Create PrintWriter objects to write the processed data to the respective CSV files
		            //these objects used to write data to csv files and syntax error file.
		            PrintWriter hockeyPrintWriter = new PrintWriter(new FileWriter("Hokey.csv"));
		            PrintWriter footballPrintWriter = new PrintWriter(new FileWriter("Football.csv"));
		            PrintWriter basketballPrintWriter = new PrintWriter(new FileWriter("Basketball.csv"));
		            PrintWriter syntaxErrorWriter = new PrintWriter(new FileWriter("syntax_error_file.txt"));
		            
		            // Loop through each input file and process them one by one
		            //reading the names of csv files from input file and calling the processing method to process each CSV file
		            for (int i = 0; i < numFiles; i++) {
		            	// Read the name of the current CSV file to be processed from the input file
		            	String csvFileName = br.readLine().trim();//read part 1 input file
		            	// Call the processing method to process the CSV file and write data to the output files
		            	processing(csvFileName, hockeyPrintWriter, footballPrintWriter, basketballPrintWriter, syntaxErrorWriter);
		            }
			            //close all printwriter objects to flush
			            hockeyPrintWriter.close();
			            footballPrintWriter.close();
			            basketballPrintWriter.close();
			            syntaxErrorWriter.close();
		        	} 
		        	catch (FileNotFoundException e) {
		        		System.out.println("Error: " + filePath + " not found.");
		        	}
		        	catch (IOException e) {
		        		e.printStackTrace();
		        	}
	        try {
				br.close();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			    }
	/**
	 * Process the CSV file, validate each record, and write the valid records to appropriate output files.
	 * @param processing
	 * @param hockeyWriter PrintWriter  to write records for hockey.
	 * @param footballWriter PrintWriter  to write records for football
	 * @param basketballWriter PrintWriter  to write records for basketball 
	 * @param syntaxErrorWriter PrintWriter  to write syntax error details to the syntax error file.
	 */
	//this is where checking for syntax error occurs. it catches an error if it doesnt meet the isValidRecord. and writes to syntax error txt
	public static void processing(String csvFileName, PrintWriter hockeyWriter, PrintWriter footballWriter,
            PrintWriter basketballWriter, PrintWriter syntaxErrorWriter)  {
		
        BufferedReader csvReader = null; //to read CSV files
        String basePath = ""; //To locate csv file later
        try {
            csvReader = new BufferedReader(new FileReader(basePath + csvFileName));//initializing reader to read from csv file b
            																		//basically reading csv file from part 1
            String line; //store current line read from csv file
            //for each csv file in part1 file your read until null
            while ((line = csvReader.readLine()) != null) { //Read each line from the CSV file until the end (null is returned)
                try {
                    if (isValidRecord(line)) {//check if currentline is a valid record by calling isValidmethod
                    	String[] fields = line.split(","); // Split the CSV line directly
                        String sportType = fields[1].trim(); // Sport type is at index 1     
                        writeRecordToCSV(line, sportType, hockeyWriter, footballWriter, basketballWriter);// Write the current line to the appropriate output file based on its sport type
                    }
                }
                //try --> if validrecord is wrong then goes to catch because doesnt meet because validation not successful
                //catches exceptions and write error details to syntax error file using writeSyntaxErrorRecord method   
                catch (MissingFieldException e) {
                    writeSyntaxErrorRecord(line, csvFileName, e.getMessage(), syntaxErrorWriter);
                } 
                catch (TooManyFieldsException e) {
                    writeSyntaxErrorRecord(line, csvFileName, e.getMessage(), syntaxErrorWriter);
                } 
                catch (TooFewFieldsException e) {
                    writeSyntaxErrorRecord(line, csvFileName, e.getMessage(), syntaxErrorWriter);
                } 
                catch (UnknownSportException e) {
                    writeSyntaxErrorRecord(line, csvFileName, e.getMessage(), syntaxErrorWriter);
                }  	
            }
            	//System.out.println(); // Add a blank line between files
		} 
		    catch (FileNotFoundException e) {
		    	// Handle the exception if the CSV file is not found
		        System.out.println("Error: " + csvFileName + " not found!");
		     }
        	catch (IOException e) {
        		// Handle the general IOException
		        e.printStackTrace();
		        } 
		        try {
					csvReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	/**
	 * Validate CSV record is valid or not.
	 * @param record    The CSV record to be validated.
	 */
	//To catch syntax error
	public static boolean isValidRecord(String record) throws TooManyFieldsException, TooFewFieldsException,
    MissingFieldException, UnknownSportException {
		
		String[] fields = record.split(","); //split csv record 
	    int expectedNoFields = 5; //expected # of fields
	    
	    if (fields.length > expectedNoFields) {
	    	throw new TooManyFieldsException("too much field");	
	    }
	    if (fields.length < expectedNoFields) {
	    	throw new TooFewFieldsException("not enough field");	
	    }

			//extract individual fields from record    
			String teamName = fields[0].trim();
			String sportType = fields[1].trim();
			String year = fields[2].trim();
			String teamRecord = fields[3].trim();
			String championship = fields[4].trim();

			    // Generate the error message if there are missing fields
			    if (!isValidSport(sportType)) {
			        throw new UnknownSportException("Unknown sport type");
			    }
			    //check if any of field are missing(empty or null)
				if(teamName.equals("")) {
					throw new MissingFieldException (" missing teamName field");
				}
				if(sportType.equals("")) {
					throw new MissingFieldException (" missing sportType field");
				}
				if(year.equals("")) {
					throw new MissingFieldException (" missing year field");
				}
				if(teamRecord.equals("")) {
					throw new MissingFieldException (" missing teamRecord field");
				}
				if(championship.equals("")) {
					throw new MissingFieldException (" missing championship field");
				}
			   return true;
			}
	/**
	 * Check whether the given sport type is valid.
	 * @return True if the sport type is valid, otherwise false.
	 */
	public static boolean isValidSport(String sportType) {
	    return sportType.equalsIgnoreCase("Hokey") || sportType.equalsIgnoreCase("Football")
	            || sportType.equalsIgnoreCase("Basketball");
	  //method to check if given sporttype matches hockey football or basketball
	}
	
	/**
	 * Write the given record to the appropriate output file
	 * @param record    line to be written
	 * @param sportType    sport type
	 * @param hockeyWriter      PrintWriter for writing records of hockey type
	 * @param footballWriter    PrintWriter for writing records of football type
	 * @param basketballWriter  PrintWriter for writing records of basketball type
	 */
	//write to newer csv file(basketball football hokey if isvalidrecord on the processing method.)
	public static void writeRecordToCSV(String record, String sportType, PrintWriter hockeyWriter,
			PrintWriter footballWriter, PrintWriter basketballWriter){
		//writes record to appropriate printwriter (hokey football basketball)
		try {
			 // Based on the sport type, write the record to the appropriate output file (hockey, football, basketball)
			if (sportType.equals("Hokey")) {
				hockeyWriter.println(record);
			} else if (sportType.equals("Football")) {
				footballWriter.println(record);
			} else if (sportType.equals("Basketball")) {
				basketballWriter.println(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the syntax error to syntax error file.
	 * @param record     line that caused syntax error
	 * @param fileName      The name of CSV file where the syntax error occurred.
	 * @param errorMessage    The error message describing the syntax error.
	 * @param syntaxErrorWriter PrintWriter for writing to the syntax error file.
	 */
	//write the syntax error file
	//template of how syntax error file should look like
	public static void writeSyntaxErrorRecord(String record, String fileName, String errorMessage,
	        PrintWriter syntaxErrorWriter) {
	    // generate string containing syntax error details
	    String syntaxError = "\nSyntax Error in file: " + fileName + "\n" + "====================\n" + "Error: "
	            + errorMessage + "\n" + "Record: " + record + "\n";
	    syntaxErrorWriter.write(syntaxError);
	}
	
	
	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	
	
	
//basically go to part 2 read that and its 3 csv file, then for each line of the part 1 i go to processcsvfile then read the inside until null to catch error
	public static void do_part2() {
	    BufferedReader br = null;
	    String filePath = "part2_output_file_names.txt";
	    PrintWriter semanticErrorWriter = null;
	    int totalRecords = 0; // Variable to keep track of the total number of records

	    try {
	        br = new BufferedReader(new FileReader(filePath)); //initializing the bufferedreader
	        int numFiles = Integer.parseInt(br.readLine());

	        semanticErrorWriter = new PrintWriter(new FileWriter("semantic_error_file.txt"));

	        for (int i = 0; i < numFiles; i++) {
	            String csvFileName = br.readLine().trim(); //reading part 2 
	            int numRecords = processCSVFile(csvFileName, semanticErrorWriter); // Process the CSV file and get the number of valid records
	            totalRecords += numRecords; // Update the total number of records
	        }

	        // Close the PrintWriter after processing all files
	        semanticErrorWriter.close();


	    } catch (FileNotFoundException e) {
	        System.out.println("Error: " + filePath + " not found.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    } catch (TooManyFieldsException e) {
	        e.printStackTrace();
	    } catch (TooFewFieldsException e) {
	        e.printStackTrace();
	    } catch (MissingFieldException e) {
	        e.printStackTrace();
	    } catch (UnknownSportException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Process CSV file, validate each line, and write valid line to appropriate output files
	 * serialize the valid records into binary files for each sport
	 * @param csvFileName  The name of the CSV file to be processed.
	 * @param semanticErrorWriter PrintWriter to write semantic error to semantic error file.
	 */
	//this is where i write it to semantic error txt
	public static int processCSVFile(String csvFileName, PrintWriter semanticErrorWriter) throws NumberFormatException, TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownSportException {
	    Team[] tempValidTeams = new Team[80]; // Temporary array to store valid records
	    int numValidRecords = 0;

	    BufferedReader csvReader = null;
	    try {
	        csvReader = new BufferedReader(new FileReader(csvFileName)); ///reading csv file  that was on part 2
	        String line;

	        while ((line = csvReader.readLine()) != null) {
	            try {
	                if (isValidRecord(line)) { //using same validation for syntax, since its validaded syntaxically already it will work
	                    String[] fields = line.split(",");
	                    String teamName = fields[0].trim();
	                    String sportType = fields[1].trim();
	                    int year = Integer.parseInt(fields[2].trim());
	                    String teamRecord = fields[3].trim();
	                    boolean championship = fields[4].trim().equalsIgnoreCase("Y");
	                    if (year < 1990 || year > 2023) {
	                        throw new BadYearException("Invalid year");
	                    }
	                    if (!isValidTeamRecord(teamRecord)) {
	                        throw new BadRecordException("Invalid team record");
	                    }
	                    Team team = new Team(teamName, sportType, year, teamRecord, championship);
	                    tempValidTeams[numValidRecords] = team; //add team object to tempvalidteams array with numValidRecords as the index so it doesnt overwrite other stuff an goes smoothly
	                    numValidRecords++; //count goes up everytime somethings gets added 
	                }
	            }
	            catch (BadRecordException e) {
	                writeSemanticErrorRecord(line, csvFileName, e.getMessage(), semanticErrorWriter);
	            } 
	            catch (BadYearException e) {
	                writeSemanticErrorRecord(line, csvFileName, e.getMessage(), semanticErrorWriter);
	            }       
	        }
	        
	        //where serializing start
	        // Copy the valid records from the temporary array to the validTeams array
	        Team[] validTeams = new Team[numValidRecords];
	        System.arraycopy(tempValidTeams, 0, validTeams, 0, numValidRecords);//tempvalidteams w/ starting position 0 copied to validteam with position 0(where it start)

	        // Serialize array here into binary file(write to binary file)
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(csvFileName + ".ser"))) {
	            oos.writeObject(validTeams);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    } catch (FileNotFoundException e) {
	        System.out.println("Error: " + csvFileName + " not found!");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (csvReader != null) {
	            try {
	                csvReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return numValidRecords;
	}
	
	/**
	 * Check whether the given team record is valid. It should in format "number-number" 
	 * @return           True if the team record is valid, otherwise false.
	 */
	//this is to check if the format for teamreord is correct.
	public static boolean isValidTeamRecord(String teamRecord) {
	    // Check if the team record is in the format of "number-number"
	    String[] scores = teamRecord.split("-");
	    if (scores.length != 2) {
	        return false;
	    }
	    try {
	        int score1 = Integer.parseInt(scores[0].trim());
	        int score2 = Integer.parseInt(scores[1].trim());
	        return score1 >= 0 && score2 >= 0;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	/**
	 * Write semantic error to semantic error file.
	 * @param record  The line that caused the semantic error.
	 * @param fileName    name of the CSV file where  semantic error occurred.
	 * @param errorMessage    error message describing the semantic error.
	 * @param semanticErrorWriter  PrintWriter  for writing to  semantic error file txt
	 */
	//write the error in this file with this format (template)
	public static void writeSemanticErrorRecord(String record, String fileName, String errorMessage,
	            PrintWriter semanticErrorWriter) {
	      
	    String semanticError = "\nSemantic Error in file: " + fileName + "\n" + "====================\n" + "Error: "
	            + errorMessage + "\n" + "Record: " + record + "\n";
	    semanticErrorWriter.write(semanticError);
	    }

	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	///////////////////////////////////////////////	///////////////////////////////////////////////	///////////////////////////////////////////////
	/**
	 * Displays the main menu and allows viewing of selected binary files.
	 * The method allows for user input until the user chooses to exit the program.
	 */
	public static void do_part3() {
		 String[] binaryFiles = {"Hokey.csv.ser", "Football.csv.ser", "Basketball.csv.ser"};
		    int[] numRecords = new int[binaryFiles.length]; //[0,0,0]
		    int[] currentIndices = new int[binaryFiles.length]; //[0,0,0] keep track of current index for each file.1st record of each basically
		    int currentRecordIndex = 0; //keep track current record index
		    
		    // Deserialize the files and get the number of records for each
		    for (int i = 0; i < binaryFiles.length; i++) {
		        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFiles[i]))) {
		            Team[] teamsArray = (Team[]) ois.readObject(); //teamArray represents the teams in current file
		            numRecords[i] = teamsArray.length;  //[5,2,5] , take length of each teamsArray and put in teamrecords
		            currentIndices[i] = 0; // Set initial current index to 0 for each file so [0,0,0], each file's viewing will start from first record
		        } catch (IOException | ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		    }
		    Scanner scanner = new Scanner(System.in);
		    //infinite loop
		    while (true) {
		    	//display main menu with current file name and number of records (hokey and 5 initially)
		        //binaryFiles array at index 0  which is hokey and numrecords 0 which is 5 for hokey
		        displayMainMenu(binaryFiles[currentIndices[0]], numRecords[currentIndices[0]]); //currentIndices is currently set to 0
		        System.out.print("Enter Your Choice: ");
		        String input = scanner.nextLine();

		        if (input.equalsIgnoreCase("v")) {
		            System.out.println();
		            int currentIndex = currentIndices[0]; // Get index of currently selected file then stores it in currentIndex
		            System.out.println("Viewing: " + binaryFiles[currentIndex] + " (" + numRecords[currentIndex] + " records)");
		            System.out.println("Enter a number(viewing command)");
		            
		            String commandNumber = scanner.nextLine();
		            int n = Integer.parseInt(commandNumber);
		            
		            //n = 0 then ends
		            if (n == 0) {
		                System.out.println("Viewing session ends");
		                System.exit(0);
		            } 
		            //handle 1 and -1
		            else if (n == 1 || n == -1) {
		                // Display the current record that the currentRecordIndex pointer is pointing to
		            	//prints only the current object
		                System.out.println(getTeamRecord(binaryFiles[currentIndex], currentRecordIndex, currentIndices));
		            } 
		            //handling positive
		            else if (n > 0) {
		            	//calculate endIndex 
	                    int endIndex = currentRecordIndex + n - 1;
	                    //if endIndex bigger than total number of records if yes set endIndex to last record index and prints EOF
	                    if (endIndex >= numRecords[currentIndex]) {
	                        endIndex = numRecords[currentIndex] - 1;
	                        System.out.println("EOF has been reached.");
	                    }
	                    try {
	                    	//iterates currentRecordIndex to print multiple records depending on the endIndex that was asked
	                        for (int i = currentRecordIndex; i <= endIndex; i++) {
	                            System.out.println(getTeamRecord(binaryFiles[currentIndex], i, currentIndices));
	                        }
	                        //updates currentRecordIndex to endIndex so it will continue from here next time
	                        currentRecordIndex = endIndex;
	                    } catch (ArrayIndexOutOfBoundsException e) {
	                        System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
	                    }
	                } 
		            //handling negative
		            else {
		            	//calculate startIndex 
	                    int startIndex = currentRecordIndex + n + 1;
	                    //if negative number then BOF
	                    if (startIndex < 0) {
	                        startIndex = 0;
	                        System.out.println("BOF has been reached.");
	                    }
	                    try {
	                    	//loop iterates from currentRecords index to print records backwards until startIndex
	                        for (int i = currentRecordIndex; i >= startIndex; i--) {
	                            System.out.println(getTeamRecord(binaryFiles[currentIndex], i, currentIndices));
	                        }
	                        //set curentRecordIndex to startIndex  so it continues from there next time
	                        currentRecordIndex = startIndex;
	                    } catch (ArrayIndexOutOfBoundsException e) {
	                        System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
	                    }
	                }
		            System.out.println();
		        } else if (input.equalsIgnoreCase("s")) {
                displaySubMenu(binaryFiles, numRecords);
                System.out.println("Enter Your Choice: ");
                String numberInput = scanner.nextLine();
                int binarynum = Integer.parseInt(numberInput);
                if (binarynum >= 1 && binarynum <= binaryFiles.length) {
                    currentIndices[0] = binarynum - 1; // in binaryFiles its 0 1 2 so -1 to get that
                    									//this is where currentIndices gets updated
                } else {
                    System.out.println("Invalid file choice. Try again.");
                }
            } else if (input.equalsIgnoreCase("x")) {
                System.out.println("Program ended");
                System.exit(0);
            } else {
                System.out.println("Invalid input...Try again.");
            }
        }
    }
	/**
	 * Retrieves team record from specified binary file on index.
	 * @param binaryFile name of the binary file to read  team record from.
	 * @param index index of the team record to retrieve from the binary file.
	 * @param currentIndices  array that keeps track of current index for each binary file.
	 * @return  The Team object representing the retrieved team record.
	 */
	//method that returns team: 3parameters: binary files, index(representing index of team record to fetch from binary file) and currentIndecs(keeps track of current index for each file)
	 public static Team getTeamRecord(String binaryFile, int index, int[] currentIndices) {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFile))) {
	            Team[] teamsArray = (Team[]) ois.readObject();//read object from binary files and cast it to array of team
	            return teamsArray[index];
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
			return null;
	       
	    }
	
		 /**
		  * Helper method to display the main menu.
		  * @param binaryFile which is the name of  current  binary file.
		  * @param numRecords which is the number of records in the currently selected binary file.
		  */
	    // Helper method to display the main menu
	    public static void displayMainMenu(String binaryFile, int numRecords) {
	        System.out.println();
	        System.out.println("-----------------------------");
	        System.out.println("Main Menu");
	        System.out.println("-----------------------------");
	        System.out.println("v View the selected file: " + binaryFile + " (" + numRecords + " records)");
	        System.out.println("s Select a file to view");
	        System.out.println("x Exit");
	        System.out.println("-----------------------------");
	    }
	    /**
	     * Helper method to display the submenu
	     * @param binaryFiles array of binary file names.
	     * @param numRecords array of  number of records for each one of the binary file.
	     */
	    // Helper method to display the submenu
	    public static void displaySubMenu(String[] binaryFiles, int[] numRecords) {
	        System.out.println();
	        System.out.println("-----------------------------");
	        System.out.println("File Sub-Menu");
	        System.out.println("-----------------------------");
	        for (int i = 0; i < binaryFiles.length; i++) {
	            System.out.println((i + 1) + " " + binaryFiles[i] + " (" + numRecords[i] + " records)");
	        }
	        System.out.println((binaryFiles.length + 1) + " Exit");
	        System.out.println("-----------------------------");
	    }



	public static void main(String[] args) {
		do_part1();
		do_part2();
		do_part3();
	}

}

