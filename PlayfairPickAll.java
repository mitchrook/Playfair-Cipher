import java.util.*;

public class PlayfairPickAll{
   static String key;
   // String used to pick user's options
   static String ed = "X";
   // Public integer used to store the message length
   public static int messageLength;
   // Public integer used to swap the row and column
   public static int temp;
   // These two int's are used for the 1st letter
   public static int row1;
   public static int column1;
   // These two int's are used for the 2nd letter
   public static int row2;
   public static int column2;
   /* Table for the Playfair Cipher:
      Can be modified for any keyword as long as it holds
      all the letters of the alphabet only once and not
      including 'J'
   */
   private static char[][] table = new char[5][5];

                                    
   /* This main method will first prompt the user as to 
      whether or not they want to encode/decode. 
      Will then take users input and encode/decode.   
   */                                
   public static void main(String[] args){
      // Prompts the user for key
      System.out.println("Welcome to the Playfair Cipher\n");
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter the key: ");
      key = scan.nextLine().trim();
      createTable(key);
      System.out.println();
      printTable();
      System.out.println();
      // Prompts the user for instruction
      System.out.println("Enter E to encode, D to decode, C to change keyword, or Q to quit:");
      String ed = scan.nextLine().trim();
      System.out.println();
      // While the user still wants to encode/decode
      while(!ed.equals("Q")){
         // If the user wants to encode
         if(ed.equals("E")){
            System.out.print("Enter the message to encode: ");
            String msg = scan.nextLine().trim();
            System.out.println();
         // Print out the keyword
            System.out.println("Using the keyword: " + key.toUpperCase());
            System.out.println();
         // Print out the table
            System.out.println("Using the table:");
            printTable();
            System.out.println();
         // Prepare message and print it out
            StringBuilder msgReady = new StringBuilder(prepMessage(msg));
            System.out.println("Message prepared: ");
            printGroups(msgReady);
            System.out.println();
         // Encode the message and print it out
            String encodedMsg = encode(msgReady);
            System.out.println("Message successfully encoded:");
            System.out.println(encodedMsg);
         }
         // If the user wants to decode
         else if(ed.equals("D")){
         // Prompts user to enter message to decode
            System.out.print("Enter the message to decode: ");
            String msg = scan.nextLine().trim();
            System.out.println();
         // Print out the keyword
            System.out.println("Using the keyword: " + key.toUpperCase());
            System.out.println();
         // Print out the table
            System.out.println("Using the table:");
            printTable();
            System.out.println();
         // Prepare message and print it out
            StringBuilder msgReady = new StringBuilder(prepMessage(msg));
            System.out.println("Message prepared: ");
            printGroups(msgReady);
            System.out.println();
            String decodeMe = msgReady.toString();
         // Decode the message and print it out
            System.out.println("Message successfully decoded:");
            String decodedMsg = decode(decodeMe);
            System.out.println(decodedMsg);
         }
         // If the user wants to change the key word
         else if(ed.equals("C")){
            System.out.print("Enter the new key: ");
            key = scan.nextLine().trim();
            createTable(key);
            printTable();
         }
         // If user enters input that is not E, D, or Q - ABORT!!!!
         else{
            System.out.print("Invalid key entry - aborting program");
            System.exit(0);
         }
         // Prompts user for next action
         System.out.println();
         System.out.println("---------------------------------------------------------------------");
         System.out.println("Enter E to encode, D to decode, C to change keyword, or Q to quit:");
         ed = scan.nextLine().trim();
         System.out.println();
      }
      scan.close();

      // Thanks user after quitting program
      System.out.println();
      System.out.println("+------------------------------------------+");
      System.out.println("|                                          |");
      System.out.println("|   Thanks for using the Playfair Cipher   |");
      System.out.println("|                                          |");
      System.out.println("+------------------------------------------+");
   }
   
   // Method used to prepare the user's input
   private static StringBuilder prepMessage(String s){
      // Used to append/insert an 'X' to the message
      String odd = "X";
      /* Takes the string, changes all letters to uppercase,
         removes all blank spaces, then replaces 'J' with 'I'
      */
      s = s.toUpperCase().replace(" ", "").replace("J", "I");
      // Store the message length
      messageLength = s.length();      
      temp = messageLength;
      StringBuilder sb = new StringBuilder(s);
      // If the letters are the same and in the same
      // group for encoding, put an 'X' in the middle
      // and increase the message length for new added
      // character
      for(int i = 0; i < temp-1; i++){
         if((sb.charAt(i) == sb.charAt(i+1))
             && (i % 2 == 0) && ((i + 1) % 2 == 1)){
            sb.insert(i+1, odd);
            messageLength++;
            temp++;
         }
      }
      // If the message length is odd, append an 'X'
      // to the end of the string
      if(messageLength % 2 == 1){
         sb.append(odd);
         messageLength++;
      }
      // Returns the StringBuilder
      return sb;
   } 
   
   // Method to encode the user's input
   private static String encode(StringBuilder sb){
      // Pass the string and the step of 1 to the
      // cipher method
      String encoded = cipher(sb, 1);
      // Print the groups of the encoded message
      //printGroups(enc);
      return encoded;
   }
   
   // Method to decode the encoded message
   private static String decode(String s){
      StringBuilder sb = new StringBuilder(s);
      // Pass the string and the step of 4 to the
      // cipher method. 
      // Step is 4 because 4 = -1 mod 5
      String decoded = cipher(sb, 4);
      return decoded;
   }
   
   /* Method to encode/decode the string depending on the 
      step int passed to this method.
       ~If step = 1 then ENCODE
       ~If step = 4 then DECODE
   */
   private static String cipher(StringBuilder sb, int step){
      StringBuilder coded = new StringBuilder(sb.length());
      // Loop through string for every two letters forming a group
      for(int i = 0; i < sb.length(); i = i + 2){
         // Get the positions of first letter in the table
         getPos(sb.charAt(i), 1);
         // Get the positions of the second letter in the table
         getPos(sb.charAt(i+1), 2);
         // If the rows are the same:
         if(row1 == row2){
            // Add the step to the colomns and mod 5
            column1 = (column1 + step) % 5;
            column2 = (column2 + step) % 5;
         }
         // If the columns are the same:
         else if(column1 == column2){
            // Add the step to the rows and mod 5
            row1 = (row1 + step) % 5;
            row2 = (row2 + step) % 5;
         }
         // If the rows and columns are both different
         else{
            // Swap the columns
            temp = column1;
            column1 = column2;
            column2 = temp;
         }
         // Insert the new characters into the strings
         coded.insert(i, table[row1][column1]);
         coded.insert(i+1, table[row2][column2]);
      }
      // Return the coded string
      return coded.toString();
   }
   
   /* Method to get the position of the character
      in the table, num is used to determine
      whether to store to row1 and column1 or 
      row2 and column2
   */
   public static void getPos(char ch, int num){
      int row = 0;
      int column = 0;
      // Loop through each row and column
      // to find the character in the table
      for(int i = 0; i < 5; i++){
         for(int j = 0; j < 5; j++){
            if(table[i][j] == ch){
               row = i;
               column = j;
               // Exits the nested loop when char is found
               i = 5;
               j = 5;
            }
         }
      }
      // If we are looking for the first char
      if(num == 1){
         row1 = row;
         column1 = column;
      }
      // If we are looking for the second char
      if(num == 2){
         row2 = row;
         column2 = column;
      }
   }
   
   // Method used to print the table
   public static void printTable(){
      char letter;
      // Loops through each row and collumn
      for(int i = 0; i < 5; i++){
         for(int j = 0; j < 5; j++){
            // Prints the characters formatted
            letter = table[i][j];
            System.out.print(" " + letter + " ");
         }
         // After each row start a new line
         System.out.println("");
      }
   }
   
   // Method for print the current string into
   // groups for readability
   public static void printGroups(StringBuilder sb){
      // Loops through string incrementing by 2
      for(int i = 0; i < messageLength - 1; i = i + 2){
         // Prints the first and second letter for a group
         // followed by a space
         char a = sb.charAt(i);
         char b = sb.charAt(i + 1);
         System.out.print(a + "" + b + " ");
      }
      // Print a new line
      System.out.println(" ");
   }
   
   // Method for setting the keyword
   public static void createTable(String k){
      // Formats the entered string
      key = key.toUpperCase().replace(" ", "").replace("J", "I");
      String s = (key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      s = s.replaceAll("J", "I");
      int i = 0;
      // Loops through putting the key into the table
      while(!s.isEmpty()){
         char c = s.charAt(0);
         table[i / 5][i % 5] = c;
         // Removes the duplicates from the string
         String remove = Character.toString(c);
         s = s.replaceAll(remove, "");
         i++;
      } 
   }
}