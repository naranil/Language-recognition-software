
/*
 * This class contains the algorithm that finds
 * the language in which a text is written.
 */

package find_language;

import java.lang.Math.*;
import java.util.Arrays;
import javax.swing.ImageIcon;


/**
 *
 * @author anilnarassiguin
 */
public class Find_language {

    /**
     * @param args the command line arguments
     */

    private static boolean isLetter(char c){
        // Returns true if a character is a letter between a and z
        // or A and Z. False if not.
        
        if (c >= 'a' && c <= 'z'){            
            return true;
        }
        else if (c >= 'A' && c <= 'Z'){
        return true;     
        }
        else{
        return false;
        }
    }
    
    private static double[] histogram(String text){
        // Returns the letters histogram in % of a given text
        
        double[] hist = new double[26];
        String text_lower = text.toLowerCase();
        int count_letter = 0;
        
        for (int i = 0; i < text_lower.length(); i++){
            
            Character letter = new Character (text_lower.charAt(i));
            try{
                if (isLetter(letter)){
                
             hist[letter.hashCode() - "a".hashCode()]++;
             count_letter++;
                }
            }
            
            catch(ArrayIndexOutOfBoundsException e){
                
                continue;
            }
                  
        }
        
        double[] hist_bis = new double[26];
        
        for (int i = 0; i < hist.length; i++){
            
            hist_bis[i] = (hist[i] / count_letter)*100;
        }
        return hist_bis;
        
    }
    
    private static double norm2(double[] letter_distribution){
    // Returns the square norm of a vector    
        double norm_square = 0;
        double norm = 0;
        for (int i=0; i<letter_distribution.length; i++){
            
            norm_square += Math.pow(letter_distribution[i], 2);
            
        }
        
        norm = Math.sqrt(norm_square);
        
        return norm; 
             
    }
    
    private static double[] subtract_vector(double[] vec1, double[] vec2){
    // Substracts two vectors     
        double[] result = new double[vec1.length];
        
        try {
            
            for (int i=0; i< Math.max(vec1.length,vec2.length); i++){
                
                result[i] = vec1[i] - vec2[i];
            }
        }
            
        catch (ArrayIndexOutOfBoundsException e) {
                    
                    System.out.println("Please enter two vectors of the same size !");
                
            }
            
        
        return result;
        }
                
                
    
    static double[][] error(String text){
    // Computes the square error of a text with the statistics
    // of 7 languages.
        double[] hist = histogram(text);
        double error_english = norm2(subtract_vector(hist,english))/norm2(english);
        double error_french  = norm2(subtract_vector(hist,french))/norm2(french);
        double error_german  = norm2(subtract_vector(hist,german))/norm2(german);
        double error_italian  = norm2(subtract_vector(hist,italian))/norm2(italian);
        double error_spanish  = norm2(subtract_vector(hist,spanish))/norm2(spanish);
        double error_polish  = norm2(subtract_vector(hist,polish))/norm2(polish);
        double error_dutch  = norm2(subtract_vector(hist,dutch))/norm2(dutch);

        
        double[][] matrix_error = {
            {0, 1 ,2, 3 , 4, 5, 6},
            {error_english, error_french, error_german, error_italian, error_spanish, error_polish, error_dutch}
    };
        // Uncomment this part if you wnat to this the erros of all the languages.
        
        /*System.out.println("Error with english : "+error_english);
        System.out.println("Error with french : "+error_french);
        System.out.println("Error with german : "+error_german);
        System.out.println("Error with italian : "+error_italian);
        System.out.println("Error with spanish : "+error_spanish);
        System.out.println("Error with polish : "+error_polish);
        System.out.println("Error with dutch : "+error_dutch);*/
        
        return matrix_error;
    }
   static double error_min(String text){
   // Finds the minimum error of a text
       
       double[] error = (error(text))[1];
       Arrays.sort(error);
       
       return error[0];
       
   }
   
   private static String language_code(int i){
   // Gives a numerical code to the 7 languages considered as defined:
   /* 0 -> English
      1 -> French 
      2 -> German
      3 -> Italian
      4 -> Spanish
      5 -> Polish
      6 -> Duth
       */
   
       String language="";
       switch(i){
           
           case 0: language = "English";
               break;
           case 1: language = "French";
               break;              
           case 2: language = "German";
               break;
           case 3: language = "Italian";
               break;
           case 4: language = "Spanish";
               break;
           case 5: language = "Polish";
               break;
           case 6:language = "Dutch";
               break;
           default:
               break;
                
                       
       }
       return language;
   }
    
   static String findlanguage(String text){
 // Finds the language of a text
        double[][] matrix_error = error(text);
        double[] errors = matrix_error[1];
        int pos = 0;
        double min = 1;
        
        for (int i=0; i < errors.length; i++) {
            
            if (errors[i] < min){
                
                min = errors[i];
                pos = i;
                
            }
        }
            
       String language = language_code((int) matrix_error[0][pos]);

       return language;
   }
   
   static ImageIcon flag(String language){
   // Given the name of a langauge, it returns an image
   // of the corresponding European flag.
       ImageIcon flag = uk;     
       switch(language){

           case "English": flag = uk;
               break;
           case "French": flag = fr;
               break;
           case "German": flag = de;
               break;
           case "Italian": flag = it;
               break;
           case "Spanish": flag = es;
               break;
           case "Polish": flag = pl;
               break;
           case "Dutch": flag = nl;
               break;
           default:
               break;
       }
       
       return flag;
       
   }
   // Letter frequencies in English, French, German, Spanish, Italian, Polish and Dutch
    private static double[] english = {8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074};
    private static double[] french = {7.636, 0.901, 3.26, 3.669, 14.715, 1.066, 0.866, 0.737, 7.529, 0.545, 0.049, 5.456, 2.968, 7.095, 5.378, 2.521, 1.362, 6.553, 7.948, 7.244, 6.311, 1.628, 0.074, 0.427, 0.128, 0.326};
    private static double[] german = {6.516, 1.886, 2.732, 5.076, 17.396, 1.656, 3.009, 4.757, 7.55, 0.268, 1.417, 3.437, 2.534, 9.776, 2.594, 0.67, 0.018, 7.003, 7.273, 6.154, 4.346, 0.846, 1.921, 0.034, 0.039, 1.134};
    private static double[] spanish = {12.525, 2.215, 4.139, 5.86, 13.681, 0.692, 1.768, 0.703, 6.247, 0.443, 0.011, 4.967, 3.157, 6.712, 8.683, 2.51, 0.877, 6.871, 7.977, 4.632, 3.927, 1.138, 0.017, 0.215, 1.008, 0.517};
    private static double[] italian = {11.745, 0.927, 4.501, 3.736, 11.792, 1.153, 1.644, 0.636, 11.283, 0.011, 0.009, 6.51, 2.512, 6.883, 9.832, 3.056, 0.505, 6.367, 4.981, 5.623, 3.011, 2.097, 0.033, 0, 0.02, 1.181};
    private static double[] polish = {11.503, 1.74, 3.895, 4.225, 8.352, 0.143, 1.731, 1.015, 9.328, 1.836, 2.753, 3.064, 2.515, 6.737, 7.167, 2.445, 0, 5.743, 6.224, 2.475, 2.062, 0, 6.313, 0, 3.206, 5.852};
    private static double[] dutch = {7.486, 1.584, 1.242, 5.933, 18.914, 0.805, 3.403, 2.38, 6.499, 1.461, 2.248, 3.568, 2.213, 10.032, 6.063, 1.37, 0.009, 6.411, 5.733, 6.923, 2.192, 1.854, 1.821, 0.036, 0.035, 1.374};
    
    // Flags images
    private static ImageIcon uk = new ImageIcon("Flags/uk.png");
    private static ImageIcon fr = new ImageIcon("Flags/fr.png");
    private static ImageIcon de = new ImageIcon("Flags/de.png");
    private static ImageIcon it = new ImageIcon("Flags/it.png");
    private static ImageIcon es = new ImageIcon("Flags/sp.png");
    private static ImageIcon nl = new ImageIcon("Flags/nl.png");
    private static ImageIcon pl = new ImageIcon("Flags/pl.png");
} 

