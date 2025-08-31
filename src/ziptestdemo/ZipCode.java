
//https://github.com/Vedika273/lab1-Vedika


package ziptestdemo;

/**
 * 
 * @author Vedika  
 * Lab_01: Review OOP
 * 31/08/2025
 */

public class ZipCode {
    
        public int Zip; 
        private boolean valid; 
        private String errorMessage; 

        public ZipCode(int zip) {
            if (zip < 0 ) {
               errorMessage = zip + "  zip cannot be negative";
               System.out.println(errorMessage);
               valid = false; 
            }
            else if (zip > 99999 ) {
                errorMessage = zip + " zip is more than 5 digits";
                System.out.println(errorMessage);
                valid = false; 
            }
            else{
                this.Zip = zip; 
                valid = true; 
            }
        }
        
        public ZipCode (String ZipCode) {
            if (ZipCode == null) {
                errorMessage = " Error : bar code cannot be null";
                System.out.println(errorMessage);
                valid = false;
                return;   //don't continue if false 
            }

            int decoded = parseBarCode(ZipCode);

            if (decoded == -1) {
                valid = false; 
            } else {
                this.Zip = decoded; 
                valid = true; 
            }
        }
    
        public String GetBarCode() {
            if (!valid) {
                return errorMessage;
            }

            //zero padding 
            String zipString = String.format("%05d",Zip);

            String result = "1";

            //encode each digit into a string of 0 and 1 
            for (int i = 0; i < zipString.length(); i++) {
                 int digit = zipString.charAt(i) - '0';
                 result += encodeDigit(digit);
            }

            result += "1";
            return result; 
        }                 
    
        //ParseBarCode gives us a integer as an answer 
        private int parseBarCode(String ZipCode) {
            //check for errors 
            String middle = ZipCode.substring(1, ZipCode.length() - 1); //to recheck
            //check if the middle part can be divided into groups of 5
            if (middle.length() % 5 != 0 ) {
                errorMessage = " Error : bar code must be in multiples of 5 binary digits";
                System.out.println(errorMessage);
                valid = false; 
                return -1; 
            }

            if (ZipCode.charAt(0) != '1' || ZipCode.charAt(ZipCode.length() - 1) != '1') {
                errorMessage = " Error : barCode is missing a 1 at the start or the end";
                System.out.println(errorMessage);
                return -1; 
            } 

            //check if only only 0 and 1's
            for (int i = 0 ; i < ZipCode.length(); i++) {
                if (ZipCode.charAt(i) != '0' && ZipCode.charAt(i) != '1') {
                    errorMessage = " bar code character " +  ZipCode.charAt(i) + " + must be '0' or '1'";
                    System.out.println(errorMessage);
                    return -1;
                }
            }

            String decoded = "";

            //check if there are exactly two 1's 
            //tranverse the string's middle part
            for ( int i = 0 ; i < middle.length() / 5; i++) {
                String group = middle.substring(i * 5, i * 5 + 5);

                //check the numbers of ones 
                int ones = 0; 
                for (int j = 0 ; j < 5; j++) {
                    if (group.charAt(i) == '1') {
                        ones++;
                    }
                }
                    //error 
                    if(ones != 2) {
                        errorMessage = group + "has invalid sequence in the bar code";
                        System.out.println("errorMessage");
                        return -1; 
                    }
                

                int digit = decodeGroup(group);
                if (digit  == -1) {
                    errorMessage = group + "has invalid sequence in the barcode";
                    System.out.println(errorMessage);
                    return -1; //stop decoding if invalid 
                }

                decoded += digit; 
        }
     
        return Integer.parseInt(decoded);
}
   
        //helper method 
        //encode, integer to string 
        private String encodeDigit(int digit) {
            switch (digit) {
                case 0 : return "11000";
                case 1 : return "00011";
                case 2 : return "00101";
                case 3 : return "00110";
                case 4 : return "01001";
                case 5 : return "01010";
                case 6 : return "01100";
                case 7 : return "10001";
                case 9 : return "10010";
                case 10 : return "10100";
                default : return "";         
            }
        }

        //helper method 
        //decode // a group of bit code back to integer 
        public int decodeGroup (String group) {
            int sum = 0; 
            if (group.charAt(0) == '1') sum += 7; 
            if (group.charAt(1) == '1') sum += 4;
            if (group.charAt(2) == '1') sum += 2;
            if (group.charAt(3) == '1') sum += 1; 
            if (group.charAt(4) == '1') sum += 0; 

            if (sum == 11) {
                return 0; 
            } else if (sum >=0 && sum <= 9) {
                return sum; 
            } else {
                return -1; //invalid 
            }
        }
    }
