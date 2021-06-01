import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is a program to scan and test the validity of a file based on certain
 * specifications. Different exceptions are thrown based on if it is valid or not.
 * @author Brig Lowell
 *
 */
public class FormatChecker {
	private int row;
	private int col;
	private int numRows = 0;
	private int numCols = 0;
	private double rowDouble;
	Scanner scan;
	
	
	public FormatChecker (File file) {
		
		try{
			scan = new Scanner(file);
			isValid(scan);	
			System.out.println(file + "\nVALID\n");
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(file + "\n File doesnt exist: " + fnfe + "\nINVALID\n");
		}
		catch (NumberFormatException nfe){
			System.out.println (file + " \nInput not accepted: " + nfe  + "\nINVALID\n");
		}
		catch (InputMismatchException ime){
			System.out.println(file + "\nInput mismatch: " +ime + "\nINVALID\n");
		}
		finally {
			scan.close();
		}
	}

	/**
	 * This method is where all my calculations for when a file is scanned.
	 * It has two while loops scanning rows and number of columns. It also 
	 * determines if items are ints for the first grid numbers and doubles for
	 * the rest of the file input. Exceptions are thrown if there are violations.
	 * @param s
	 */
	public void isValid(Scanner s) {
		
		//scans top row in file to make sure there is only 2 pieces of input
		//otherwise throws input mismatch exception
		String[] topRow = s.nextLine().split("\\s+");
		if(topRow.length < 2){
			throw new InputMismatchException("First line has too few inputs!");
		}else if (topRow.length > 2) {
			throw new InputMismatchException("First line has too many inputs!");
		}
		
		//scans in the first two Ints to determine grid size,
		//also determines if inputs are Integers
		row = Integer.parseInt(topRow[0]);
		col = Integer.parseInt(topRow[1]);
		
		//First while scanner to determine the amount of rows and counts the 
		//number of rows.
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner tokenScanner = new Scanner(line);
			//if the next line is not empty, then increment numRows
			if(!line.isEmpty()) numRows ++;
			
			//second while loop to scan each item in the rows. Determines if there are
			//any items other than doubles and gets the column count
			while(tokenScanner.hasNext()) {
				String token = tokenScanner.next();
				tokenScanner.useDelimiter("\\s+");
				Double.parseDouble(token);
				numCols++;
			}
			tokenScanner.close();
		}
		//Casts row to a double so I can check below in number columns.
		rowDouble = (double)row;
		//Determines amount of rows and throws exception if row count is off.
		if (numRows > row){						  
			throw new InputMismatchException("Number of rows exceeds expected rows.");	
		} else if(numRows < row){
			throw new InputMismatchException("Number of rows is less than expected rows.");
		}
		//Calculates amount of Columns by dividing amount of items total
		//divided by the number of rows. Throws exception if column count is off.
		if(numCols/rowDouble > col) {
			throw new InputMismatchException("Number of columns exceeds expected columns.");
		}else if (numCols/rowDouble < col) {
			throw new InputMismatchException("Number of columns is less than expected columns.");
		}
		s.close();
	}
	
	/**
	 * Reads in file and runs it through FormatChecker class.
	 * @param args
	 */
	public static void main(String args[]){
		
		for(int i = 0; i < args.length; i++) {
			File file = new File(args[i]);
			FormatChecker text = new FormatChecker(file);
		}
	}
}
